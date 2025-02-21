CREATE TABLE client (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
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
                         id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '项目ID，自动递增',
                         project_name VARCHAR(255) NOT NULL COMMENT '项目名称',
                         description TEXT COMMENT '描述',
                         create_time DATETIME COMMENT '创建时间，默认为当前时间',
                         create_by VARCHAR(100) COMMENT '创建人',
                         update_time DATETIME COMMENT '更新时间，默认为当前时间，并在更新时自动更新',
                         update_by VARCHAR(100) COMMENT '更新人'
                         dept INT COMMENT '部门ID';
);



-- 按钮父菜单ID
SELECT @parentId := 2000;

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('模型查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'miaoxiang-business:model:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('模型新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'miaoxiang-business:model:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('模型修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'miaoxiang-business:model:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('模型删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'miaoxiang-business:model:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('模型导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'miaoxiang-business:model:export',       '#', 'admin', sysdate(), '', null, '');

CREATE TABLE model (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '模型ID，自动递增',
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
                            id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '任务ID',
                            assignment_name VARCHAR(255) NOT NULL COMMENT '任务名称',
                            project_id BIGINT UNSIGNED NOT NULL COMMENT '关联的项目ID',
                            model_id BIGINT UNSIGNED NOT NULL COMMENT '关联的模型ID',
                            client_id BIGINT UNSIGNED DEFAULT NULL COMMENT '训练此任务的训练机ID',
                            preTrain_mode VARCHAR(255) NOT NULL COMMENT '训练网络的预训练模式',
                            epoch INT UNSIGNED NOT NULL COMMENT '训练次数',
                            batch_size INT UNSIGNED NOT NULL COMMENT '每次训练的批大小',
                            img_size INT UNSIGNED NOT NULL COMMENT '输入图像的大小',
                            state INT UNSIGNED NOT NULL COMMENT '任务状态',
                            description TEXT COMMENT '描述',
                            create_time DATETIME COMMENT '创建时间',
                            create_by VARCHAR(100) COMMENT '创建人',
                            update_time DATETIME COMMENT '更新时间',
                            update_by VARCHAR(100) COMMENT '更新人'
                            dept INT COMMENT '部门ID';
                            jump_time DATETIME COMMENT '插队时间',
) COMMENT='任务表';

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

CREATE TABLE assignment_train (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
                                  assignment_id BIGINT NOT NULL COMMENT '任务ID',
                                  client_id BIGINT UNSIGNED DEFAULT NULL COMMENT '训练此任务的训练机ID',
                                  state VARCHAR(50) NOT NULL COMMENT '状态',
                                  progress DECIMAL(5,2) NOT NULL COMMENT '进度',
                                  description TEXT COMMENT '备注',
                                  create_time DATETIME COMMENT '创建时间',
                                  create_by VARCHAR(100) COMMENT '创建人',
                                  update_time DATETIME COMMENT '更新时间',
                                  update_by VARCHAR(100) COMMENT '更新人'
) COMMENT='任务训练';



-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('任务训练', '3', '1', 'train', 'business/train/index', 1, 0, 'C', '0', '0', 'business:train:list', '#', 'admin', sysdate(), '', null, '任务训练菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('任务训练查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'business:train:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('任务训练新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'business:train:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('任务训练修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'business:train:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('任务训练删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'business:train:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('任务训练导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'business:train:export',       '#', 'admin', sysdate(), '', null, '');


CREATE TABLE resources (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
                           img_name VARCHAR(255) NOT NULL COMMENT '图片名称',
                           assignment_id BIGINT NOT NULL COMMENT '关联的任务ID',
                           img_path VARCHAR(500) NOT NULL COMMENT '图片存储路径',
                           img_size BIGINT NOT NULL COMMENT '图片大小（字节）',
                           description TEXT COMMENT '图片描述',
                           state TINYINT NOT NULL DEFAULT 0 COMMENT '图片状态（0-未打标，1-已打标）',
                           create_time DATETIME COMMENT '创建时间',
                           create_by VARCHAR(100) COMMENT '创建人',
                           update_time DATETIME COMMENT '更新时间',
                           update_by VARCHAR(100) COMMENT '更新人'
) COMMENT = '资源';

CREATE TABLE train_log (
                     id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
                     assignment_id BIGINT NOT NULL COMMENT '关联的任务ID',
                     assignment_train_id BIGINT NOT NULL COMMENT '关联的训练任务ID',
                     content TEXT COMMENT '日志内容',
                     create_time DATETIME COMMENT '创建时间',
                     create_by VARCHAR(100) COMMENT '创建人',
                     update_time DATETIME COMMENT '更新时间',
                     update_by VARCHAR(100) COMMENT '更新人'
) COMMENT = '日志表';

-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('训练日志', '3', '1', 'trainLog', 'business/trainLog/index', 1, 0, 'C', '0', '0', 'business:trainLog:list', '#', 'admin', sysdate(), '', null, '训练日志菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('训练日志查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'business:trainLog:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('训练日志新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'business:trainLog:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('训练日志修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'business:trainLog:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('训练日志删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'business:trainLog:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('训练日志导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'business:trainLog:export',       '#', 'admin', sysdate(), '', null, '');


CREATE TABLE client_log (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
                            client_id BIGINT NOT NULL COMMENT '关联的客户端ID',
                            command_str VARCHAR(255) COMMENT '客户端执行的命令字符串',
                            state INT NOT NULL DEFAULT 0 COMMENT'日志状态',
                            content TEXT COMMENT '日志详细内容',
                            create_time DATETIME COMMENT '创建时间',
                            create_by VARCHAR(100) COMMENT '创建人'
) COMMENT = '客户端操作日志表';


-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户端操作日志', '3', '1', 'log', 'business/log/index', 1, 0, 'C', '0', '0', 'business:log:list', '#', 'admin', sysdate(), '', null, '客户端操作日志菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户端操作日志查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'business:log:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户端操作日志新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'business:log:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户端操作日志修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'business:log:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户端操作日志删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'business:log:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户端操作日志导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'business:log:export',       '#', 'admin', sysdate(), '', null, '');
