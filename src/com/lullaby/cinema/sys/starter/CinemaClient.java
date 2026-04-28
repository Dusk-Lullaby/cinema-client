package com.lullaby.cinema.sys.starter;

import com.lullaby.cinema.sys.menu.MenuManger;
import com.lullaby.cinema.sys.util.InputUtil;

import java.util.Date;
import java.util.Scanner;

/**
 * 影院客户端
 */
public class CinemaClient {

    public static void main(String[] args) {
        MenuManger.showMenu(MenuManger.LOGIN_MENUS);
        int input = InputUtil.getInputInteger("请选择菜单编号", 1, MenuManger.LOGIN_MENUS.length);
        System.out.println(input);
        Date date = InputUtil.getInputDate("请输入日期：");
        System.out.println(date);
    }
}
