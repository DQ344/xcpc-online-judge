package com.cqyt.evaluation.strategy.cpp;

import com.cqyt.core.entity.Result;
import com.cqyt.evaluation.strategy.LanguageRunner;
import org.springframework.stereotype.Component;

@Component
public class CppV17Runner implements LanguageRunner {
    @Override
    public Result runCode(String code) {
        // 实现 C++ 17 版本的运行逻辑
        return Result.ok().data("key", "valueObject for C++ 17");
    }
}