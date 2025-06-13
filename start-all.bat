@echo off
REM 启动前端（新窗口）
start cmd /k "cd frontend && npm run dev"
REM 启动后端（新窗口）
start cmd /k "mvnw spring-boot:run"
