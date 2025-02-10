package com.ruoyi.business.socket.messageHandler.handler;

import lombok.Getter;

@Getter
public enum CommandEnum {
    CLIENT_ADD("Client_Add", "客户端上线"),
    CLIENT_OFFLINE("Client_Offline", "客户端下线"),
    CLIENT_PROJECT_TRAIN_END("Client_ProjectTrainEnd", "客户端训练完成"),
    CLIENT_PROJECT_TRAIN_START("Client_ProjectTrainStart", "客户端开始训练"),
    CLIENT_ERROR("Client_Error", "异常报警信息"),
    MC_GET_CLIENT_STATE("MC_GetClientState", "获取客户端状态信息"),
    MC_GET_TRAIN_STATE("MC_GetTrainState", "获取训练进度详细信息"),
    MC_START_TRAIN("MC_StartTrain", "开始客户端训练"),
    MC_STOP_TRAIN("MC_StopTrain", "停止客户端训练"),
    MC_PAUSE_TRAIN("MC_PauseTrain", "暂停客户端训练"),
    MC_CHANGE_TRAIN_PARA("MC_ChangeTrainPara", "调整训练参数");

    private final String commandStr;
    private final String commandType;

    CommandEnum(String commandStr, String commandType) {
        this.commandStr = commandStr;
        this.commandType = commandType;
    }

    public static CommandEnum fromCommandStr(String commandStr) {
        for (CommandEnum command : CommandEnum.values()) {
            if (command.getCommandStr().equals(commandStr)) {
                return command;
            }
        }
        throw new IllegalArgumentException("No enum constant with commandStr: " + commandStr);
    }
}


