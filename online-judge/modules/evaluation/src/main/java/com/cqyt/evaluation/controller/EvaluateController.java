package com.cqyt.evaluation.controller;

import com.cqyt.core.entity.Result;
import com.cqyt.core.utils.ThumbnailUtil;
import com.cqyt.evaluation.service.EvaluateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;



@RestController
@RequestMapping("/evaluate")
public class EvaluateController {

    @Autowired
    private EvaluateService evaluateService;

    @PostMapping("/run/{language}")
    public Result run(@RequestHeader("user_id") String userId,
                      @PathVariable String language,
                      @RequestBody String code) {
         return evaluateService.run(userId, language, code);
    }




    @GetMapping("download")
    public ResponseEntity<byte[]> download() {
        try {
            File file = new File("D:\\相册\\终末地\\终末地01.png");

            File compressedFile = ThumbnailUtil.compression(file, 0.67);

            byte[] fileContent = org.apache.commons.io.FileUtils.readFileToByteArray(compressedFile);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", compressedFile.getName());

            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            // 处理异常
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
