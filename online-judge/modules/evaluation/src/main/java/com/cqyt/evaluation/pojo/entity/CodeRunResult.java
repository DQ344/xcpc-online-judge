package com.cqyt.evaluation.pojo.entity;

import lombok.*;

/**
 * @author qlh
 * date  2024/05/06 14:46
 * @description 代码运行后统一返回结果类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeRunResult {
    // 是否成功运行
    private boolean success;
    // 运行消息（一般是报错消息）
    private String message;
    // 输出结果
    private String output;
}
