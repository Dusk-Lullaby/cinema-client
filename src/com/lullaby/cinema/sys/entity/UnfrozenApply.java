package com.lullaby.cinema.sys.entity;

/**
 * 解冻申请
 */
public class UnfrozenApply {
    /**
     * 编号
     */
    private String id;
    /**
     * 账号
     */
    private String username;
    /**
     * 原因
     */
    private String reason;
    /**
     * 处理状态 0-待处理 1-已通过 2-已驳回
     */
    private int state;
}
