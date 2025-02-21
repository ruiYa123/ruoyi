package com.ruoyi.business.socket.service;

import com.ruoyi.business.socket.client.ClientHandler;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ServiceRegistry {
    private final ConcurrentHashMap<String, PrintWriter> registry = new ConcurrentHashMap<>();

    public void register(String clientKey, PrintWriter printWriter) {
        registry.put(clientKey, printWriter);
    }

    public void unregister(String clientKey) {
        registry.remove(clientKey);
    }

    public PrintWriter getPrintWriter(String clientName) {
        return registry.get(clientName);
    }
}
