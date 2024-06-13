FROM ubuntu:latest
LABEL authors="jiny"

# Java 설치
RUN apt-get update && \
    apt-get install -y openjdk-11-jdk && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# 작업 디렉토리 설정
WORKDIR /app

# 로컬에서 빌드한 JAR 파일을 컨테이너에 복사
COPY build/libs/application-0.0.1-SNAPSHOT.jar ./app.jar

# 애플리케이션 실행 명령어
ENTRYPOINT ["java", "-jar", "./app.jar"]

