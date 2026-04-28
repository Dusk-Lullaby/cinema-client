package com.lullaby.cinema.sys.entity;

import java.util.Date;

/**
 * 订单
 */
public class Order {
    /**
     * 编号
     */
    private String id;
    /**
     * 影片名称
     */
    private String fileName;
    /**
     * 开始时间
     */
    private Date begin;
    /**
     * 结束时间
     */
    private Date end;
    /**
     * 座位信息
     */
    private String seatInfo;
    /**
     * 状态 0-退订中 1-正常 2-已经退订
     */
    private int state;
    /**
     * 拥有者
     */
    private String owner;
}
