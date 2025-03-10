# 베이스 이미지
FROM openjdk:17-jdk-slim

# 작업 디렉토리 생성
WORKDIR /app

# JAR 파일을 컨테이너로 복사
COPY build/libs/cutly-0.0.1-SNAPSHOT.jar cutly.jar

# 컨테이너 실행 시 명령어
CMD ["java", "-jar", "cutly.jar"]

# 컨테이너 사용 포트
EXPOSE 8080