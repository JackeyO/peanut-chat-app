create database if not exists peanut_chat;

use peanut_chat;

-- Modified message_mark table
CREATE TABLE message_mark (
                              id         bigint   NOT NULL AUTO_INCREMENT COMMENT '主键id' PRIMARY KEY,
                              msg_id     bigint   NOT NULL COMMENT '消息id',
                              user_id    bigint   NOT NULL COMMENT '执行操作的用户id',
                              likeFlag   int      NOT NULL DEFAULT 0 COMMENT '是否喜欢',
                              dislikeFlag int     NOT NULL DEFAULT 0 COMMENT '是否不喜欢',
                              create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              INDEX idx_message_mark_msg_id (msg_id),
                              INDEX idx_message_mark_user_id (user_id)
);

-- Modified user table - added default values
CREATE TABLE user (
                      id            bigint       NOT NULL AUTO_INCREMENT COMMENT '主键id' PRIMARY KEY,
                      open_id       varchar(255) NOT NULL COMMENT '用户openId',
                      nick_name     varchar(255) NOT NULL DEFAULT '用户' COMMENT '用户昵称',
                      avatar        varchar(255) NOT NULL DEFAULT 'default_avatar.png' COMMENT '用户头像',
                      sex           int          NOT NULL COMMENT '0:男，1：女',
                      birthday      date         COMMENT '生日',
                      province      varchar(255) COMMENT '省份',
                      city          varchar(255) COMMENT '城市',
                      country       varchar(255) COMMENT '国家',
                      register_time date         NOT NULL COMMENT '注册时间',
                      create_time   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                      update_time   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                      UNIQUE INDEX idx_user_open_id (open_id)
);

-- Modified file_message table
CREATE TABLE file_message (
                              id          bigint       NOT NULL AUTO_INCREMENT COMMENT '主键id' PRIMARY KEY,
                              msg_id      bigint       NOT NULL COMMENT '消息id',
                              url         varchar(255) NOT NULL COMMENT '文件url地址',
                              size        int          NOT NULL COMMENT '文件大小',
                              extra       varchar(255) COMMENT '附加信息',
                              type        int          NOT NULL COMMENT '文件类型(1:音频，2:视频，3:图片，4:普通文件)',
                              create_time datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              update_time datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              INDEX idx_file_message_msg_id (msg_id)
);

-- Modified message table
CREATE TABLE message (
                         id           bigint unsigned AUTO_INCREMENT COMMENT '消息id' PRIMARY KEY,
                         from_uid     bigint          NOT NULL COMMENT '发送者id',
                         room_id      bigint          NOT NULL COMMENT '房间id',
                         content      longtext        COMMENT '消息内容',
                         type         int             NOT NULL COMMENT '消息内容类型 （0：文本消息，1:音频消息,2:视频消息，3:图片，4:普通文件)',
                         reply_msg_id bigint          COMMENT '回复的消息id',
                         send_time    datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
                         update_time  datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                         status       int             NOT NULL DEFAULT 0 COMMENT '0未撤回，1已撤回',
                         extra        varchar(255)    COMMENT '扩展信息',
                         INDEX idx_message_send_time (send_time),
                         INDEX idx_message_room_id (room_id),
                         INDEX idx_message_from_uid (from_uid)
);

-- Modified room table - changed avatar_url to avatar with default value and made description NOT NULL with default value
CREATE TABLE room (
                      id            bigint unsigned AUTO_INCREMENT COMMENT '主键id' PRIMARY KEY,
                      room_name     varchar(255) NOT NULL COMMENT '房间名称',
                      avatar        varchar(255) NOT NULL DEFAULT 'default_room_avatar.png' COMMENT '房间头像',
                      description   varchar(255) NOT NULL DEFAULT '' COMMENT '房间描述',
                      delete_status int          NOT NULL DEFAULT 0 COMMENT '0未删除，1已删除',
                      create_time   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                      update_time   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                      extra         varchar(255) COMMENT '扩展描述',
                      type          int          NOT NULL COMMENT '0多人群，1：1对1房间'
);

-- Modified room_member table - added default value for delete_status
CREATE TABLE room_member (
                             id            bigint unsigned AUTO_INCREMENT COMMENT '主键id' PRIMARY KEY,
                             room_id       bigint   NOT NULL COMMENT '房间id',
                             uid1          bigint   NOT NULL COMMENT '用户id',
                             uid2          bigint,
                             create_time   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             update_time   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             delete_status int      NOT NULL DEFAULT 0 COMMENT '0未删除，1已删除',
                             INDEX idx_room_member_room_id (room_id),
                             INDEX idx_room_member_uid1 (uid1),
                             INDEX idx_room_member_uid2 (uid2)
);

-- Modified user_friend table - added default value for delete_status
CREATE TABLE user_friend (
                             id            bigint  AUTO_INCREMENT COMMENT '主键id' PRIMARY KEY,
                             uid1          bigint   NOT NULL COMMENT '用户a',
                             uid2          bigint   NOT NULL COMMENT '用户b',
                             create_time   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             update_time   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             delete_status tinyint  NOT NULL DEFAULT 0 COMMENT '0：未被删除，1：已被删除',
                             INDEX idx_user_friend_uid1 (uid1),
                             INDEX idx_user_friend_uid2 (uid2),
                             UNIQUE INDEX idx_user_friend_relation (uid1, uid2)
);

-- Modified user_friend_apply table
CREATE TABLE user_friend_apply (
                                   id            bigint  AUTO_INCREMENT COMMENT '主键id' PRIMARY KEY,
                                   uid           bigint       NOT NULL COMMENT '申请人id',
                                   target_id     bigint       NOT NULL COMMENT '申请的目标对象id',
                                   apply_time    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请发起时间',
                                   apply_msg     varchar(255)  COMMENT '申请消息内容',
                                   accept_status int          NOT NULL COMMENT '0：请求被拒绝，1：请求被接受 2:请求还未被处理',
                                   update_time   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   INDEX idx_user_friend_apply_uid (uid),
                                   INDEX idx_user_friend_apply_target_id (target_id)
);

-- Modified user_conversation table - removed last_read_time as requested
CREATE TABLE user_conversation (
                                   id             bigint  AUTO_INCREMENT COMMENT '主键id' PRIMARY KEY,
                                   uid            bigint       NOT NULL COMMENT '用户id',
                                   room_id        bigint       NOT NULL COMMENT '房间id',
                                   create_time    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '会话创建时间',
                                   update_time    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   extra          varchar(255)  COMMENT '扩展描述',
                                   INDEX idx_user_conversation_uid (uid),
                                   INDEX idx_user_conversation_room_id (room_id),
                                   UNIQUE INDEX idx_user_room_unique (uid, room_id)
);