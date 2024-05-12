# 使用官方的 ubuntu 作为基础镜像
FROM ubuntu:latest

# 更新 apt 软件源并安装编译工具和其他必要的软件包
RUN apt-get update && apt-get install -y \
    build-essential \
    && rm -rf /var/lib/apt/lists/*

# 设置工作目录
WORKDIR /app
