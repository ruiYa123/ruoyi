package com.ruoyi.business.service;

import java.util.List;
import com.ruoyi.business.domain.AssignmentTrain;

/**
 * 任务训练Service接口
 *
 * @author ruoyi
 * @date 2025-02-08
 */
public interface IAssignmentTrainService
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

    public AssignmentTrain startTrain(Long assignmenId, String clientName);

    public boolean finishTrain(Long assignmenId, String clientName, Integer state);
    /**
     * 修改任务训练
     *
     * @param assignmentTrain 任务训练
     * @return 结果
     */
    public int updateAssignmentTrain(AssignmentTrain assignmentTrain);

    /**
     * 批量删除任务训练
     *
     * @param ids 需要删除的任务训练主键集合
     * @return 结果
     */
    public int deleteAssignmentTrainByIds(Long[] ids);


    public int deleteAssignmentTrainByAssignmentIds(Long[] ids);
    /**
     * 删除任务训练信息
     *
     * @param id 任务训练主键
     * @return 结果
     */
    public int deleteAssignmentTrainById(Long id);
}
