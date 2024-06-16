package xyz.ibudai.template.logic.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/template")
public class TemplateController {

    @GetMapping("testing")
    public String testing() {
        return "Hello World!";
    }
}
