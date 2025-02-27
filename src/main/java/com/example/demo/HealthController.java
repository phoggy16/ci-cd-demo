package com.example.demo;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public String health(){
        return "OK";
    }
}
