package com.ruoyi.business.socket.messageHandler.handler.feedBack;

import com.ruoyi.business.socket.messageHandler.handler.AbstractMessageHandler;
import com.ruoyi.business.socket.messageHandler.handler.BaseMessageHandler;
import com.ruoyi.business.socket.messageHandler.model.command.MCGetTrainStateCommand;
import com.ruoyi.business.socket.messageHandler.model.feedBack.MCGetTrainStateFeedBack;
import com.ruoyi.common.utils.JsonUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.ruoyi.business.socket.messageHandler.handler.CommandEnum.MC_GET_TRAIN_STATE;

@Slf4j
@Component
public class MCGetTrainStateFeedBackHandler extends AbstractMessageHandler {

    @Override
    public void handle(String jsonMessage, String ip, int port) {
        MCGetTrainStateCommand request = JsonUtil.fromJson(jsonMessage, MCGetTrainStateCommand.class);
        log.info("获取客户端训练进度请求: {}", request.getClientName());

        // 这里可以添加逻辑来获取训练进度
        MCGetTrainStateFeedBack response = new MCGetTrainStateFeedBack();
        MCGetTrainStateFeedBack.TrainState trainState = new MCGetTrainStateFeedBack.TrainState();
        trainState.setTrainPercentage(75);

        response.setTrainState(trainState);

        // 这里可以将 response 转换为 JSON 并发送回去
        String jsonResponse = JsonUtil.toJson(response);
        log.info("返回客户端训练进度信息: {}", jsonResponse);
    }

    @Override
    public String getCommand() {
        return MC_GET_TRAIN_STATE.getCommandStr();
    }
}
