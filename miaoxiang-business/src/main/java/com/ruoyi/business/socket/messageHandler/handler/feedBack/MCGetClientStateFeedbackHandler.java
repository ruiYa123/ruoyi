package com.ruoyi.business.socket.messageHandler.handler.feedBack;

import com.ruoyi.business.socket.messageHandler.handler.AbstractMessageHandler;
import com.ruoyi.business.socket.messageHandler.model.command.MCGetClientStateCommand;
import com.ruoyi.business.socket.messageHandler.model.feedBack.MCGetClientStateFeedBack;
import com.ruoyi.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.ruoyi.business.socket.messageHandler.handler.CommandEnum.MC_GET_CLIENT_STATE;

@Slf4j
@Component
public class MCGetClientStateFeedbackHandler extends AbstractMessageHandler {

    @Override
    public void handle(String jsonMessage, String ip , int port) {
        MCGetClientStateCommand request = JsonUtil.fromJson(jsonMessage, MCGetClientStateCommand.class);
        log.info("获取客户端状态信息请求: {}", request.getClientNames());

        MCGetClientStateFeedBack response = new MCGetClientStateFeedBack();
        MCGetClientStateFeedBack.ClientState clientState = new MCGetClientStateFeedBack.ClientState();
        clientState.setName(request.getClientNames());
        clientState.setState(0); // 假设状态为0（空闲）

        MCGetClientStateFeedBack.ClientState.TrainState trainState = new MCGetClientStateFeedBack.ClientState.TrainState();
        trainState.setTrainProcess("Collect_Label"); // 假设训练过程为"Collect_Label"

        clientState.setTrainState(trainState);
        response.setClientState(clientState);

        String jsonResponse = JsonUtil.toJson(response);
        log.info("返回客户端状态信息: {}", jsonResponse);
    }

    @Override
    public String getCommand() {
        return MC_GET_CLIENT_STATE.getCommandStr();
    }
}
