package com.github.pedroluiznogueira.microservicesproduct.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logs")
public class ProductLogController {

    private static final Logger logger = LoggerFactory.getLogger(ProductLogController.class);

    @GetMapping("/product/{number}")
    public String logProductNumber(@PathVariable Integer number) {
         logger.info("product " + number);
         return "ok " + number;
    }

}
