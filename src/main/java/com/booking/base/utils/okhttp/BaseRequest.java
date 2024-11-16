package com.booking.base.utils.okhttp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import okhttp3.MediaType;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BaseRequest {
    Map<String, String> params;
    Map<String, String> headers;
    private RequestMethod method;
    private String url;
    private MediaType mediaType;
    private Object payload;
}
