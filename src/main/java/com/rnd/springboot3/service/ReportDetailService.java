package com.rnd.springboot3.service;

import com.google.gson.Gson;
import com.rnd.springboot3.entity.Car;
import com.rnd.springboot3.entity.ReportDetail;
import com.rnd.springboot3.repository.CarRepository;
import com.rnd.springboot3.repository.ReportDetailRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ReportDetailService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ReportDetailService.class);
    private final static Gson gson = new Gson();

    private final SimpleDateFormat DB_FORMATTER = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
    private final SimpleDateFormat REPORT_FORMATTER = new SimpleDateFormat("ddMMyyyy_HHmmss");

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ReportDetailRepository reportGeneratedRepository;

    @Autowired
    private TaskExecutor generateExecutor;

    @Autowired
    private Environment env;

    public String generate(String createdDate, String manufactur) throws ParseException, IOException {
        String createdDateReq;
        String manufactureReq;

        if(createdDate != null && manufactur != null) {
            createdDateReq = createdDate;
            manufactureReq = manufactur;
        } else {
            manufactureReq = null;
            createdDateReq = null;
        }

        ReportDetail reportDetail = new ReportDetail();
        reportDetail.setCreatedDate(new Date());
        reportDetail.setStatus("GENERATED");

        generateExecutor.execute(()-> {
            try {
                generateExcel(reportDetail, createdDateReq, manufactureReq);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        reportGeneratedRepository.save(reportDetail);
        return "SUCCESS";
    }


    @Async
    public ReportDetail generateExcel(ReportDetail reportDetail, String createdDate, String manufactur) throws ParseException, IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        List<Car> carList;
        if(createdDate != null && manufactur != null) {
            Date reqeustDate = DB_FORMATTER.parse(createdDate);
            carList = carRepository.getCarList(reqeustDate, manufactur);
        } else {
            carList = carRepository.findAll();
        }

        LOGGER.info("{}{} CAR LIST {}{} " + gson.toJson(carList));

        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet 1");
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();

        writeHeader(workbook, sheet, style, font);
        writeBody(carList, sheet, style, font);
        workbook.write(bos);

        String currentDateTime = REPORT_FORMATTER.format(new Date());
        String fileName = "Report-".concat(currentDateTime)
                .concat(".xlsx");

        String path = env.getProperty("download.storage");
        FileOutputStream fos = new FileOutputStream(new File(path + fileName));
        bos.writeTo(fos);
        bos.close();

        reportDetail.setStatus("SUCCESS");
        reportDetail.setFileName(fileName);
        reportDetail.setFilePath(path + fileName);
        reportGeneratedRepository.save(reportDetail);
        return reportDetail;
    }

    private void writeBody(List<Car> carList, Sheet sheet, CellStyle style, Font font) {
        int rowIndex = 1;
        int number = 1;
        style.setFont(font);
        for(Car car: carList) {
            LOGGER.error("DATA Ke - " + number + " : " + gson.toJson(car));
            int columnCount = 0;
            Row row = sheet.createRow(rowIndex++);
            createCell(sheet, row, columnCount++, number, style);
            createCell(sheet, row, columnCount++, car.getId(), style);
            createCell(sheet, row, columnCount++, car.getManufactur(), style);
            createCell(sheet, row, columnCount++, car.getModel(), style);
            createCell(sheet, row, columnCount++, car.getType(), style);
            createCell(sheet, row, columnCount++, DB_FORMATTER.format(car.getCreatedDate()), style);
            number++;
        }
    }

    private void writeHeader(HSSFWorkbook workbook, Sheet sheet, CellStyle style, Font font) {
        Row row = sheet.createRow(0);
        font.setBold(true);
        style.setFont(font);

        for (int i = 0; i < headers().length; i++) {
            createCell(sheet, row, i, headers()[i], style);
        }
    }

    private void createCell(Sheet sheet, Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }

    private String[] headers() {
        return new String[] {
                "NO",
                "ID",
                "MANUFACTUR",
                "MODEL",
                "TYPE",
                "CREATED_DATE"
        };
    }

}
