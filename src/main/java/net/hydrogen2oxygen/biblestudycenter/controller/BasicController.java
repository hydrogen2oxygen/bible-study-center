package net.hydrogen2oxygen.biblestudycenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BasicController {

    @GetMapping("/test")
    public String test() {
        return "test.html";
    }
}
