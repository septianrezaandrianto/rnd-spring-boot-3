package com.rnd.completablefuture.controller;

import com.rnd.completablefuture.service.ReportDetailService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/report")
public class ReportDetilController {

    @Autowired
    private ReportDetailService reportDetailService;

    @GetMapping(value = "/generate" /*, produces = {MediaType.APPLICATION_JSON_VALUE},
        consumes = {MediaType.APPLICATION_JSON_VALUE} */)
    public String genererate(@RequestParam(value = "createdDate", required = false)String createdDate,
                                @RequestParam(value = "manufactur", required = false)String manufactur) throws ParseException, IOException {
        return reportDetailService.generate(createdDate, manufactur);
    }
}
