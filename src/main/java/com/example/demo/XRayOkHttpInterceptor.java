package com.example.demo;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.entities.Segment;
import com.amazonaws.xray.entities.Subsegment;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class XRayOkHttpInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Segment segment = AWSXRay.beginSegment("MyApplication-DEV");
//        Subsegment subsegment = AWSXRay.beginSubsegment("OkHttp Call: " + request.url());

        Map<String, Object> httpMap = new HashMap<>();

        try {

            segment.setOrigin(request.method());
            segment.setUser("Rohit");

            httpMap.put("request", Map.of(
            "method", "GET",
            "url", request.url().toString()
            ));
            // Execute the request
            Response response = chain.proceed(request);

            httpMap.put("response", Map.of(
                    "status", response.code()
            ));

            // Add metadata to the trace
//            subsegment.putAnnotation("method", request.method());
//            subsegment.putAnnotation("url", request.url().toString());
//            subsegment.putMetadata("response_code", response.code());
//            subsegment.putMetadata("request", request);
//            subsegment.putMetadata("response", response);

            return response;
        } catch (Exception e) {
            segment.setError(true);
            segment.addException(e);
            segment.putMetadata("error", e.getMessage());
            httpMap.put("response", Map.of(
                    "status", 500
            ));
            throw e;
        } finally {
            segment.setHttp(httpMap);
//            AWSXRay.endSubsegment();
            AWSXRay.endSegment();
        }
    }
}

