package com.cqyt.evaluation.constant;

import com.cqyt.evaluation.strategy.gcc.*;
import com.cqyt.evaluation.strategy.cpp.*;
import com.cqyt.evaluation.strategy.csharp.*;
import com.cqyt.evaluation.strategy.go.*;
import com.cqyt.evaluation.strategy.java.*;
import com.cqyt.evaluation.strategy.javascript.*;
import com.cqyt.evaluation.strategy.python.*;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CodeLanguageConstant {
    // 语言简称
    public static final String GCC_V11 = "gcc11";
    public static final String CPP_V17 = "cpp17";
    public static final String CPP_V11 = "cpp11";
    public static final String JAVA_V8 = "java8";
    public static final String JAVA_V11 = "java11";
    public static final String JAVA_V17 = "java17";
    public static final String PYTHON_V2 = "python2";
    public static final String PYTHON_V3 = "python3";
    public static final String GO_LATEST = "go-latest";
    public static final String C_SHARP_LATEST = "csharp-latest";
    public static final String JAVA_SCRIPT_LATEST = "javascript-latest";

    // 运行器类名
    public static final String GCC_V11_RUNNER = GccV11Runner.class.getSimpleName();
    public static final String CPP_V17_RUNNER = CppV17Runner.class.getSimpleName();
    public static final String CPP_V11_RUNNER = CppV11Runner.class.getSimpleName();
    public static final String JAVA_V8_RUNNER = JavaV8Runner.class.getSimpleName();
    public static final String JAVA_V11_RUNNER = JavaV11Runner.class.getSimpleName();
    public static final String JAVA_V17_RUNNER = JavaV17Runner.class.getSimpleName();
    public static final String PYTHON_V2_RUNNER = PythonV2Runner.class.getSimpleName();
    public static final String PYTHON_V3_RUNNER = PythonV3Runner.class.getSimpleName();
    public static final String GO_LATEST_RUNNER = GoVLatestRunner.class.getSimpleName();
    public static final String C_LATEST_SHARP_RUNNER = CsharpVLatestRunner.class.getSimpleName();
    public static final String JAVA_SCRIPT_LATEST_RUNNER = JavaScriptVLatestRunner.class.getSimpleName();

    // 语言简称到类名的映射
    public static final Map<String, String> LANGUAGE;
    // 类名到语言简称的映射
    public static final Map<String, String> RUNNER;

    // 初始化映射关系
    static {
        // 创建不可变的映射
        Map<String, String> runner = new HashMap<>();
        Map<String, String> language = new HashMap<>();

        // 添加映射关系
        runner.put(GCC_V11, GCC_V11_RUNNER);
        runner.put(CPP_V11, CPP_V11_RUNNER);
        runner.put(CPP_V17, CPP_V17_RUNNER);
        runner.put(JAVA_V8, JAVA_V8_RUNNER);
        runner.put(JAVA_V11, JAVA_V11_RUNNER);
        runner.put(JAVA_V17, JAVA_V17_RUNNER);
        runner.put(PYTHON_V2, PYTHON_V2_RUNNER);
        runner.put(PYTHON_V3, PYTHON_V3_RUNNER);
        runner.put(GO_LATEST, GO_LATEST_RUNNER);
        runner.put(C_SHARP_LATEST, C_LATEST_SHARP_RUNNER);
        runner.put(JAVA_SCRIPT_LATEST, JAVA_SCRIPT_LATEST_RUNNER);

        // 通过反转映射关系，创建从类名到语言简称的映射
        for (Map.Entry<String, String> entry : runner.entrySet()) {
            language.put(entry.getValue(), entry.getKey());
        }

        // 使映射关系不可修改
        RUNNER = Collections.unmodifiableMap(runner);
        LANGUAGE = Collections.unmodifiableMap(language);
    }
}
