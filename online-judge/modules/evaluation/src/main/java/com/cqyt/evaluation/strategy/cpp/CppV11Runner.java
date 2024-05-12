package com.cqyt.evaluation.strategy.cpp;

import com.cqyt.core.entity.Result;
import com.cqyt.evaluation.pojo.entity.CodeRunResult;
import com.cqyt.evaluation.strategy.LanguageRunner;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class CppV11Runner implements LanguageRunner {


    private static final String WINDOWS_COMPILE_DIR = "D:/code/test/compile";
    private static final String LINUX_COMPILE_DIR = "/sandbox";

    private static final int BUFFER_SIZE = 1024;

    @Override
    public Result runCode(String code) {
        try {


            // 执行任务在沙盒中
            CodeRunResult runResult = executeTaskInSandbox(code);

            // 成功执行，返回结果
            return Result.ok().data("out", runResult.getOutput());
        } catch (IOException e) {
            e.printStackTrace();
            // 发生异常，返回错误结果
            return Result.error().message("执行代码时发生错误：" + e.getMessage());
        }
    }

    private static CodeRunResult executeTaskInSandbox(String code) throws IOException {
        // 将代码写入文件
        String fileName = "code.cpp";
        File file = new File(WINDOWS_COMPILE_DIR + "/" + fileName);
        // 确保目录存在，如果不存在则创建
        file.getParentFile().mkdirs();

        // 创建文件
        file.createNewFile();

        // 写入文件
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(code);
        fileWriter.close();
        // 构建 Docker 命令
        String dockerCommand = "docker run --rm ";
        dockerCommand += "--read-only ";
        dockerCommand += "--network none ";
        dockerCommand += "--user nobody "; // 使用非特权用户
        dockerCommand += "--cpus 0.5 "; // 限制 CPU 资源
        dockerCommand += "--memory 512m "; // 限制内存资源
        dockerCommand += "-v " + WINDOWS_COMPILE_DIR + ":" + LINUX_COMPILE_DIR + " ";
        dockerCommand += "cpp11 "; // 你的沙盒化 Docker 镜像名

        String exe = "main";
        // 添加任务文件名作为参数
        dockerCommand += "sh -c  \"cd /sandbox && g++ -o " + exe + " " + fileName + " && ./" + exe + "\"";

        // 执行 Docker 命令
        Process process = Runtime.getRuntime().exec(dockerCommand);

        // 处理容器的输出
        // 例如，你可以读取容器的输出流并打印到控制台
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        // 可以根据需要，将输出结果返回或进行其他处理
        return new CodeRunResult(true, "运行成功", output.toString());
    }
}
