package com.hidq.products.controllers;

import com.hidq.products.service.MinioServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
@Slf4j
public class ImagesController {
    @Autowired
    MinioServiceImpl minioService;

    @PostMapping("/save")
    @Parameter(name = "file", description = "o arquivo a ser salvo deve ser enviado via multipart form")
    String saveImage(@RequestParam("file") MultipartFile file) throws Exception {
        log.info("ImagesController.saveImage entry: {}", file.getOriginalFilename());
        return minioService.save(file);
    }
}
