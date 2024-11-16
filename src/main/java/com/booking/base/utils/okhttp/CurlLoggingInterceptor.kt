package com.falcon.moservice.helpers.okhttp

import com.booking.auth.exception.ApiException
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor.Logger
import okio.Buffer
import java.nio.charset.Charset
import java.util.*

class CurlLoggingInterceptor(private val logger: Logger = Logger.DEFAULT) : Interceptor {

    companion object {
        val UTF8 = Charset.forName("UTF-8")
    }

    private var curlOptions: String? = null

    @Throws(ApiException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        var compressed = false

        var curlCmd = "curl"
        if (curlOptions != null) {
            curlCmd += " " + curlOptions!!
        }
        curlCmd += " -X " + request.method + " \"${request.url}\""

        val headers = request.headers
        for (i in 0 until headers.size) {
            val name = headers.name(i)
//            if (name.contains("Authorization")
//                || name.contains("api")
//                || name.contains("token")
//                || name.contains("key")) {
//                continue
//            }
            var value = headers.value(i)

            val start = 0
            val end = value.length - 1
            if (value[start] == '"' && value[end] == '"') {
                value = "\\\"" + value.substring(1, end) + "\\\""
            }

            if ("Accept-Encoding".equals(name, ignoreCase = true) && "gzip".equals(value, ignoreCase = true)) {
                compressed = true
            }
            curlCmd += " -H \"$name: $value\""
        }

//        if (response.isSuccessful) return response

        request.body?.let {
            val buffer = Buffer().apply { it.writeTo(this) }
            val charset = it.contentType()?.charset(UTF8) ?: UTF8

            curlCmd += " --data $'" + buffer.readString(charset).replace("\n", "\\n") + "'"
        }

        curlCmd += (if (compressed) " --compressed " else " ")

        val uuid = UUID.randomUUID().toString()

        logger.log("Request ID: [$uuid] $curlCmd")

        return response.newBuilder()
            .addHeader("mo-trace-id", uuid).build();
    }
}