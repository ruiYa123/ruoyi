package com.ruoyi.business.mapper;

import java.util.List;
import com.ruoyi.business.domain.Assignment;
import org.apache.ibatis.annotations.Param;

/**
 * 任务Mapper接口
 *
 * @author ruoyi
 * @date 2025-01-16
 */
public interface AssignmentMapper
{
    /**
     * 查询任务
     *
     * @param id 任务主键
     * @return 任务
     */
    public Assignment selectAssignmentById(Long id);

    /**
     * 查询任务列表
     *
     * @param assignment 任务
     * @return 任务集合
     */
    public List<Assignment> selectAssignmentList(Assignment assignment);

    public List<Assignment> selectAssignmentByProjectIds(Long[] projectIds);

    public List<Assignment> selectAssignmentListByIds(@Param("assignment") Assignment assignment, @Param("ids") Long[] ids);

    /**
     * 新增任务
     *
     * @param assignment 任务
     * @return 结果
     */
    public int insertAssignment(Assignment assignment);

    /**
     * 修改任务
     *
     * @param assignment 任务
     * @return 结果
     */
    public int updateAssignment(Assignment assignment);

    /**
     * 删除任务
     *
     * @param id 任务主键
     * @return 结果
     */
    public int deleteAssignmentById(Long id);

    /**
     * 批量删除任务
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAssignmentByIds(Long[] ids);

    public int deleteAssignmentByProjectIds(Long[] projectIds);

    public List<Integer> getStateCounts(Assignment assignment);
}
