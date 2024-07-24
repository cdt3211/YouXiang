package com.cupk.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface CosService {
    String uploadFile(MultipartFile file) throws IOException;
    File convertMultiPartToFile(MultipartFile file) throws IOException;
}
