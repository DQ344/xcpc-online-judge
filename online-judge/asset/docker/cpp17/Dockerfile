# 使用官方的 ubuntu 作为基础镜像
FROM ubuntu:latest

# 更新 apt 软件源并安装编译工具和其他必要的软件包
RUN apt-get update && apt-get install -y \
    build-essential \
    && rm -rf /var/lib/apt/lists/*

# 设置工作目录
WORKDIR /app

# 将宿主机中的代码复制到容器中的 /app 目录下
COPY ./code.cpp /app

# 使用 g++ 编译器编译代码，指定使用 C++11 标准
RUN g++ -std=c++11 -o main code.cpp

# 设置容器启动时执行的命令
CMD ["./main"]
