package com.cqyt.evaluation.strategy.javascript;

import com.cqyt.core.entity.Result;
import com.cqyt.evaluation.strategy.LanguageRunner;
import org.springframework.stereotype.Component;

@Component
public class JavaScriptVLatestRunner implements LanguageRunner {
    @Override
    public Result runCode(String code) {
        // 实现 C++ 11 版本的运行逻辑
        return Result.ok().data("key", "valueObject for C++ 11");
    }
}