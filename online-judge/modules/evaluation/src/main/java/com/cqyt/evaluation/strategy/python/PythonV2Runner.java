package com.cqyt.evaluation.strategy.python;

import com.cqyt.core.entity.Result;
import com.cqyt.evaluation.strategy.LanguageRunner;
import org.springframework.stereotype.Component;

@Component
public class PythonV2Runner implements LanguageRunner {
    @Override
    public Result runCode(String code) {
        // 实现 Python 代码的执行逻辑
        return Result.ok().data("key", "valueObject for Python");
    }
}