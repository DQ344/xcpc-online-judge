package com.cqyt.evaluation.service.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.cqyt.core.entity.Result;
import com.cqyt.evaluation.constant.CodeLanguageConstant;
import com.cqyt.evaluation.service.EvaluateService;
import com.cqyt.evaluation.strategy.LanguageRunner;
import org.springframework.stereotype.Service;

@Service
public class EvaluateServiceImpl implements EvaluateService {

    /**
     * 语言简称与运行器类名的映射关系
     */
    private final Map<String, LanguageRunner> languageRunnerMap;

    /**
     * 构造函数，接受一个 LanguageRunner 类型的列表作为参数
     *
     * @param languageRunners LanguageRunner 类型的列表，包含了各种语言的运行器实例
     */
    public EvaluateServiceImpl(List<LanguageRunner> languageRunners) {
        // 使用流操作将 languageRunners 列表转换为一个 Map
        this.languageRunnerMap = languageRunners.stream()
                // 将 LanguageRunner 实例映射为键值对，键是运行器类的简单名称，值是语言运行器实例
                .collect(Collectors.toMap(
                        // 键的生成函数，返回运行器类的简单名称
                        runner -> runner.getClass().getSimpleName(),
                        // 值的生成函数，直接返回语言运行器实例本身
                        Function.identity()
                ));
    }

    /**
     * 执行给定语言的代码
     *
     * @param userId   用户ID
     * @param language 语言简称
     * @param code     待执行的代码
     * @return 执行结果
     */
    @Override
    public Result run(String userId, String language, String code) {
        // 根据语言简称获取对应的运行器类名
        String runner = CodeLanguageConstant.RUNNER.get(language);

        if (runner == null) {
            // 如果找不到对应的语言运行器，则返回错误信息
            return Result.error().message("不支持的语言：" + language);
        }

        // 根据运行器类名获取对应的语言运行器实例
        LanguageRunner app = languageRunnerMap.get(runner);

        if (app != null) {
            // 如果找到了对应的语言运行器，则使用它执行代码
            return app.runCode(code);
        } else {
            // 如果找不到对应的语言运行器，则返回错误信息
            return Result.error().message("不支持的语言：" + language);
        }
    }
}
