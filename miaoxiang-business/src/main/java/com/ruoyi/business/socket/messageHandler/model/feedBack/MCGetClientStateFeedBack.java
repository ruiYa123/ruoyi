package com.ruoyi.business.socket.messageHandler.model.feedBack;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.business.socket.messageHandler.model.BaseMessage;
import lombok.Data;

@Data
public class MCGetClientStateFeedBack extends BaseMessage {
    @JsonProperty("ClientState")
    private ClientState clientState;

    @Data
    public static class ClientState {
        @JsonProperty("Name")
        private String name;

        @JsonProperty("State")
        private int state; // 0: 空闲中, 1: 训练中, 2: 停用

        @JsonProperty("SystemInfo")
        private SystemInfo systemInfo;

        @JsonProperty("ResourceUsage")
        private ResourceUsage resourceUsage;

        @JsonProperty("TrainState")
        private TrainState trainState;

        @Data
        public static class SystemInfo {
            @JsonProperty("OS")
            private String os;

            @JsonProperty("OS_Version")
            private String osVersion;

            @JsonProperty("Machine_Name")
            private String machineName;

            @JsonProperty("CPU_Model")
            private String cpuModel;

            @JsonProperty("GPU_Model")
            private String gpuModel;

            @JsonProperty("RAM_Total_MB")
            private int ramTotalMB;

            @JsonProperty("Disk_Total_MB")
            private int diskTotalMB;

            @JsonProperty("Network_Status")
            private String networkStatus;
        }

        @Data
        public static class ResourceUsage {
            @JsonProperty("CPU")
            private CPUUsage cpu;

            @JsonProperty("GPU")
            private GPUUsage gpu;

            @JsonProperty("Disk")
            private DiskUsage disk;

            @Data
            public static class CPUUsage {
                @JsonProperty("Usage")
                private String usage; // CPU使用率

                @JsonProperty("Cores")
                private int cores; // CPU核心数

                @JsonProperty("Threads")
                private int threads; // CPU线程数

                @JsonProperty("Mem")
                private MemUsage mem;

                @Data
                public static class MemUsage {
                    @JsonProperty("Used_MB")
                    private int usedMB; // 已使用内存 (MB)

                    @JsonProperty("Total_MB")
                    private int totalMB; // 总内存 (MB)
                }
            }

            @Data
            public static class GPUUsage {
                @JsonProperty("Usage")
                private String usage; // GPU使用率

                @JsonProperty("Cores")
                private int cores; // GPU核心数 (CUDA核心数)

                @JsonProperty("Mem")
                private MemUsage mem;

                @Data
                public static class MemUsage {
                    @JsonProperty("Used_MB")
                    private int usedMB; // GPU已使用内存 (MB)

                    @JsonProperty("Total_MB")
                    private int totalMB; // GPU总内存 (MB)
                }
            }

            @Data
            public static class DiskUsage {
                @JsonProperty("Used_MB")
                private int usedMB; // 已使用磁盘空间 (MB)

                @JsonProperty("Total_MB")
                private int totalMB; // 总磁盘空间 (MB)
            }
        }

        @Data
        public static class TrainState {
            @JsonProperty("Train_Process")
            private String trainProcess; // "Collect_Label", "Datasets_Process", "Add_Datasets", "Train_Model", "Transform_Model"
        }
    }
}

