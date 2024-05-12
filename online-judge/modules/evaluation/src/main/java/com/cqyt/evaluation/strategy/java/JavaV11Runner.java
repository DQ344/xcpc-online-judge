package com.cqyt.evaluation.strategy.java;

import com.cqyt.core.entity.Result;
import com.cqyt.evaluation.strategy.LanguageRunner;
import org.springframework.stereotype.Component;

@Component
public class JavaV11Runner implements LanguageRunner {
    @Override
    public Result runCode(String code) {
        // 实现 Java 代码的执行逻辑
        return Result.ok().data("key", "valueObject for Java");
    }
}