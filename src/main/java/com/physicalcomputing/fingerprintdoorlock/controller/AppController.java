package com.physicalcomputing.fingerprintdoorlock.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AppController {

    @GetMapping("/dashboard")
    private String showDashboard() {
        return "devices/dashboard";
    }
}
