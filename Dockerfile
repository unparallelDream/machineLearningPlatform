FROM openjdk:17


#将jar包添加到容器中
ADD machineLearningPlatform-0.0.1-SNAPSHOT.jar machineLearningPlatform.jar

#对外暴露端口
EXPOSE 8080
# 运行jar包 
CMD ["java","-jar","-Duser.timezone=Asia/Shanghai","machineLearningPlatform.jar"]


