package com.platform.machinelearningplatform.server;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.json.ObjectMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.*;
import com.platform.machinelearningplatform.dto.MessageData;
import com.platform.machinelearningplatform.entity.Message;
import com.platform.machinelearningplatform.entity.StudentPicture;
import com.platform.machinelearningplatform.mapper.MessageMapper;

import com.platform.machinelearningplatform.utils.MapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.server
 * @Author: EnMing Zhang
 * @CreateTime: 2023-01-17  21:44
 * @Description: TODO
 * @Version: 1.0
 */
@Component
@ServerEndpoint("/webSocket/{username}")
public class WebSocketServer {
    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * 记录当前在线连接数
     */
    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        sessionMap.put(username, session);
        log.info("有新用户加入，username={}, 当前在线人数为：{}", username, sessionMap.size());
        Page<Message> messagePage = new Page<>(1, 50);
        Page<Message> page = MapperUtils.getMapper().selectPage(messagePage, new LambdaQueryWrapper<Message>().eq(false, Message::getFromUser, username).orderByAsc(Message::getCreateTime));
        List<Message> records = page.getRecords();
        List<MessageData> collect = records.stream().map(e -> MessageData.builder().text(e.getContent()).from(e.getFromUser()).createTime(e.getCreateTime()).build()).collect(Collectors.toList());

        sendAllMessage(new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) -> new JsonPrimitive(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))))
                .serializeNulls()
                .create().toJson(collect));
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        sessionMap.remove(username);
        log.info("有一连接关闭，移除username={}的用户session, 当前在线人数为：{}", username, sessionMap.size());
    }

    /**
     * 收到客户端消息后调用的方法
     * 后台收到客户端发送过来的消息
     * onMessage 是一个消息的中转站
     * 接受 浏览器端 socket.send 发送过来的 json数据
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("username") String username) {
        log.info("服务端收到用户username={}的消息:{}", username, message);
        JSONObject obj = JSONUtil.parseObj(message);
        String text = obj.getStr("text"); // 发送的消息文本  hello
        MapperUtils.getMapper().insert(Message.builder().content(text).fromUser(username).build());
        Session s = sessionMap.get(username);
        ArrayList<Session> values = new ArrayList<>(sessionMap.values());
        values.remove(s);
        values.forEach(e -> {
            if (e != null) {
                // 服务器端 再把消息组装一下，组装后的消息包含发送人和发送的文本内容
                this.sendMessage(new JSONObject().set("text", text).set("from", username).toString(), e);
            } else {
                this.sendMessage(new JSONObject().set("from", "null").set("text", "信息发送失败").toString(), s);
                log.error("发送失败，未找到用户的session");
            }
        });

    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }

    /**
     * 服务端发送消息给所有客户端
     */
    private void sendAllMessage(String message) {
        try {
            for (Session session : sessionMap.values()) {
                log.info("服务端给客户端[{}]发送消息{}", session.getId(), message);
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }
}
