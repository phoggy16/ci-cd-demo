package com.example.demo;

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
        return "CI CD Working fine";
    }

    @GetMapping("pr-test")
    public String testPr(){
        return "pr-test";
    }
}
