package com.lullaby.cinema.sys.entity;

/**
 * 影厅
 */
public class FilmHall {
    /**
     * 编号
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 总行数
     */
    private int totalRow;
    /**
     * 总列数
     */
    private int totalCol;
    /**
     * 座位
     */
    private Seat[][] seats;
}
