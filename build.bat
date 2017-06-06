@echo off
REM set JAVA_HOME=D:\tool\jdk1.8.0_20\
REM set ANT_HOME=D:\tool\apache-ant-1.9.4\

echo %JAVA_HOME%
echo %ANT_HOME%


cmd /k ant -buildfile build.xml gen-appCode-service