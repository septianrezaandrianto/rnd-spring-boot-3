package com.rnd.springboot3.controller;

import com.rnd.springboot3.dto.GuitarRequest;
import com.rnd.springboot3.dto.GuitarResponse;
import com.rnd.springboot3.service.GuitarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/guitar")
@Slf4j
public class GuitarController {

    @Autowired
    private GuitarService guitarService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody GuitarRequest guitarRequest) {
        return ResponseEntity.ok(guitarService.create(guitarRequest));
    }

    @GetMapping("/getDetailGuitarUsingJpa")
    public ResponseEntity<Map<String, Object>> getDetailGuitarUsingJpa(@RequestParam("id") long id) {
        return ResponseEntity.ok(guitarService.getDetailGuitarUsingJpa(id));
    }

    @GetMapping("/getDetailGuitarDao")
    public ResponseEntity<Map<String, Object>> getDetailGuitarDao(@RequestParam("id") long id) {
        return ResponseEntity.ok(guitarService.getDetailGuitarDao(id));
    }
}
