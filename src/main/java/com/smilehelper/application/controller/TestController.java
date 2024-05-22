package com.smilehelper.application.controller;

import com.smilehelper.application.annotation.WebAdapter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Test API", description = "임시 Test API 입니다.")
@WebAdapter
@RestController
@RequestMapping(value = "/api/v1")
public class TestController {

    @RequestMapping(value = "/test")
    public String test() {
        return "Hello World!";
    }
}
