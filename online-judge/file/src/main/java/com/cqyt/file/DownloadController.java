package com.cqyt.file;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;

@RestController
public class DownloadController {

    @GetMapping("download")
    public ResponseEntity<byte[]> download(@RequestHeader(value = "Range", required = false) String rangeHeader) {
        try {
            File file = new File("D:\\相册\\终末地\\终末地01.png");

            // 获取文件长度
            long fileSize = file.length();

            // 如果客户端请求了范围，则进行部分内容传输
            if (rangeHeader != null) {
                return handlePartialContent(file, fileSize, rangeHeader);
            } else {
                // 如果没有请求范围，则返回整个文件
                return handleFullContent(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 处理异常
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private ResponseEntity<byte[]> handlePartialContent(File file, long fileSize, String rangeHeader) throws IOException {
        String[] ranges = rangeHeader.substring("bytes=".length()).split("-");
        long startRange = Long.parseLong(ranges[0]);
        long endRange = ranges.length > 1 ? Long.parseLong(ranges[1]) : fileSize - 1;

        long contentLength = endRange - startRange + 1;

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set("Content-Range", "bytes " + startRange + "-" + endRange + "/" + fileSize);
        headers.setContentLength(contentLength);

        // 读取文件内容并返回部分内容
        byte[] fileContent = Files.readAllBytes(file.toPath());
        byte[] partialContent = new byte[(int) contentLength];
        System.arraycopy(fileContent, (int) startRange, partialContent, 0, (int) contentLength);

        return new ResponseEntity<>(partialContent, headers, HttpStatus.PARTIAL_CONTENT);
    }

    private ResponseEntity<byte[]> handleFullContent(File file) throws IOException {
        byte[] fileContent = Files.readAllBytes(file.toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", file.getName());
        headers.setContentLength(fileContent.length);

        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }
}
