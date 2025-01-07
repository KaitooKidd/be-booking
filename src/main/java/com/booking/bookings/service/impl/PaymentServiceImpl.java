package com.booking.bookings.service.impl;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.booking.bookings.configs.VNPAYConfig;
import com.booking.bookings.dtos.VnpayResultInfo;
import com.booking.bookings.dtos.request.CreatePaymentUrlRequest;
import com.booking.bookings.dtos.request.RefundPaymentVnPayRequest;
import com.booking.bookings.dtos.response.PaymentUrlResponse;
import com.booking.bookings.entity.BookingEntity;
import com.booking.bookings.service.BookingService;
import com.booking.bookings.service.PaymentService;
import com.booking.utils.JsonUtils;
import com.booking.utils.StringUtils;
import com.booking.utils.VNPayUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    private static final String VNP_API_URL = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";
    private final BookingService bookingService;
    private final VNPAYConfig vnPayConfig;
    private final PendingBookingService pendingBookingService;

    @Value("${email.client-url}")
    private String clientUrl;

    @Override
    public PaymentUrlResponse createVnpayPaymentURL(
            HttpServletRequest request, CreatePaymentUrlRequest paymentUrlRequest) {
        BookingEntity booking = bookingService.getBookingById(paymentUrlRequest.getBookingId(), null);

        String orderId = booking.getPaymentId();
        String amount = String.valueOf((long) (booking.getTotalPrice() * 100));
        String locale = paymentUrlRequest.getLocale();

        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();

        vnpParamsMap.put("vnp_Amount", amount);
        vnpParamsMap.put("vnp_IpAddr", VNPayUtils.getIpAddress(request));
        vnpParamsMap.put("vnp_TxnRef", orderId);
        vnpParamsMap.put("vnp_Locale", locale);
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan cho ma GD:" + orderId);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        Long ttlMillis = pendingBookingService.remainTime(paymentUrlRequest.getBookingId()); // TTL in milliseconds
        if (ttlMillis == null || ttlMillis > 0) {
            calendar.add(Calendar.MILLISECOND, -1);
        } else {
            calendar.add(Calendar.MILLISECOND, Math.toIntExact(Math.abs(ttlMillis)));
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpExpireDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_ExpireDate", vnpExpireDate);

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
            if (updateResult != null && !pendingBookingService.tryPayPending(updateResult.getId())) {
                return Collections.singletonMap("url", resultUrl + "&code=97");
            }

            if (updateResult == null) vnpayCode = "100"; // custom: internal error, update failed

            return Collections.singletonMap("url", resultUrl + "&code=" + vnpayCode);
        } else {
            return Collections.singletonMap("url", resultUrl + "&code=97");
        }
    }

    public String refundVNPAY(HttpServletRequest request, RefundPaymentVnPayRequest refundRequest) throws IOException {
        return sendRefundRequest(createRequestRefundVNPay(request, refundRequest.getBookingId()));
    }

    private Map<String, String> createRequestRefundVNPay(HttpServletRequest request, String bookingId) {
        BookingEntity booking = bookingService.getBookingById(bookingId, true);
        VnpayResultInfo paymentInfo = (VnpayResultInfo) booking.getPaymentInfo();
        String orderId = booking.getPaymentId();
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_RequestId", UUID.randomUUID().toString());
        vnpParamsMap.put("vnp_Command", "refund");
        vnpParamsMap.put("vnp_TransactionType", "02");
        vnpParamsMap.put("vnp_TxnRef", orderId);
        vnpParamsMap.put("vnp_Amount", String.valueOf((long) (booking.getTotalPrice() * 100)));
        vnpParamsMap.put("vnp_OrderInfo", "Hoan tien cho ma GD:" + orderId);
        vnpParamsMap.put("vnp_TransactionNo", paymentInfo.getVnp_TransactionNo());
        vnpParamsMap.put("vnp_TransactionDate", vnpParamsMap.get("vnp_CreateDate"));
        vnpParamsMap.put("vnp_CreateBy", booking.getCustomerName());
        vnpParamsMap.put("vnp_IpAddr", VNPayUtils.getIpAddress(request));

        String data = String.join(
                "|",
                vnpParamsMap.get("vnp_RequestId"),
                vnpParamsMap.get("vnp_Version"),
                vnpParamsMap.get("vnp_Command"),
                vnpParamsMap.get("vnp_TmnCode"),
                vnpParamsMap.get("vnp_TxnRef"),
                vnpParamsMap.get(" vnp_TransactionDate"),
                vnpParamsMap.get("vnp_CreateDate"),
                vnpParamsMap.get("vnp_IpAddr"),
                vnpParamsMap.get("vnp_OrderInfo"));

        String vnpSecureHash = VNPayUtils.hmacSHA512(vnPayConfig.getSecretKey(), data);
        vnpParamsMap.put("vnp_SecureHash", vnpSecureHash);
        //        vnpParamsMap.put("vnp_SecureHash", paymentInfo.getVnp_SecureHash());

        vnpParamsMap.remove("vnp_CurrCode");
        vnpParamsMap.remove("vnp_OrderType");
        return vnpParamsMap;
    }

    private String sendRefundRequest(Map<String, String> postData) throws IOException {
        URL url = new URL(VNP_API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // Gửi dữ liệu JSON
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = JsonUtils.toString(postData).getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Đọc phản hồi
        int responseCode = conn.getResponseCode();
        InputStream inputStream =
                (responseCode >= 200 && responseCode < 300) ? conn.getInputStream() : conn.getErrorStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    public String hmacSHA256(String secretKey, String data) {
        try {

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest((data + secretKey).getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error while hashing: " + e.getMessage());
        }
    }
}
