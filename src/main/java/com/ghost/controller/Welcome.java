package com.ghost.controller;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ghost on 2017/8/10.
 */
@RestController
@EnableAutoConfiguration
public class Welcome {
    private static Logger logger = Logger.getLogger(Welcome.class);

    @RequestMapping("/")
    String home() {
        return "hello word1!";
    }

    //RESTFUL
    @RequestMapping(value = "/welcome/{user}", method = RequestMethod.GET)
    public String welcome(@PathVariable String user) {
        return "hello " + user + "!";
    }

    @RequestMapping("/hello")
    public String hello(@RequestParam(value = "name", required = false, defaultValue = "any") String name) {

        return "hello " + name;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Welcome.class, args);
    }
}
