package com.lullaby.cinema.sys.entity;
// 实体包的命名：entity 实体 model 数据模型
// bo(business object)业务对象 vo （view object）视图对象 dto（data transfer object） 数据传输对象
// pojo（plain ordinary java object）简单java对象 domain 领域模型

/**
 * 用户类
 */
public class User {
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 安全码
     */
    private String securityCode;
    /**
     * 是否是管理员
     */
    private boolean manager;
    /**
     * 状态：1-正常 0-冻结
     */
    private int state;
}
