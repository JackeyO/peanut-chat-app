create database if not exists peanut-chat;

use peanut-chat;

create table file_message
(
    id     int          not null comment '主键id'
        primary key,
    msg_id int          not null comment '消息id',
    url    varchar(255) not null comment '音频url地址',
    size   bigint       not null comment '文件大小',
    extra  varchar(255) null comment '附加信息',
    type   int          not null comment '文件类型(包括音频，视频)'
);

create table message
(
    id           int unsigned auto_increment comment '消息id'
        primary key,
    from_uid     int          not null comment '发送者id',
    room_id      int          null comment '房间id',
    type         int          not null comment '消息内容类型 （0：文本消息，1:音频消息,2:视频消息)',
    send_time    datetime     not null comment '发送时间',
    reply_msg_id int          null comment '回复的消息id',
    update_time  datetime     not null comment '更新时间',
    status       int          not null comment '0未撤回，1已撤回',
    extra        varchar(255) null comment '扩展信息'
);

create table message_mark
(
    id      int not null comment '主键id'
        primary key,
    msg_id  int not null comment '消息id',
    user_id int not null comment '执行操作的用户id',
    `like`  int not null comment '是否喜欢',
    dislikeFlag int not null comment '是否不喜欢'
);

create table room
(
    id            int unsigned auto_increment comment '主键id'
        primary key,
    room_name     varchar(255) not null comment '房间名称',
    avatar_url    varchar(255) not null comment '房间头像url',
    description   varchar(255) null comment '房间描述',
    delete_status int          not null comment '0未删除，1已删除',
    create_time   datetime     not null comment '创建时间',
    update_time   datetime     not null comment '更新时间',
    extra         varchar(255) null comment '扩展描述',
    type          int          not null comment '0多人群，1：1对1房间'
);

create table room_action
(
    id               int unsigned auto_increment comment '主键id'
        primary key,
    room_id          int      not null comment '房间id',
    last_active_time datetime not null comment '最后一次活跃时间',
    hot_flag         int      not null comment '0不是热房间，1：是热房间',
    last_msg_id      int      not null comment '该房间最后一条消息id'
);

create table room_member
(
    id            int unsigned auto_increment comment '主键id'
        primary key,
    room_id       int      not null comment '房间id',
    uid1          int      not null comment '用户id',
    uid2          int      null,
    create_time   datetime not null comment '创建时间',
    update_time   datetime not null comment '更新时间',
    delete_status int      not null comment '0未删除，1已删除'
);

create table text_message
(
    id      int          not null comment '主键id'
        primary key,
    msg_id  int          not null comment '消息id',
    content varchar(255) null comment '文本内容'
);

create table user
(
    id            int          not null comment '主键id'
        primary key,
    open_id       varchar(255) not null comment '用户openId',
    nick_name     varchar(255) null comment '用户昵称',
    sex           int          null comment '0:男，1：女',
    birthday      date         null comment '生日',
    province      varchar(255) null comment '省份',
    city          varchar(255) null comment '城市',
    country       varchar(255) null comment '国际啊',
    register_time date         null comment '注册事件'
);

create table user_friend
(
    id            int unsigned auto_increment comment '主键id'
        primary key,
    uid1          int      not null comment '用户a',
    uid2          int      not null comment '用户b',
    create_time   datetime not null comment '创建时间',
    update_time   datetime not null comment '更新时间',
    delete_status tinyint  not null comment '0：未被删除，1：已被删除'
);

create table user_friend_apply
(
    id            int unsigned auto_increment comment '主键id'
        primary key,
    uid           int          not null comment '申请人id',
    target_id     int          not null comment '申请的目标对象id',
    apply_time    datetime     not null comment '申请发起时间',
    accept_status int          not null comment '0：请求被拒绝，1：请求被接受',
    apply_msg     varchar(255) null comment '申请消息内容'
);

create table user_session
(
    id             int unsigned auto_increment comment '主键id'
        primary key,
    uid            int          not null comment '用户id',
    room_id        int          not null comment '房间id',
    last_read_time datetime     not null comment '该用户最后一次读该房间内的消息的时间',
    extra          varchar(255) null comment '扩展描述 ',
    create_time    datetime     null comment '会话创建时间'
);

