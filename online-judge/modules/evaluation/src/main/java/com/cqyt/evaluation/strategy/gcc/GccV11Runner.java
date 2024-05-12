package com.cqyt.evaluation.strategy.gcc;

import com.cqyt.core.entity.Result;
import com.cqyt.evaluation.strategy.LanguageRunner;
import org.springframework.stereotype.Component;

@Component
public class GccV11Runner implements LanguageRunner {
    @Override
    public Result runCode(String code) {
        // 实现 C++ 代码的执行逻辑
        return Result.ok().data("key", "valueObject for C++");
    }
}





// 根据需要添加其他语言的策略类
