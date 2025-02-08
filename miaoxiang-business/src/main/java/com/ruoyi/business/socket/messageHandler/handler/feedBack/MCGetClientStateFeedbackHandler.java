package com.ruoyi.business.socket.messageHandler.handler.feedBack;

import com.ruoyi.business.socket.messageHandler.handler.BaseMessageHandler;
import com.ruoyi.business.socket.messageHandler.model.command.MCGetClientStateCommand;
import com.ruoyi.business.socket.messageHandler.model.feedBack.MCGetClientStateFeedBack;
import com.ruoyi.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MCGetClientStateFeedbackHandler implements BaseMessageHandler {

    @Override
    public void handle(String jsonMessage) {
        MCGetClientStateCommand request = JsonUtil.fromJson(jsonMessage, MCGetClientStateCommand.class);
        log.info("获取客户端状态信息请求: {}", request.getClientNames());

        // 这里可以添加逻辑来获取客户端状态
        MCGetClientStateFeedBack response = new MCGetClientStateFeedBack();
        MCGetClientStateFeedBack.ClientState clientState = new MCGetClientStateFeedBack.ClientState();
        clientState.setName(request.getClientNames());
        clientState.setState(0); // 假设状态为0（空闲）

        MCGetClientStateFeedBack.ClientState.TrainState trainState = new MCGetClientStateFeedBack.ClientState.TrainState();
        trainState.setTrainProcess("Collect_Label"); // 假设训练过程为"Collect_Label"

        clientState.setTrainState(trainState);
        response.setClientState(clientState);

        // 这里可以将 response 转换为 JSON 并发送回去
        String jsonResponse = JsonUtil.toJson(response);
        log.info("返回客户端状态信息: {}", jsonResponse);
    }

    @Override
    public String getCommand() {
        return "MC_GetClientState";
    }
}
