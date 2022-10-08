package site.easystartup.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin(origins = {"212.76.129.195:9990", "212.76.129.195"})
@Controller
public class MainController {
    @GetMapping("/")
    public String main() {
        return "/main";
    }
}
