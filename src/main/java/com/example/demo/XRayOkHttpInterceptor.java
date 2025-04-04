package com.example.demo;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.entities.Segment;
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
            subsegment.putMetadata("request", request);
            subsegment.putMetadata("response", response);

            return response;
        } catch (Exception e) {
            subsegment.setError(true);
            subsegment.addException(e);
            throw e;
        } finally {
            AWSXRay.endSubsegment();
        }
    }
}

