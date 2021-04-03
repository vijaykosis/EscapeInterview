package com.example.EscapeInterview.controller;

import com.example.EscapeInterview.model.Distance;
import com.example.EscapeInterview.service.DistanceCalculateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class DistanceCalculateController {
    private static final Logger logger = LoggerFactory.getLogger(DistanceCalculateController.class);


    @Autowired
    private DistanceCalculateService service;

    @GetMapping("/getShortestDistance")
    public Collection<Distance> getShortestDistance(@RequestParam String city) throws Exception {
       logger.info("request received at getShortestDistance()..{}",city);
        return service.checkDistance(city);
    }

}
