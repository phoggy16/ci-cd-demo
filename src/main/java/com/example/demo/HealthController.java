package com.example.demo;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health-check")
public class HealthController {

    @GetMapping
    public String health(){
        return "OK";
    }
}
