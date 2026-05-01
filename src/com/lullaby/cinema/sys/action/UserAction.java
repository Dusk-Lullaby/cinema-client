package com.lullaby.cinema.sys.action;

import com.lullaby.cinema.sys.entity.User;
import com.lullaby.cinema.sys.message.Message;
import com.lullaby.cinema.sys.util.InputUtil;
import com.lullaby.cinema.sys.util.SocketUtil;

/**
 * 用户行为
 */
public class UserAction {

    /**
     * 注册
     */
    public static void register() {
        String username = InputUtil.getInputText("请输入账号：");
        String password = InputUtil.getInputText("请输入密码：");
        String securityCode = InputUtil.getInputText("请输入安全码：");
        User user = new User(username, password, securityCode);
        Message<User> msg = new Message<>("register", user);
        Integer result = SocketUtil.sendMessage(msg);
        if (result != null && result == 1) {
            System.out.println("注册成功");
        } else if (result != null && result == -1) {
            System.out.println("账号已被注册");
        } else {
            System.out.println("注册失败，请稍后重试");
        }
    }

    /**
     * 登录
     */
    public static void login() {

    }

    /**
     * 找回密码
     */
    public static void getPasswordBack() {

    }

    /**
     * 申请解冻
     */
    public static void unfrozenApply() {

    }

    /**
     * 退出系统
     */
    public static void quit() {
        System.out.println("感谢使用影院选票系统");
        System.exit(0);
    }

    /**
     * 查看订单
     */
    public static void getOrderList() {

    }

    /**
     * 修改订单
     */
    public static void updateOrder() {

    }

    /**
     * 取消订单
     */
    public static void cancelOrder() {

    }

    /**
     * 审核订单
     */
    public static void auditOrder() {

    }

    /**
     * 查看影片
     */
    public static void getFilmList() {

    }

    /**
     * 添加影片
     */
    public static void addFilm() {

    }

    /**
     * 修改影片
     */
    public static void updateFilm() {

    }

    /**
     * 删除影片
     */
    public static void deleteFilm() {

    }

    /**
     * 查看影厅
     */
    public static void getFilmHallList() {

    }

    /**
     * 添加影厅
     */
    public static void addFilmHall() {

    }

    /**
     * 删除影厅
     */
    public static void deleteFilmHall() {

    }

    /**
     * 修改影厅
     */
    public static void updateFilmHall() {

    }

    /**
     * 查看播放计划
     */
    public static void getFilmPlanList() {

    }

    /**
     * 添加播放计划
     */
    public static void addFilmPlanList() {

    }

    /**
     * 删除播放计划
     */
    public static void deleteFilmPlanList() {

    }

    /**
     * 更新播放计划
     */
    public static void updateFilmPlanList() {

    }

    /**
     * 查看用户
     */
    public static void getUserList() {

    }

    /**
     * 冻结用户
     */
    public static void frozenUser() {

    }

    /**
     * 解冻用户
     */
    public static void unfrozenUser() {

    }

    /**
     * 查看解冻申请
     */
    public static void getUnfrozenApplyList() {

    }
 }
