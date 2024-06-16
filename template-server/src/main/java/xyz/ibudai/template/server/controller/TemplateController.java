package xyz.ibudai.template.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/template")
public class TemplateController {

    @GetMapping("testing")
    public String testing() {
        return "Hello World!";
    }
}
