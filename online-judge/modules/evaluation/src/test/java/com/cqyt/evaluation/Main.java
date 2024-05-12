package com.cqyt.evaluation;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    private static final String WINDOWS_CLIENT_DIR = "D:/code/test/client";
    private static final String WINDOWS_COMPILE_DIR = "D:/code/test/compile";

    private static final String WINDOWS_RUN_DIR = "D:\\code\\temp\\run\\";

    private static final String LINUX_COMPILE_DIR = "/sandbox";


    private static final int BUFFER_SIZE = 1024;

    private static final BlockingQueue<String> taskQueue = new LinkedBlockingQueue<>();

    /**
     * 沙盒策略
     * @param args
     */
    public static void main(String[] args) {
        // 添加任务到队列
        taskQueue.add("main.cpp");

        // 从队列中取出任务并执行
        while (!taskQueue.isEmpty()) {
            String fileName = taskQueue.poll();
            try {
                // 使用 Docker 容器进行沙盒化
                executeTaskInSandbox(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            } 
        }
    }

    private static void executeTaskInSandbox(String fileName) throws IOException {
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
        System.out.println(dockerCommand);
        Process process = Runtime.getRuntime().exec(dockerCommand);

        // 处理容器的输出
        // 例如，你可以读取容器的输出流并打印到控制台
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        System.out.println(output);

    }


    private static void copyFile(String sourcePath, String destinationPath) throws IOException {
        File sourceFile = new File(sourcePath);
        File destinationFile = new File(destinationPath);

        // 检查源文件是否存在
        if (!sourceFile.exists()) {
            throw new FileNotFoundException("Source file does not exist.");
        }

        // 检查目标目录是否存在，如果不存在，则创建目录
        File destinationDir = destinationFile.getParentFile();
        if (destinationDir != null && !destinationDir.exists()) {
            destinationDir.mkdirs();
        }

        try (InputStream inputStream = new FileInputStream(sourceFile);
             OutputStream outputStream = new FileOutputStream(destinationFile)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    private static void createDirectories(String filePath){
        try {
            Path path = Paths.get(filePath).getParent();
            if (path != null) {
                Files.createDirectories(path); // 创建所有不存在的文件夹路径
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static void deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("File deleted successfully: " + fileName);
            } else {
                System.out.println("Failed to delete the file: " + fileName);
            }
        } else {
            System.out.println("File does not exist: " + fileName);
        }
    }

    /**
     * 去后缀
     * @param filePath
     * @return
     */
    private static String removeFileExtension(String filePath){
        return filePath.substring(0, filePath.lastIndexOf("."));
    }


    private static String compileCpp(String fileName) {
        String compileFile = WINDOWS_COMPILE_DIR + fileName;
        String runFileDir = WINDOWS_RUN_DIR + removeFileExtension(fileName);
        createDirectories(compileFile);
        try {
            ProcessBuilder builder = new ProcessBuilder("cmd", "/c", "g++", "-o", runFileDir, compileFile);
            builder.directory(new File(WINDOWS_COMPILE_DIR));
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            if (process.waitFor() != 0) {
                return output.toString();
            }
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }finally {
            deleteFile(compileFile);
        }
    }

    private static String runCpp(String fileName, String input) {
        String runFile = WINDOWS_RUN_DIR + removeFileExtension(fileName) + ".exe";
        createDirectories(runFile);
        try {
            ProcessBuilder builder = new ProcessBuilder("cmd", "/c", runFile);
            builder.directory(new File(WINDOWS_RUN_DIR));
            Process process = builder.start();

            // 如果需要输入数据，将其写入到进程的输入流
            if(input != null && input.isEmpty()){
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                writer.write(input);
                writer.flush();
                writer.close();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }finally {
            deleteFile(runFile);
        }
    }

}
