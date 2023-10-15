
# Machine-Learning-Platform

机器学习平台，可以进行CNN神经网络、生成对抗网络等知识的学习
##### 环境依赖

java 17

mysql 8

redis

#### 本机测试环境配置
1.安装mysql(端口默认3306)

2.安装redis(端口某人6379)

3.将src/resources/application.yml中   spring:profiles:active属性值改为dev，使用开发模式配置

4.配置同目录下application-dev信息，包括数据库url，账号密码和mysql、redis端口

5.导入数据库表信息，将data.sql导入数据库

6.启动项目

#### 接口文档查看
服务器端:<br/> http://101.201.65.86:8080/swagger-ui/index.html

本地启动:<br/> http://localhost:8080/swagger-ui/index.html
##### 项目目录
```
.
├─src  
│  ├─main  
│  │  ├─java  
│  │  │  └─com  
│  │  │      └─platform  
│  │  │          └─machinelearningplatform  
│  │  │              │  MachineLearningPlatformApplication.java  
│  │  │              │    
│  │  │              ├─common  
│  │  │              │      Result.java 与前端约定好的统一返回类型   
│  │  │              │        
│  │  │              ├─config  //部分框架配置  
│  │  │              │      CorsConfig.java  
│  │  │              │      Knife4jConfig.java  
│  │  │              │      MPConfig.java  
│  │  │              │      SecurityConfig.java  
│  │  │              │      WebMvcConfig.java  
│  │  │              │      WebSocketConfig.java  
│  │  │              │      
│  │  │              ├─controller  //定义前后端接口  
│  │  │              │      FileController.java    
│  │  │              │      LogController.java  
│  │  │              │      ManageController.java  
│  │  │              │      ManageMessageController.java  
│  │  │              │      MarkdownQueryController.java  
│  │  │              │      QueryDataController.java  
│  │  │              │      RegisterController.java  
│  │  │              │      StudentController.java  
│  │  │              │      
│  │  │              ├─dto  //与前端传输数据用       
│  │  │              │      ExcelData.java  
│  │  │              │      MachineLearningData.java  
│  │  │              │      MessageData.java  
│  │  │              │      StudentDetail.java  
│  │  │              │      StudentDetails.java  
│  │  │              │      StudentMessageData.java  
│  │  │              │      TrainData.java  
│  │  │              │      
│  │  │              ├─entity  // 实体类，与数据库中表对应  
│  │  │              │      LearningTime.java  
│  │  │              │      Levels.java  
│  │  │              │      Markdown.java  
│  │  │              │      Message.java  
│  │  │              │      Params.java  
│  │  │              │      StudentMessage.java  
│  │  │              │      StudentPicture.java  
│  │  │              │      TrainResult.java  
│  │  │              │      
│  │  │              ├─filter  //请求过滤器  
│  │  │              │      JwtAuthenticationTokenFilter.java  
│  │  │              │      
│  │  │              ├─handler  //异常处理器  
│  │  │              │      AccessDeniedHandlerImpl.java  
│  │  │              │      AuthenticationEntryPointerImpl.java  
│  │  │              │      GlobalExceptionHandler.java  
│  │  │              │      MyMetaObjectHandler.java  
│  │  │              │      
│  │  │              ├─mapper  //mybatis-plus框架数据库查询接口
│  │  │              │      LearningTimeMapper.java  
│  │  │              │      LevelsMapper.java  
│  │  │              │      MarkdownMapper.java  
│  │  │              │      MessageMapper.java  
│  │  │              │      ParamsMapper.java  
│  │  │              │      StudentMessageMapper.java  
│  │  │              │      StudentPictureMapper.java  
│  │  │              │      TrainResultMapper.java  
│  │  │              │      
│  │  │              ├─server  //websocket服务器，负责聊天功能    
│  │  │              │      WebSocketServer.java  
│  │  │              │      
│  │  │              ├─service  //业务层，负责增删查改
│  │  │              │  │  LoginMessage.java  
│  │  │              │  │  
│  │  │              │  ├─impl  
│  │  │              │  │      LearningTimeUpdateServiceImp.java  
│  │  │              │  │      LoginServiceImpl.java  
│  │  │              │  │      LogoutServiceImpl.java  
│  │  │              │  │      MachineLearnServiceImp.java  
│  │  │              │  │      ManageServiceImp.java  
│  │  │              │  │      MarkdownQueryServiceImp.java  
│  │  │              │  │      MessageImp.java  
│  │  │              │  │      QueryDataServiceImp.java  
│  │  │              │  │      RegisterServiceImpl.java  
│  │  │              │  │      UserDetailServiceImpl.java  
│  │  │              │  │      
│  │  │              │  └─inter  
│  │  │              │          LearningTimeUpdateService.java  
│  │  │              │          LoginService.java  
│  │  │              │          LogoutService.java  
│  │  │              │          MachineLearnService.java  
│  │  │              │          MarkdownQueryService.java  
│  │  │              │          MessageService.java  
│  │  │              │          QueryDataService.java  
│  │  │              │          RegisterService.java  
│  │  │              │          
│  │  │              └─utils  //工具类  
│  │  │                      JacksonObjectMapper.java  
│  │  │                      JwtUtil.java  
│  │  │                      MapperUtils.java  
│  │  │                      UserMessageUtils.java  
│  │  │                      ValidateCodeUtils.java  
│  │  │                      WebUtils.java  
│  │  │                      
│  │  └─resources  
│  │      │  application.yml  //配置文件   
│  │      │  
│  │      ├─META-INF  
│  │      │      additional-spring-configuration-metadata.json  
│  │      │      
│  │      ├─static //静态文件  
│  │      │      学生信息.xlsx  
│  │      │      
│  │      └─templates  
│  └─test  
│      └─java  
