package com.example.test.service;

import com.example.test.module.Tester;
import com.example.test.repository.TesterRepository;
import lombok.RequiredArgsConstructor;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private final TesterRepository testerRepository;
    public ResponseEntity<Resource> getAllTesterExelFile() throws IOException {
        List<Tester> all = testerRepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Tester Data");
        Row headerRow = sheet.createRow(0);
        List<String> headers = Arrays.asList("Ismi", "Familiyasi", "Otasinig ismi", "ID Raqami",  "Role", "Position");
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers.get(i));
        }
        int rowNumber = 1;
        for (Tester tester : all) {
            Row row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue(tester.getFirstName());
            row.createCell(1).setCellValue(tester.getMiddleName());
            row.createCell(2).setCellValue(tester.getLastName());
            row.createCell(3).setCellValue(tester.getIdNumber());
            row.createCell(5).setCellValue(tester.getRole().name());
            row.createCell(5).setCellValue(tester.getPosition().getName());
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        try (FileOutputStream fileOut = new FileOutputStream("user_data.xlsx")) {
            workbook.write(fileOut);
        // Faylni brauzerga yuborish
        ByteArrayResource resource = new ByteArrayResource(byteArrayOutputStream.toByteArray());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=user_data.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(byteArrayOutputStream.size())
                .body(resource);
    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body(null);
    }

    }
}
