package com.rnd.springboot3.service;

import com.rnd.springboot3.entity.Car;
import com.rnd.springboot3.entity.ReportDetail;
import com.rnd.springboot3.repository.CarRepository;
import com.rnd.springboot3.repository.ReportDetailRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class ReportDetailServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private ReportDetailRepository reportDetailRepository;

    @Mock
    private TaskExecutor generateExecutor;

    @Mock
    private Environment env;

    @InjectMocks
    private ReportDetailService reportDetailService;


    static final String storageUrl = "D:/gli/storage/";

    @Test
    @DisplayName("should return success generate service")
    public void generate() throws ParseException, IOException {
        String createdDate = "2023-04-01";
        String manufactur = "Toyota";
        ReportDetail reportDetail = ReportDetail.builder()
                .createdDate(new Date())
                .status("GENERATED")
                .build();
        when(env.getProperty("download.storage")).thenReturn(storageUrl);
        reportDetailRepository.save(reportDetail);
        String result = reportDetailService.generate(createdDate, manufactur);
        assertEquals("SUCCESS", result);
    }


    @Test
    @DisplayName("should return success generate_nullParam service")
    public void generate_nullParam() throws ParseException, IOException {
        ReportDetail reportDetail = ReportDetail.builder()
                .createdDate(new Date())
                .status("GENERATED")
                .build();
        when(env.getProperty("download.storage")).thenReturn(storageUrl);
        reportDetailRepository.save(reportDetail);
        String result = reportDetailService.generate(null, null);
        assertEquals("SUCCESS", result);
    }

    @Test
    @DisplayName("should return success generateExcel service")
    public void generateExcel() throws ParseException, IOException {
        ReportDetail reportDetail = new ReportDetail();
        String createDate = "01/Apr/2024 12:00:00";
        String manufactur = "Toyota";

        SimpleDateFormat DB_FORMATTER = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");

        Date date = DB_FORMATTER.parse(createDate);
        when(env.getProperty("download.storage")).thenReturn(storageUrl);
        when(carRepository.getCarList(date, manufactur)).thenReturn(carList(date));
        ReportDetail result = reportDetailService.generateExcel(reportDetail, createDate, manufactur);
        assertNotNull(result);
    }

    @Test
    @DisplayName("should return success generateExcel_null_Parameter service")
    public void generateExcel_null_Parameter() throws ParseException, IOException {
        ReportDetail reportDetail = new ReportDetail();
        String createDate = "01/Apr/2024 12:00:00";
        SimpleDateFormat DB_FORMATTER = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
        Date date = DB_FORMATTER.parse(createDate);

        when(env.getProperty("download.storage")).thenReturn(storageUrl);
        when(carRepository.findAll()).thenReturn(carList(date));
        ReportDetail result = reportDetailService.generateExcel(reportDetail, null, null);
        assertNotNull(result);
    }

    private List<Car> carList(Date date) {
        List<Car> carList = new ArrayList<>();
        carList.add(car("BMW", "X5", "SUV", date));
        carList.add(car("Toyota", "Camry", "Sedan", date));
        carList.add(car("Honda", "CR-V", "SUV", date));
        return carList;
    }

    private Car car(String manufactur, String model, String type, Date date) {
        return Car.builder()
                .manufactur(manufactur)
                .model(model)
                .type(type)
                .createdDate(date)
                .build();
    }
}
