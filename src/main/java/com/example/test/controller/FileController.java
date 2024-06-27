package com.example.test.controller;

import com.example.test.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','DIRECTOR')")
    @GetMapping("/downloadTester")
       public ResponseEntity<Resource> getAllTesterExelFile() throws IOException {
              return fileService.getAllTesterExelFile();
       }
}
