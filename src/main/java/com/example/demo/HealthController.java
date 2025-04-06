package com.example.demo;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import com.example.demo.exception.GarageException;
import jakarta.servlet.http.HttpServletRequest;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
public class HealthController {

    @Autowired
    HttpServletRequest httpServletRequest;

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
    @XRayEnabled
    public String testPr(@RequestParam String url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new XRayOkHttpInterceptor())
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();


        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        return response.body().toString();

//        Segment segment = AWSXRay.beginSegment("MyApplication");
//        segment.setOrigin(httpServletRequest.getRequestURI());
//        segment.setUser("Rohit");
//        Map<String, Object> httpMap = new HashMap<>();
//
//        try {
//            Request request = new Request.Builder()
//                    .url(url)
//                    .get()
//                    .build();
//
//            httpMap.put("request", Map.of(
//                    "method", "GET",
//                    "url", url
//            ));
//
//
//            Response response = client.newCall(request).execute();
//
//            httpMap.put("response", Map.of(
//                    "status", response.code()
//            ));
//
//            return response.body().string();
//        } catch (Exception e) {
//            segment.setError(true);
//            httpMap.put("response", Map.of(
//                    "status", 500
//            ));
//            segment.putMetadata("error", e.getMessage());
//            throw e;
//        } finally {
//            segment.setHttp(httpMap);
//            AWSXRay.endSegment();
//        }
    }
}
