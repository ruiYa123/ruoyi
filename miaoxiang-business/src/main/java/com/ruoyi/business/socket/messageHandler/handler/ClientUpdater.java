package com.ruoyi.business.socket.messageHandler.handler;

import com.ruoyi.business.domain.Client;
import com.ruoyi.business.service.IClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Component
public class ClientUpdater {

    @Autowired
    private IClientService clientService;

    private List<Client> clients;

    @Scheduled(initialDelay = 0, fixedRateString = "${socket.scheduling.rate}")
    public void updateClients() {
        log.info("更新client信息");
        clients = clientService.selectClientList(new Client());
    }

    public synchronized List<Client> getClients() {
        return clients;
    }
}
