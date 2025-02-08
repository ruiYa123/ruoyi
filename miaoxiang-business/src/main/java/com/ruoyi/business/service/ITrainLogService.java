package com.ruoyi.business.service;

import java.util.List;
import com.ruoyi.business.domain.TrainLog;

/**
 * 训练日志Service接口
 * 
 * @author ruoyi
 * @date 2025-02-07
 */
public interface ITrainLogService 
{
    /**
     * 查询训练日志
     * 
     * @param id 训练日志主键
     * @return 训练日志
     */
    public TrainLog selectTrainLogById(Long id);

    /**
     * 查询训练日志列表
     * 
     * @param trainLog 训练日志
     * @return 训练日志集合
     */
    public List<TrainLog> selectTrainLogList(TrainLog trainLog);

    /**
     * 新增训练日志
     * 
     * @param trainLog 训练日志
     * @return 结果
     */
    public int insertTrainLog(TrainLog trainLog);

    /**
     * 修改训练日志
     * 
     * @param trainLog 训练日志
     * @return 结果
     */
    public int updateTrainLog(TrainLog trainLog);

    /**
     * 批量删除训练日志
     * 
     * @param ids 需要删除的训练日志主键集合
     * @return 结果
     */
    public int deleteTrainLogByIds(Long[] ids);

    /**
     * 删除训练日志信息
     * 
     * @param id 训练日志主键
     * @return 结果
     */
    public int deleteTrainLogById(Long id);
}
