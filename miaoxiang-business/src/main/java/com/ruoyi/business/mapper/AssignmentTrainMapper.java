package com.ruoyi.business.mapper;

import java.util.List;
import com.ruoyi.business.domain.AssignmentTrain;

/**
 * 任务训练Mapper接口
 * 
 * @author ruoyi
 * @date 2025-02-08
 */
public interface AssignmentTrainMapper 
{
    /**
     * 查询任务训练
     * 
     * @param id 任务训练主键
     * @return 任务训练
     */
    public AssignmentTrain selectAssignmentTrainById(Long id);

    /**
     * 查询任务训练列表
     * 
     * @param assignmentTrain 任务训练
     * @return 任务训练集合
     */
    public List<AssignmentTrain> selectAssignmentTrainList(AssignmentTrain assignmentTrain);

    /**
     * 新增任务训练
     * 
     * @param assignmentTrain 任务训练
     * @return 结果
     */
    public int insertAssignmentTrain(AssignmentTrain assignmentTrain);

    /**
     * 修改任务训练
     * 
     * @param assignmentTrain 任务训练
     * @return 结果
     */
    public int updateAssignmentTrain(AssignmentTrain assignmentTrain);

    /**
     * 删除任务训练
     * 
     * @param id 任务训练主键
     * @return 结果
     */
    public int deleteAssignmentTrainById(Long id);

    /**
     * 批量删除任务训练
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAssignmentTrainByIds(Long[] ids);
}
