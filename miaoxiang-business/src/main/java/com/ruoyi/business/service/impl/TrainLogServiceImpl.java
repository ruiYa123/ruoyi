package com.ruoyi.business.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.TrainLogMapper;
import com.ruoyi.business.domain.TrainLog;
import com.ruoyi.business.service.ITrainLogService;

/**
 * 训练日志Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-07
 */
@Service
public class TrainLogServiceImpl implements ITrainLogService
{
    @Autowired
    private TrainLogMapper trainLogMapper;

    /**
     * 查询训练日志
     *
     * @param id 训练日志主键
     * @return 训练日志
     */
    @Override
    public TrainLog selectTrainLogById(Long id)
    {
        return trainLogMapper.selectTrainLogById(id);
    }

    /**
     * 查询训练日志列表
     *
     * @param trainLog 训练日志
     * @return 训练日志
     */
    @Override
    public List<TrainLog> selectTrainLogList(TrainLog trainLog)
    {
        return trainLogMapper.selectTrainLogList(trainLog);
    }

    /**
     * 新增训练日志
     *
     * @param trainLog 训练日志
     * @return 结果
     */
    @Override
    public int insertTrainLog(TrainLog trainLog)
    {
        trainLog.setCreateTime(DateUtils.getNowDate());
        return trainLogMapper.insertTrainLog(trainLog);
    }

    /**
     * 修改训练日志
     * x
     * @param trainLog 训练日志
     * @return 结果
     */
    @Override
    public int updateTrainLog(TrainLog trainLog)
    {
        trainLog.setUpdateTime(DateUtils.getNowDate());
        return trainLogMapper.updateTrainLog(trainLog);
    }

    /**
     * 批量删除训练日志
     *
     * @param ids 需要删除的训练日志主键
     * @return 结果
     */
    @Override
    public int deleteTrainLogByIds(Long[] ids)
    {
        return trainLogMapper.deleteTrainLogByIds(ids);
    }

    /**
     * 删除训练日志信息
     *
     * @param id 训练日志主键
     * @return 结果
     */
    @Override
    public int deleteTrainLogById(Long id)
    {
        return trainLogMapper.deleteTrainLogById(id);
    }
}
