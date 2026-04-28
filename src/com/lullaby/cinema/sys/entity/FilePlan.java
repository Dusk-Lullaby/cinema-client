package com.lullaby.cinema.sys.entity;

import java.util.Date;

/**
 * 影片播放计划
 */
public class FilePlan {
    /**
     * 编号
     */
    private String id;
    /**
     * 影片
     */
    private Film film;
    /**
     * 影厅
     */
    private FilmHall filmHall;
    /**
     * 开始时间
     */
    private Date begin;
    /**
     * 结束时间
     */
    private Date end;
}
