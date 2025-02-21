package com.ruoyi.business.socket.messageHandler.handler.command;

import com.ruoyi.business.domain.Client;
import com.ruoyi.business.domain.ClientStatus;
import com.ruoyi.business.socket.SocketService;
import com.ruoyi.business.socket.messageHandler.handler.AbstractMessageHandler;
import com.ruoyi.business.socket.messageHandler.model.Events.ClientProjectTrainEndEvent;
import com.ruoyi.business.socket.messageHandler.model.command.MCStopTrainCommand;
import com.ruoyi.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.ruoyi.business.socket.messageHandler.handler.CommandEnum.CLIENT_PROJECT_TRAIN_END;

@Slf4j
@Component
public class MCStopTrainCommandHandler extends AbstractMessageHandler {

    public void stopTrain(Client client) {
        MCStopTrainCommand request = new MCStopTrainCommand();
        request.setClientName(client.getName());
        socketService.sendMessageToClientByAddress(client.getIp(), client.getPort(), JsonUtil.toJson(request));
    }
    @Override
    public void handle(String jsonMessage, ClientStatus clientStatus) {
        ClientProjectTrainEndEvent message = JsonUtil.fromJson(jsonMessage, ClientProjectTrainEndEvent.class);
        log.info("处理客户端训练完成: {}", message.getName());
        setClientLog(message.getName(), jsonMessage);
    }

    @Override
    public String getCommand() {
        return CLIENT_PROJECT_TRAIN_END.getCommandStr();
    }
}
