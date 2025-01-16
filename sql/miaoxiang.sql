CREATE TABLE client (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        ip VARCHAR(45) NOT NULL,
                        port INT NOT NULL,
                        state TINYINT NOT NULL,
                        create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);


-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('【请填写功能名称】', '3', '1', 'client', 'system/client/index', 1, 0, 'C', '0', '0', 'system:client:list', '#', 'admin', sysdate(), '', null, '【请填写功能名称】菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('【请填写功能名称】查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'system:client:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('【请填写功能名称】新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'system:client:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('【请填写功能名称】修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'system:client:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('【请填写功能名称】删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:client:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('【请填写功能名称】导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'system:client:export',       '#', 'admin', sysdate(), '', null, '');


-- 菜单 SQL
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2000, '项目管理', 0, 0, 'project', 'projectManager/index.vue', NULL, '', 1, 0, 'C', '0', '0', '', 'clipboard', 'admin', '2025-01-08 16:45:53', 'admin', '2025-01-11 14:22:09', '');

CREATE TABLE project (
                         id INT AUTO_INCREMENT PRIMARY KEY COMMENT '项目ID，自动递增',
                         project_name VARCHAR(255) NOT NULL COMMENT '项目名称',
                         description TEXT COMMENT '描述',
                         create_time DATETIME COMMENT '创建时间，默认为当前时间',
                         create_by VARCHAR(100) COMMENT '创建人',
                         update_time DATETIME COMMENT '更新时间，默认为当前时间，并在更新时自动更新',
                         update_by VARCHAR(100) COMMENT '更新人'
);


-- 按钮父菜单ID
SELECT @parentId := 2001;

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('任务查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'system:assignment:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('任务新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'system:assignment:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('任务修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'system:assignment:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('任务删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:assignment:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('任务导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'system:assignment:export',       '#', 'admin', sysdate(), '', null, '');


-- 按钮父菜单ID
SELECT @parentId := 2000;

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('项目查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'system:projects:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('项目新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'system:projects:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('项目修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'system:projects:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('项目删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:projects:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('项目导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'system:projects:export',       '#', 'admin', sysdate(), '', null, '');


CREATE TABLE model (
                       id INT AUTO_INCREMENT PRIMARY KEY COMMENT '模型ID，自动递增',
                       model_name VARCHAR(255) NOT NULL COMMENT '模型名称',
                       description TEXT COMMENT '描述',
                       create_time DATETIME COMMENT '创建时间',
                       create_by VARCHAR(100) COMMENT '创建人',
                       update_time DATETIME COMMENT '更新时间',
                       update_by VARCHAR(100) COMMENT '更新人'
);

INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2009, '项目导出', 2000, 5, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'system:projects:export', '#', 'admin', '2025-01-13 13:55:22', '', NULL, '');


-- 按钮父菜单ID
SELECT @parentId := 2003;

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('模型查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'system:model:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('模型新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'system:model:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('模型修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'system:model:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('模型删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:model:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('模型导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'system:model:export',       '#', 'admin', sysdate(), '', null, '');


CREATE TABLE assignment (
                            id INT AUTO_INCREMENT PRIMARY KEY COMMENT '任务ID',
                            assignment_name VARCHAR(255) NOT NULL COMMENT '任务名称',
                            project_id BIGINT UNSIGNED NOT NULL COMMENT '关联的项目ID',
                            model_id BIGINT UNSIGNED NOT NULL COMMENT '关联的模型ID',
                            preTrain_mode VARCHAR(255) NOT NULL COMMENT '训练网络的预训练模式',
                            epoch INT UNSIGNED NOT NULL COMMENT '训练次数',
                            batch_size INT UNSIGNED NOT NULL COMMENT '每次训练的批大小',
                            img_size INT UNSIGNED NOT NULL COMMENT '输入图像的大小',
                            description TEXT COMMENT '描述',
                            create_time DATETIME COMMENT '创建时间',
                            create_by VARCHAR(100) COMMENT '创建人',
                            update_time DATETIME COMMENT '更新时间',
                            update_by VARCHAR(100) COMMENT '更新人',
                            PRIMARY KEY (id)
) COMMENT='任务表';
