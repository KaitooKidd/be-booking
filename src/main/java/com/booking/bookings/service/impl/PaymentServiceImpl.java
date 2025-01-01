package com.booking.bookings.service.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Hex;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.booking.bookings.configs.VNPAYConfig;
import com.booking.bookings.dtos.VnpayResultInfo;
import com.booking.bookings.dtos.request.CreatePaymentUrlRequest;
import com.booking.bookings.dtos.response.PaymentUrlResponse;
import com.booking.bookings.entity.BookingEntity;
import com.booking.bookings.service.BookingService;
import com.booking.bookings.service.PaymentService;
import com.booking.utils.JsonUtils;
import com.booking.utils.StringUtils;
import com.booking.utils.VNPayUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import static com.booking.bookings.service.impl.BookingServiceImpl.REDIS_PREFIX;

@RequiredArgsConstructor
@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {
    private final BookingService bookingService;
    private final VNPAYConfig vnPayConfig;
    @Autowired
    private RedissonClient redissonClient;

    @Value("${email.client-url}")
    private String clientUrl;

    @Override
    public PaymentUrlResponse createVnpayPaymentURL(
            HttpServletRequest request, CreatePaymentUrlRequest paymentUrlRequest) {
        BookingEntity booking = bookingService.getBookingById(paymentUrlRequest.getBookingId(), null);
        //        BookingEntity booking = new BookingEntity();
        String orderId = booking.getPaymentId();
        String amount = String.valueOf((long) (booking.getTotalPrice() * 100));
        String locale = paymentUrlRequest.getLocale();

        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();

        vnpParamsMap.put("vnp_Amount", amount);
        vnpParamsMap.put("vnp_IpAddr", VNPayUtils.getIpAddress(request));
        vnpParamsMap.put("vnp_TxnRef", orderId);
        vnpParamsMap.put("vnp_Locale", locale);
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan cho ma GD:" + orderId);

        RBucket<Object> bucket = redissonClient.getBucket(REDIS_PREFIX + paymentUrlRequest.getBookingId());
        long ttlMillis = bucket.remainTimeToLive(); // TTL in milliseconds

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        if(ttlMillis > 0) {
            calendar.add(Calendar.MILLISECOND, (int) ttlMillis);
        } else {
            calendar.add(Calendar.MINUTE, 10);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_ExpireDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_ExpireDate", vnp_ExpireDate);

        if (paymentUrlRequest.getBankCode() != null
                && StringUtils.isExist(paymentUrlRequest.getBankCode().name())) {
            vnpParamsMap.put("vnp_BankCode", paymentUrlRequest.getBankCode().name());
        }
        String returnUrl =
                request.getRequestURL().toString().replace(request.getServletPath(), "/bookings/payment/return/vnpay");

        vnpParamsMap.put("vnp_ReturnUrl", returnUrl);

        // build query url
        String queryUrl = VNPayUtils.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtils.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtils.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;

        return PaymentUrlResponse.builder().data(paymentUrl).status("success").build();
    }

    @Override
    public Map<String, String> vnpayPaymentResult(HttpServletRequest request) throws Exception {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> params =
                parameterMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()[0]));

        Map<String, String> sortedParams = new TreeMap<>(params);
        String secureHash = sortedParams.get("vnp_SecureHash");
        sortedParams.remove("vnp_SecureHash");
        sortedParams.remove("vnp_SecureHashType");

        String defaultLocale = "en"; // Replace with your default locale, if different.
        String orderId = sortedParams.get("vnp_TxnRef");

        String signData = sortedParams.entrySet().stream()
                .map(p -> p.getKey() + "=" + URLEncoder.encode(p.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        Mac mac = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKeySpec =
                new SecretKeySpec(vnPayConfig.getSecretKey().getBytes(), "HmacSHA512");
        mac.init(secretKeySpec);
        String signed = Hex.encodeHexString(mac.doFinal(signData.getBytes(StandardCharsets.UTF_8)));

        String resultUrl = clientUrl + "/" + defaultLocale + "/book-room/result?channel=vn_pay";
        String vnpayCode = sortedParams.get("vnp_ResponseCode");

        if (secureHash.equals(signed) && "00".equals(vnpayCode)) {
            BookingEntity updateResult = bookingService.updateBookingPaymentValue(
                    orderId, true, JsonUtils.getObject(JsonUtils.toString(params), VnpayResultInfo.class));
            if (updateResult != null) {
                bookingService.deleteBookingFromRedis(updateResult.getId());
            }

            if (updateResult == null) vnpayCode = "100"; // custom: internal error, update failed

            return Collections.singletonMap("url", resultUrl + "&code=" + vnpayCode);
        } else {
            return Collections.singletonMap("url", resultUrl + "&code=97");
        }
    }
}
