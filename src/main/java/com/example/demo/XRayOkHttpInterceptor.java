package com.example.demo;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.entities.Subsegment;
import okhttp3.*;

import java.io.IOException;

public class XRayOkHttpInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Subsegment subsegment = AWSXRay.beginSubsegment("OkHttp Call: " + request.url());

        try {
            // Execute the request
            Response response = chain.proceed(request);

            // Add metadata to the trace
            subsegment.putAnnotation("method", request.method());
            subsegment.putAnnotation("url", request.url().toString());
            subsegment.putMetadata("response_code", response.code());

            return response;
        } catch (Exception e) {
            subsegment.addException(e);
            throw e;
        } finally {
            AWSXRay.endSubsegment();
        }
    }
}

