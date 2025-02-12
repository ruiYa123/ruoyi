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
    public AssignmentTrain startTrain(Long assignmentId) {
        AssignmentTrain assignmentTrain = new AssignmentTrain();
        assignmentTrain.setState(3L);
        List<AssignmentTrain> assignmentTrains = selectAssignmentTrainList(assignmentTrain);
        if (assignmentTrains != null && !assignmentTrains.isEmpty()) {
            assignmentTrain = assignmentTrains.get(assignmentTrains.size() - 1);
        } else {
            assignmentTrain.setAssignmentId(assignmentId);
            assignmentTrain.setCreateTime(new Date());
            assignmentTrain.setProgress(BigDecimal.ZERO);
            assignmentTrain.setState(1L);
            insertAssignmentTrain(assignmentTrain);
        }
        return assignmentTrain;
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
