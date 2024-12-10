package com.booking.bookings.service.impl;

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
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {
    private final BookingService bookingService;
    private final VNPAYConfig vnPayConfig;

    @Value("${email.client-url}")
    private String clientUrl;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public PaymentUrlResponse createVnpayPaymentURL(HttpServletRequest request, CreatePaymentUrlRequest paymentUrlRequest) {
        BookingEntity booking = bookingService.getBookingById(paymentUrlRequest.getBookingId(), null);
//        BookingEntity booking = new BookingEntity();
        String orderId = booking.getPaymentId();
        String amount = String.valueOf(BigDecimal.valueOf(booking.getTotalPrice()).multiply(BigDecimal.valueOf(100)));
        String locale = paymentUrlRequest.getLocale();

        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();

        vnpParamsMap.put("vnp_Amount", amount);
        vnpParamsMap.put("vnp_IpAddr", VNPayUtils.getIpAddress(request));
        vnpParamsMap.put("vnp_TxnRef", orderId);
        vnpParamsMap.put("vnp_Locale", locale);
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan cho ma GD:" + orderId);

        if (paymentUrlRequest.getBankCode() != null && StringUtils.isExist(paymentUrlRequest.getBankCode().name())) {
            vnpParamsMap.put("vnp_BankCode", paymentUrlRequest.getBankCode().name());
        }

        String serverURL = String.format("%s://%s", request.getScheme(), request.getServerName());
        String returnUrl = serverURL + contextPath + "/bookings/payment/return/vnpay";
        vnpParamsMap.put("vnp_ReturnUrl", returnUrl);

        //build query url
        String queryUrl = VNPayUtils.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtils.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtils.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;

        return PaymentUrlResponse.builder()
                .data(paymentUrl)
                .status("success")
                .build();
    }

    @Override
    public Map<String, String> vnpayPaymentResult(HttpServletRequest request) throws Exception {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> params = parameterMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()[0]
                ));

        Map<String, String> sortedParams = new TreeMap<>(params);
        String secureHash = sortedParams.remove("vnp_SecureHash");
        sortedParams.remove("vnp_SecureHashType");

        String defaultLocale = "en"; // Replace with your default locale, if different.
        String orderId = sortedParams.get("vnp_TxnRef");

        String signData = sortedParams.entrySet().stream()
                .map(p -> p.getKey() + "=" + p.getValue())
                .collect(Collectors.joining("&"));

        Mac mac = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKeySpec = new SecretKeySpec(vnPayConfig.getSecretKey().getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        mac.init(secretKeySpec);

        String signed = Hex.encodeHexString(mac.doFinal(signData.getBytes(StandardCharsets.UTF_8)));

        String resultUrl = clientUrl + "/" + defaultLocale + "/book-room/result?channel=vn_pay";
        String vnpayCode = sortedParams.get("vnp_ResponseCode");

        if (secureHash.equals(signed)) {
            BookingEntity updateResult = bookingService.updateBookingPaymentValue(orderId, true, JsonUtils.getObject(JsonUtils.toString(params), VnpayResultInfo.class));

            if (updateResult != null) vnpayCode = "100"; // custom: internal error, update failed

            return Collections.singletonMap("url", resultUrl + "&code=" + vnpayCode);
        } else {
            return Collections.singletonMap("url", resultUrl + "&code=97");
        }
    }

}
