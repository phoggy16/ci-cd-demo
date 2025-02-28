package com.example.demo;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("health")
    public String health(){
        return "OK";
    }

    @GetMapping("test")
    public String testCiCi(){
        return "CI CD Working fine";
    }
}
