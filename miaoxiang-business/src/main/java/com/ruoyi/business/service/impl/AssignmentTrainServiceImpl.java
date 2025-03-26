package com.ruoyi.business.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.AssignmentTrainMapper;
import com.ruoyi.business.domain.AssignmentTrain;
import com.ruoyi.business.service.IAssignmentTrainService;

/**
 * 任务训练Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-08
 */
@Service
public class AssignmentTrainServiceImpl implements IAssignmentTrainService
{
    @Autowired
    private AssignmentTrainMapper assignmentTrainMapper;

    /**
     * 查询任务训练
     *
     * @param id 任务训练主键
     * @return 任务训练
     */
    @Override
    public AssignmentTrain selectAssignmentTrainById(Long id)
    {
        return assignmentTrainMapper.selectAssignmentTrainById(id);
    }

    /**
     * 查询任务训练列表
     *
     * @param assignmentTrain 任务训练
     * @return 任务训练
     */
    @Override
    public List<AssignmentTrain> selectAssignmentTrainList(AssignmentTrain assignmentTrain)
    {
        return assignmentTrainMapper.selectAssignmentTrainList(assignmentTrain);
    }

    /**
     * 新增任务训练
     *
     * @param assignmentTrain 任务训练
     * @return 结果
     */
    @Override
    public int insertAssignmentTrain(AssignmentTrain assignmentTrain)
    {
        assignmentTrain.setCreateTime(DateUtils.getNowDate());
        return assignmentTrainMapper.insertAssignmentTrain(assignmentTrain);
    }

    @Override
    public AssignmentTrain startTrain(Long assignmentId, String clientName) {
        AssignmentTrain assignmentTrain = new AssignmentTrain();
        assignmentTrain.setState(3);
        assignmentTrain.setAssignmentId(assignmentId);
        List<AssignmentTrain> assignmentTrains = selectAssignmentTrainList(assignmentTrain);
        if (assignmentTrains != null && !assignmentTrains.isEmpty()) {
            assignmentTrain = assignmentTrains.get(assignmentTrains.size() - 1);
            assignmentTrain.setState(1);
            updateAssignmentTrain(assignmentTrain);
        } else {
            assignmentTrain.setAssignmentId(assignmentId);
            assignmentTrain.setCreateTime(new Date());
            assignmentTrain.setProgress(BigDecimal.ZERO);
            assignmentTrain.setState(1);
            assignmentTrain.setClientName(clientName);
            insertAssignmentTrain(assignmentTrain);
        }
        return assignmentTrain;
    }

    @Override
    public Long finishTrain(Long assignmentId, String clientName, Integer state) {
        AssignmentTrain assignmentTrain = new AssignmentTrain();
        assignmentTrain.setState(1);
        assignmentTrain.setAssignmentId(assignmentId);
        List<AssignmentTrain> assignmentTrains = selectAssignmentTrainList(assignmentTrain);
        if (assignmentTrains != null && !assignmentTrains.isEmpty()) {
            assignmentTrain = assignmentTrains.get(assignmentTrains.size() - 1);
            assignmentTrain.setClientName(clientName);
            assignmentTrain.setState(state);
            updateAssignmentTrain(assignmentTrain);
            return assignmentTrain.getAssignmentId();
        } else {
            assignmentTrain.setState(state);
            assignmentTrain.setClientName(clientName);
            assignmentTrain.setProgress(new BigDecimal(100));
            insertAssignmentTrain(assignmentTrain);
        }
        return assignmentTrain.getId();
    }

    @Override
    public Long updateTrain(Long assignmentId, String clientName,  BigDecimal progress, Integer state) {
        AssignmentTrain assignmentTrain = new AssignmentTrain();
//        assignmentTrain.setState(1);
        assignmentTrain.setAssignmentId(assignmentId);
        List<AssignmentTrain> assignmentTrains = selectAssignmentTrainList(assignmentTrain);
        if (assignmentTrains != null && !assignmentTrains.isEmpty()) {
            assignmentTrain = assignmentTrains.get(assignmentTrains.size() - 1);
            assignmentTrain.setProgress(progress);
            if (state != null) {
                assignmentTrain.setState(state);
            }
            assignmentTrain.setClientName(clientName);
            updateAssignmentTrain(assignmentTrain);
        }
        return assignmentTrain.getId();
    }

    /**
     * 修改任务训练
     *
     * @param assignmentTrain 任务训练
     * @return 结果
     */
    @Override
    public int updateAssignmentTrain(AssignmentTrain assignmentTrain)
    {
        assignmentTrain.setUpdateTime(DateUtils.getNowDate());
        return assignmentTrainMapper.updateAssignmentTrain(assignmentTrain);
    }

    /**
     * 批量删除任务训练
     *
     * @param ids 需要删除的任务训练主键
     * @return 结果
     */
    @Override
    public int deleteAssignmentTrainByIds(Long[] ids)
    {
        return assignmentTrainMapper.deleteAssignmentTrainByIds(ids);
    }

    @Override
    public int deleteAssignmentTrainByAssignmentIds(Long[] ids)
    {
        return assignmentTrainMapper.deleteAssignmentTrainByAssignmentIds(ids);
    }

    /**
     * 删除任务训练信息
     *
     * @param id 任务训练主键
     * @return 结果
     */
    @Override
    public int deleteAssignmentTrainById(Long id)
    {
        return assignmentTrainMapper.deleteAssignmentTrainById(id);
    }
}
