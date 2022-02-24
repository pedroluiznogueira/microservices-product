package com.github.pedroluiznogueira.microservicesproduct.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/log")
public class LogController {

    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @GetMapping("/{number}")
    public String log(@PathVariable Integer number) {
         logger.info("log " + number);
         return "ok " + number;
    }

}
