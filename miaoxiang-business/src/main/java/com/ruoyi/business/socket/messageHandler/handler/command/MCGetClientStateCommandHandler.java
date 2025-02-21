package com.ruoyi.business.socket.messageHandler.handler.command;

import com.ruoyi.business.domain.Client;
import com.ruoyi.business.domain.ClientStatus;
import com.ruoyi.business.socket.SocketService;
import com.ruoyi.business.socket.messageHandler.handler.AbstractMessageHandler;
import com.ruoyi.business.socket.messageHandler.model.command.MCGetClientStateCommand;
import com.ruoyi.business.socket.messageHandler.model.feedBack.MCGetClientStateFeedBack;
import com.ruoyi.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import static com.ruoyi.business.socket.messageHandler.handler.CommandEnum.GET_CLIENT_STATE;

@Slf4j
@Component
public class MCGetClientStateCommandHandler extends AbstractMessageHandler {

    @Scheduled(initialDelay = 2000, fixedRateString = "${socket.scheduling.rate}")
    public void requestClientState() {
//        log.info("获取client状态");
        getClients().forEach(client -> {
            if (client.getState() == -1) {
                return;
            }
            MCGetClientStateCommand request = new MCGetClientStateCommand();
            request.setClientNames(client.getName());
            SocketService.sendMessageToClientByAddress(
                    client.getIp(),
                    client.getPort(),
                    JsonUtil.toJson(request)
            );
        });
    }

    @Override
    public void handle(String jsonMessage, ClientStatus clientStatus) {
        log.info("返回客户端状态信息: {}", jsonMessage);
        MCGetClientStateFeedBack response = JsonUtil.fromJson(jsonMessage, MCGetClientStateFeedBack.class);
        Client client = new Client();
        client.setState(response.getClientState().getState());
        client.setName(response.getClientState().getName());
        client.setIp(clientStatus.getIp());
        client.setPort(clientStatus.getPort());
        clientService.addClient(client);
        clientStatus.setGpu(response.getClientState().getGpu());
        clientStatus.setCpu(response.getClientState().getCpu());
        clientStatus.setGpuMem(response.getClientState().getGpuMem());
        clientStatus.setCpuMem(response.getClientState().getCpuMem());
        clientStatus.setDisk(response.getClientState().getDisk());

        setClientLog(clientStatus.getIp(), clientStatus.getPort(), jsonMessage);
    }

    public String getCommand() {
        return GET_CLIENT_STATE.getCommandStr();
    }
}
