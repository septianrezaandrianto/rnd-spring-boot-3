package com.rnd.completablefuture.controller;

import com.rnd.completablefuture.dto.ReportRequestDto;
import com.rnd.completablefuture.dto.ReportResponseDto;
import com.rnd.completablefuture.entity.ReportDetail;
import com.rnd.completablefuture.service.ReportDetailService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/report")
public class ReportDetilController {

    private final SimpleDateFormat DB_FORMATTER = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

    @Autowired
    private ReportDetailService reportDetailService;

    @Autowired
    private HttpServletResponse response;


    @GetMapping(value = "/generate" , produces = {MediaType.APPLICATION_JSON_VALUE},
        consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String genererate(@RequestParam(value = "createdDate", required = false)String createdDate,
                                @RequestParam(value = "manufactur", required = false)String manufactur) throws ParseException, IOException {
        return reportDetailService.generate(createdDate, manufactur);
    }
}
