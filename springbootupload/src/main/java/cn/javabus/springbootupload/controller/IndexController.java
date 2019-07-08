package cn.javabus.springbootupload.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ouzhx on 2019/7/8.
 */
@RestController
public class IndexController {
    @GetMapping("/")
    public String hello() {
        return "hello world!";
    }
}
