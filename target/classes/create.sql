create table admin_info
(
    admin_id       int auto_increment primary key,
    admin_username varchar(50)  not null,
    admin_password varchar(100) not null,
    admin_email    varchar(100) not null,
    constraint admin_email unique (admin_email),
    constraint admin_username unique (admin_username)
)
    comment '管理员信息表';

create table class_info
(
    class_id             int auto_increment
        primary key,
    admin_username       varchar(50)   not null,
    class_name           varchar(100)  not null,
    class_student_number int default 0 not null,
    admin_id             int           not null,
    constraint class_name
        unique (class_name)
)
    comment '班级信息表';

create table group_info
(
    group_id           int auto_increment
        primary key,
    group_name         varchar(30)                        not null,
    group_pro_name     varchar(30)                        not null,
    group_score        decimal(5, 2) default 0.00         null,
    group_is_available tinyint       default 1            not null,
    group_leader       varchar(10)                        not null,
    group_notice       text                               null,
    group_finish       tinyint       default 0            not null,
    class_id           int                                not null,
    gitee_url          varchar(255)  default '未加入仓库' null
)
    comment '小组信息表';

create table student_group
(
    student_id           int                               not null
        primary key,
    student_name         varchar(10)                       not null,
    student_number       varchar(20)                       not null,
    group_id             int          default -1           null,
    student_position     tinyint      default 0            not null,
    student_apply_reason varchar(20)                       null,
    student_notice       text                              null,
    gitee_url            varchar(255) default '未加入仓库' null
)
    comment '学生小组信息表';

create table student_info
(
    student_id       int auto_increment
        primary key,
    student_username varchar(50)  not null,
    student_name     varchar(100) not null,
    student_number   varchar(20)  not null,
    student_password varchar(100) not null,
    student_email    varchar(100) not null,
    class_id         int          not null,
    constraint student_email
        unique (student_email),
    constraint student_username
        unique (student_username)
)
    comment '学生信息表';

create table student_score
(
    student_id     int                        not null
        primary key,
    student_name   varchar(10)                not null,
    student_number varchar(20)                not null,
    group_pro_name varchar(30)                not null,
    group_score    decimal(5, 2) default 0.00 null,
    group_id       int           default -1   null,
    student_finish tinyint       default 0    not null,
    class_id int not null
)
    comment '学生得分表';