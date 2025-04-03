package com.ruoyi.business.socket.messageHandler.handler.event;

import com.ruoyi.business.domain.Client;
import com.ruoyi.business.domain.ClientStatus;
import com.ruoyi.business.socket.messageHandler.handler.AbstractMessageHandler;
import com.ruoyi.business.socket.messageHandler.model.feedBack.MCGetClientStateFeedBack;
import com.ruoyi.business.socket.messageHandler.model.feedBack.MCGetTrainStateFeedBack;
import com.ruoyi.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.ruoyi.business.socket.messageHandler.handler.CommandEnum.REPORT_CLIENT_STATE;

@Slf4j
@Component
public class MCReportClientStateEventHandler extends AbstractMessageHandler {

    @Override
    public void handle(String jsonMessage, ClientStatus clientStatus) {
        log.info("返回客户端状态信息: {}", jsonMessage);
        MCGetClientStateFeedBack response = JsonUtil.fromJson(jsonMessage, MCGetClientStateFeedBack.class);
        if (response.getClientState().getState() == 0) {
            clientInfoManager.registerClient(response.getClientState().getName());
            clientStatus.setMcGetTrainStateFeedBack(new MCGetTrainStateFeedBack());
        }
        Client client = new Client();
        client.setState(response.getClientState().getState());
        client.setName(response.getClientState().getName());
        client.setIp(clientStatus.getClient().getIp());
        client.setPort(clientStatus.getClient().getPort());
        clientService.addClient(client);
        clientStatus.setMcGetClientStateFeedBack(response);
        clientInfoManager.updateClientInfo(clientStatus);
        setClientLog(clientStatus.getClient().getIp(), clientStatus.getClient().getPort(), jsonMessage);
    }


    public String getCommand() {
        return REPORT_CLIENT_STATE.getCommandStr();
    }
}
