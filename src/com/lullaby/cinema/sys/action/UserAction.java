package com.lullaby.cinema.sys.action;

import com.lullaby.cinema.sys.entity.Film;
import com.lullaby.cinema.sys.entity.UnfrozenApply;
import com.lullaby.cinema.sys.entity.User;
import com.lullaby.cinema.sys.message.Message;
import com.lullaby.cinema.sys.util.IdGenerator;
import com.lullaby.cinema.sys.util.InputUtil;
import com.lullaby.cinema.sys.util.SocketUtil;

import java.awt.image.FilteredImageSource;
import java.util.List;
import java.util.Map;

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
    public static Map<String, Object> login() {
        String username = InputUtil.getInputText("请输入账号：");
        String password = InputUtil.getInputText("请输入密码：");
        User user = new User(username, password, null);
        Message<User> msg = new Message<>("login", user);
        return SocketUtil.sendMessage(msg);
    }

    /**
     * 找回密码
     */
    public static void getPasswordBack() {
        String username = InputUtil.getInputText("请输入账号：");
        String securityCode = InputUtil.getInputText("请输入安全码：");
        User user = new User(username, null, securityCode);
        Message<User> msg = new Message<>("getPasswordBack", user);
        String result = SocketUtil.sendMessage(msg);
        if (result == null) {
            System.out.println("安全码不正确，请重新尝试");
        } else  {
            System.out.println("您的密码是" + result);
        }
    }

    /**
     * 申请解冻
     */
    public static void unfrozenApply() {
        String username = InputUtil.getInputText("请输入账号：");
        String reason = InputUtil.getInputText("请输入理由：");
        UnfrozenApply unfrozenApply = new UnfrozenApply(IdGenerator.generateId(10), username, reason);
        Message<UnfrozenApply> msg = new Message<>("unfrozenApply", unfrozenApply);
        Integer result = SocketUtil.sendMessage(msg);
        if (result == null || result == 0) {
            System.out.println("解冻申请失败，请稍后重试");
        } else if (result == 1) {
            System.out.println("解冻申请发送成功");
        } else {
            System.out.println("账号未被冻结，无需申请");
        }
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
        String name = InputUtil.getInputText("请输入影片名称");
        Message<String> msg = new Message<>("getFilmList", name);
        List<Film> films = SocketUtil.sendMessage(msg);
        if (films == null || films.isEmpty()) {
            System.out.println("未找到与\"" + name + "\"相关的影片信息");
        } else {
            System.out.println("影片编号\t\t影片名称\t制片人\t影片描述\t");
            films.forEach(System.out::println);
        }
    }

    /**
     * 添加影片
     */
    public static void addFilm() {
        String name = InputUtil.getInputText("请输入影片名称");
        String producer = InputUtil.getInputText("请输入制片人");
        String description = InputUtil.getInputText("请输入影片描述");
        Film film = new Film(IdGenerator.generateId(10), name, producer, description);
        Message<Film> message = new Message<>("addFilm", film);
        Integer result = SocketUtil.sendMessage(message);
        if (result == null || result == 0) {
            System.out.println("添加失败，请稍后重试");
        } else {
            System.out.println("添加成功");
        }
    }

    /**
     * 修改影片
     */
    public static void updateFilm() {
        String id = InputUtil.getInputText("请输入影片编号");
        String name = InputUtil.getInputText("请输入影片名称");
        String producer = InputUtil.getInputText("请输入制片人");
        String description = InputUtil.getInputText("请输入影片描述");
        Film film = new Film(id, name, producer, description);
        Message<Film> message = new Message<>("UpdateFilm", film);
        Integer result = SocketUtil.sendMessage(message);
        if (result == null || result == 0) {
            System.out.println("修改失败，请稍后重试");
        } else if (result == 1) {
            System.out.println("修改成功");
        } else {
            System.out.println("未找到与\"" + id + "\"相关的影片信息");
        }
    }

    /**
     * 删除影片
     */
    public static void deleteFilm() {
        String id = InputUtil.getInputText("请输入影片编号");
        Message<String> msg = new Message<>("deleteFilm", id);
        Integer result = SocketUtil.sendMessage(msg);
        if (result == null || result == 0) {
            System.out.println("删除失败，请稍后重试");
        } else if (result == 1) {
            System.out.println("删除成功");
        } else {
            System.out.println("未找到与\"" + id + "\"相关的影片信息");
        }
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
