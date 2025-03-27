package com.example.demo;

import com.example.demo.exception.GarageException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public String testPr(){
        int j=1/0;
        return "pr-test";
    }
}
