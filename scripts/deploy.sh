#!/usr/bin/env bash

REPOSITORY=/home/ubuntu/app
PROJECT_BACKEND=cookle_backend
# PROJECT_FRONTEND=프론트엔드_디렉토리명

echo "> 백엔드 프로젝트 디렉토리로 이동"

cd $REPOSITORY/$PROJECT_BACKEND/

echo "> git pull 백엔드 프로젝트"

git pull

echo "> build 백엔드 프로젝트"

./gradlew build

echo "> 기본 디렉토리로 이동"

cd $REPOSITORY

echo "> Build 파일 복사"

cp $REPOSITORY/$PROJECT_BACKEND/build/libs/*.jar $REPOSITORY/

# 프론트엔드 빌드

# CURRENT_PID=$(pgrep -fla java | grep hayan | awk '{print $1}')
CURRENT_PID=$(pgrep -fl cookle_backend | grep java | awk '{print $1}')

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo "현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 새 애플리케이션 배포"

# JAR_NAME=$(ls -tr $REPOSITORY/deploy/*SNAPSHOT.jar | tail -n 1)
JAR_NAME=$(ls $REPOSITORY/ | grep cookle_backend | tail -n 1)

echo "> JAR NAME: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

# nohup java -jar -Duser.timezone=Asia/Seoul $JAR_NAME >> $REPOSITORY/nohup.out 2>&1 &
nohup java -jar $REPOSITORY/$JAR_NAME &
