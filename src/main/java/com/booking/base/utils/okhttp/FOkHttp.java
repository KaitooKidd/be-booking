package com.booking.base.utils.okhttp;

import com.booking.auth.exception.ApiException;
import com.falcon.moservice.helpers.okhttp.CurlLoggingInterceptor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Log4j2
public class FOkHttp {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public static final MediaType FORM_URLENDCODED = MediaType.get("application/x-www-form-urlencoded");
    @Getter
    @Setter
    private OkHttpClient client;

    public FOkHttp() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        client = new OkHttpClient.Builder()
                .addInterceptor(new CurlLoggingInterceptor())
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build();
        Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);
    }

    private static Request addHeaders(Request request, BaseRequest call) {
        if (call.getHeaders() != null) {
            for (Map.Entry<String, String> entry : call.getHeaders().entrySet()) {
                request = request.newBuilder().addHeader(entry.getKey(), entry.getValue()).build();
            }
        }

        return request;
    }

    private static HttpUrl.Builder getUrlWithParams(BaseRequest call) {
        HttpUrl.Builder httpBuilder = Objects.requireNonNull(HttpUrl.parse(call.getUrl())).newBuilder();
        if (call.getParams() != null) {
            for (Map.Entry<String, String> entry : call.getParams().entrySet()) {
                httpBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        return httpBuilder;
    }

    private static void validateRequest(BaseRequest request) {
        if (request.getMethod() == null)
            throw new IllegalArgumentException("Attribute method is required!");
        else if (request.getUrl() == null || request.getUrl().isEmpty())
            throw new IllegalArgumentException("Attribute url is required!");
        else if (RequestMethod.POST.equals(request.getMethod())) {
            if (request.getMediaType() == null)
                throw new IllegalArgumentException("Attribute type is required for " + request.getMethod() + "!");
            else if (request.getPayload() == null)
                throw new IllegalArgumentException("Attribute payload is required for " + request.getMethod() + "!");
        }
    }

    public String call(BaseRequest request) throws IOException {
        validateRequest(request);

        return switch (request.getMethod()) {
            case GET -> get(request);
            case HEAD -> null;
            case POST -> post(request);
            case PUT -> put(request);
            case PATCH -> patch(request);
            case DELETE -> delete(request);
            case OPTIONS -> null;
            case TRACE -> null;
        };
    }

    private String get(BaseRequest call) throws IOException {
        HttpUrl.Builder httpBuilder = getUrlWithParams(call);

        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .build();

        request = addHeaders(request, call);

        Response response = client.newCall(request).execute();
        return generateResponse(response);
    }

    private String post(BaseRequest call) throws IOException {
        HttpUrl.Builder httpBuilder = getUrlWithParams(call);

        RequestBody body = null;
        if (call.getMediaType() == JSON) {
            body = RequestBody.create((String) call.getPayload(), call.getMediaType());
        } else if (call.getMediaType() == FORM_URLENDCODED) {
            body = (FormBody) call.getPayload();
        }

        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .post(body)
                .build();

        request = addHeaders(request, call);

        Response response = client.newCall(request).execute();
        return generateResponse(response);
    }

    private String put(BaseRequest call) throws IOException {
        HttpUrl.Builder httpBuilder = getUrlWithParams(call);

        RequestBody body = RequestBody
                .create(call.getPayload().toString(), call.getMediaType());

        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .put(body)
                .build();

        request = addHeaders(request, call);

        Response response = client.newCall(request).execute();
        return generateResponse(response);
    }

    private String patch(BaseRequest call) throws IOException {
        HttpUrl.Builder httpBuilder = getUrlWithParams(call);

        RequestBody body = RequestBody
                .create(call.getPayload().toString(), call.getMediaType());

        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .patch(body)
                .build();

        request = addHeaders(request, call);

        Response response = client.newCall(request).execute();
        return generateResponse(response);
    }

    private String delete(BaseRequest call) throws IOException {
        HttpUrl.Builder httpBuilder = getUrlWithParams(call);

        RequestBody body = RequestBody
                .create(call.getPayload().toString(), call.getMediaType());

        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .delete(body)
                .build();

        request = addHeaders(request, call);

        Response response = client.newCall(request).execute();
        return generateResponse(response);
    }

    public String execute(Request request) throws IOException {
        Response response = client.newCall(request).execute();
        return this.generateResponse(response);
    }

    @Value("${applications.env}")
    private String env;
    private String generateResponse(Response response) throws IOException {
        try {
            String body = response.body().string();
            if (env.equals("dev")) {
                log.info("API response: " + body);
            }
            if (!response.isSuccessful()) {
                throw new  ApiException(response.code(),
                        response.request().method(),
                        response.request().url().toString(),
                        response.message() + body,
                        response.header("mo-trace-id"));
            }
            return body;
        } catch (Exception e) {
            throw e;
        }
    }
}
