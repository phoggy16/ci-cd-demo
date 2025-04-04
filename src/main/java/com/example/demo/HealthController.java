package com.example.demo;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.entities.Segment;
import com.amazonaws.xray.entities.Subsegment;
import com.example.demo.exception.GarageException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("health")
    public String health(){
        return "OK";
    }

    @GetMapping("test-ci-cd")
    public String testCiCi(){
        throw new GarageException("Invalid test-ci-cd", HttpStatus.BAD_REQUEST);

//        return "CI CD Working fine";
    }

    @GetMapping("pr-test")
    public String testPr(@RequestParam String url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new XRayOkHttpInterceptor())
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();



//        Segment segment = AWSXRay.beginSegment("MyApplication");
//        try {
//            Request request = new Request.Builder()
//                    .url(url)
//                    .get()
//                    .build();
//
//            Response response = client.newCall(request).execute();
//            return response.body().string();
//        } catch (Exception e) {
//            segment.setError(true);
//            segment.putMetadata("error", e);
//            throw e;
//        } finally {
//            AWSXRay.endSegment();
//        }
    }
}
