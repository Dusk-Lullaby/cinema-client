package com.lullaby.cinema.sys.action;

import com.lullaby.cinema.sys.entity.*;
import com.lullaby.cinema.sys.message.Message;
import com.lullaby.cinema.sys.util.IdGenerator;
import com.lullaby.cinema.sys.util.InputUtil;
import com.lullaby.cinema.sys.util.SocketUtil;

import java.util.*;

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
        Message<String> message = new Message<>("getOrderList", null);
        List<Order> orders = SocketUtil.sendMessage(message);
        if (orders == null || orders.isEmpty()) {
            System.out.println("当前并无订单信息");
        } else {
            System.out.println("订单编号\t\t\t影片名称\t开始时间\t\t\t\t结束时间\t\t\t\t座位信息\t订单状态\t所属用户");
            orders.forEach(System.out::println);
        }
    }

    /**
     * 在线订座
     * @param currentUsername 用户名
     */
    public static void orderSeatOnline(String currentUsername) {
        Message<String> message = new Message<>("getFilmPlan", null);
        List<FilmPlan> filmPlans = SocketUtil.sendMessage(message);
        if (filmPlans == null || filmPlans.isEmpty()) {
            System.out.println("暂无影票售卖");
        } else {
            System.out.println("播放计划编号\t\t\t影片名称\t影片描述\t影厅名称\t开始时间\t\t\t\t结束时间\t\t\t\t余票");
            filmPlans.forEach(System.out::println);
            while (true) {
                String planId = InputUtil.getInputText("请输入播放计划编号");
                Optional<FilmPlan> optionalFilmPlan = filmPlans.stream().filter(filmPlan -> filmPlan.getId().equals(planId)).findFirst();
                if (optionalFilmPlan.isPresent()) {
                    FilmPlan plan = optionalFilmPlan.get();
                    FilmHall hall = plan.getFilmHall();
                    if (hall.getRestTicket() > 0) {
                        hall.showSeats();
                        while (true) {
                            int row = InputUtil.getInputInteger("请选择排号", 0, hall.getTotalRow() - 1);
                            int col = InputUtil.getInputInteger("请选择列号", 0, hall.getTotalCol() - 1);
                            if (hall.hasOwner(row, col)) {  // 如果座位已经售卖
                                System.out.println("座位：第" + row + "第" + col + "列已经售卖，请重新选择");
                            } else {
                                Map<String, Object> data = new HashMap<>();
                                data.put("planId", planId);
                                data.put("row", row);
                                data.put("col", col);
                                data.put("username", currentUsername);
                                Message<Map<String, Object>> mapMessage = new Message<>("orderSeatOnline", data);
                                Integer result = SocketUtil.sendMessage(mapMessage);
                                if (result == null || result == 0) {
                                    System.out.println("座位订购失败，请稍后重试");
                                } else {
                                    System.out.println("座位订购成功");
                                }
                                break;
                            }
                        }
                    } else {
                        System.out.println("当前影票已经售完");
                    }
                    break;
                } else {
                    System.out.println("播放计划编号输入有误，请重新输入");
                }
            }
        }
    }

    /**
     * 查看用户订单
     */
    public static void getUserOrderList(String username) {
        Message<String> message = new Message<>("getUserOrderList", username);
        List<Order> orders = SocketUtil.sendMessage(message);
        if (orders == null || orders.isEmpty()) {
            System.out.println("当前并无订单信息");
        } else {
            System.out.println("订单编号\t\t\t影片名称\t开始时间\t\t\t\t结束时间\t\t\t\t座位信息\t订单状态\t所属用户");
            orders.forEach(System.out::println);
        }
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
        String orderId = InputUtil.getInputText("请输入订单编号");
        Message<String> message = new Message<>("cancelOrder", orderId);
        Integer result = SocketUtil.sendMessage(message);
        if (result == null || result == 0) {
            System.out.println("订单取消失败，请稍后重试");
        } else if (result == 1) {
            System.out.println("订单取消中");
        } else if (result == -1){
            System.out.println("未找到与\"" + orderId + "\"相关订单信息");
        } else {
            System.out.println("订单正在取消中或已经退订，无需再取消");
        }
    }

    /**
     * 审核订单
     */
    public static void auditOrder() {
        Message<Integer> message = new Message<>("getOrderList", 0);
        List<Order> orders = SocketUtil.sendMessage(message);
        if (orders == null || orders.isEmpty()) {
            System.out.println("当前并无可审核订单");
        } else {
            while (true) {
                String orderId = InputUtil.getInputText("请输入订单编号");
                boolean exists = orders.stream().anyMatch(order -> order.getId().equals(orderId));
                if (exists) {
                    Message<String> msg = new Message<>("auditOrder", orderId);
                    Integer result = SocketUtil.sendMessage(msg);
                    if (result == null || result == 0) {
                        System.out.println("审核失败，请稍后重试");
                    } else {
                        System.out.println("审核成功");
                    }
                    break;
                } else {
                    System.out.println("订单编号输入有误");
                }
            }
        }
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
            System.out.println("影片编号\t\t\t\t影片名称\t制片人\t影片描述\t");
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
        Message<Film> message = new Message<>("updateFilm", film);
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
        Message<String> msg = new Message<>("getFilmHallList", null);
        List<FilmHall> filmHalls = SocketUtil.sendMessage(msg);
        if (filmHalls == null || filmHalls.isEmpty()) {
            System.out.println("暂无影厅信息");
        } else {
            System.out.println("影厅编号\t\t\t\t影厅名称\t座位数\t");
            filmHalls.forEach(System.out::println);
        }
    }

    /**
     * 添加影厅
     */
    public static void addFilmHall() {
        String name = InputUtil.getInputText("请输入影厅名称");
        int totalRow = InputUtil.getInputInteger("请输入影厅总排数", 5, 20);
        int totalCol = InputUtil.getInputInteger("请输入影厅总列数", 10, 15);
        FilmHall filmHall = new FilmHall(IdGenerator.generateId(10), name, totalRow, totalCol);
        Message<FilmHall> message = new Message<>("addFilmHall", filmHall);
        Integer result = SocketUtil.sendMessage(message);
        if (result == null || result == 0) {
            System.out.println("添加失败，请稍后重试");
        } else {
            System.out.println("添加成功");
        }
    }

    /**
     * 删除影厅
     */
    public static void deleteFilmHall() {
        String id = InputUtil.getInputText("请输入影厅编号");
        Message<String> message = new Message<>("deleteFilmHall", id);
        Integer result = SocketUtil.sendMessage(message);
        if (result == null || result == 0) {
            System.out.println("删除失败，请稍后重试");
        } else if (result == 1) {
            System.out.println("删除成功");
        } else {
            System.out.println("未找到与\"" + id + "\"相关的影厅信息");
        }
    }

    /**
     * 修改影厅
     */
    public static void updateFilmHall() {
        String id = InputUtil.getInputText("请输入影厅编号");
        String name = InputUtil.getInputText("请输入影厅名称");
        int totalRow = InputUtil.getInputInteger("请输入总行数", 5, 20);
        int totalCol = InputUtil.getInputInteger("请输入总列数", 10, 15);
        FilmHall filmHall = new FilmHall(id, name, totalRow, totalCol);
        Message<FilmHall> message = new Message<>("updateFilmHall", filmHall);
        Integer result = SocketUtil.sendMessage(message);
        if (result == null || result == 0) {
            System.out.println("修改失败，请稍后重试");
        } else if (result == 1) {
            System.out.println("修改成功");
        } else {
            System.out.println("未找到与\"" + id + "\"相关的影厅信息");
        }
    }

    /**
     * 查看播放计划
     */
    public static void getFilmPlan() {
        String name = InputUtil.getInputText("请输入影片名称");
        Message<String> message = new Message<>("getFilmPlan", name);
        List<FilmPlan> filmPlans = SocketUtil.sendMessage(message);
        if (filmPlans == null || filmPlans.isEmpty()) {
            System.out.println("未找到与\"" + name + "\"相关的播放计划信息");
        } else {
            System.out.println("播放计划编号\t\t\t影片名称\t影片描述\t影厅名称\t开始时间\t\t\t\t结束时间\t\t\t\t余票");
            filmPlans.forEach(System.out::println);
        }
    }

    /**
     * 添加播放计划
     */
    public static void addFilmPlan() {
        Message<String> searchFilmMsg = new Message<>("getFilmList", null);
        List<Film> films = SocketUtil.sendMessage(searchFilmMsg);
        if (films == null || films.isEmpty()) {
            System.out.println("暂无可播放的影片信息");
        } else {
            System.out.println("影片编号\t\t\t\t影片名称\t制片人\t影片描述\t");
            films.forEach(System.out::println);
            while (true) {
                String filmId = InputUtil.getInputText("请输入影片编号");
                Optional<Film> optionalFilm = films.stream().filter(film -> film.getId().equals(filmId)).findFirst();
                if (optionalFilm.isPresent()) {
                    Film film = optionalFilm.get(); // 选择影片
                    Message<String> searchFilmHallMsg = new Message<>("getFilmHallList", null);
                    List<FilmHall> filmHalls = SocketUtil.sendMessage(searchFilmHallMsg);
                    if (filmHalls == null || filmHalls.isEmpty()) {
                        System.out.println("当前并无可使用的影厅");
                    } else {
                        System.out.println("影厅编号\t\t\t\t影厅名称\t座位数\t");
                        filmHalls.forEach(System.out::println);
                        while (true) {
                            String filmHallId = InputUtil.getInputText("请输入影厅编号");
                            Optional<FilmHall> optionalFilmHall = filmHalls.stream().filter(filmHall -> filmHall.getId().equals(filmHallId)).findFirst();
                            if (optionalFilmHall.isPresent()) {
                                FilmHall filmHall = optionalFilmHall.get();
                                Date begin = InputUtil.getInputDate("请输入开始时间");
                                Date end = InputUtil.getInputDate("请输入结束时间");
                                FilmPlan filmPlan = new FilmPlan(IdGenerator.generateId(10), film, filmHall, begin, end);
                                Message<FilmPlan> message = new Message<>("addFilmPlan", filmPlan);
                                Integer result = SocketUtil.sendMessage(message);
                                if (result == null || result == 0) {
                                    System.out.println("添加失败，请稍后重试");
                                } else if (result == -1) {
                                    System.out.println("添加失败，播放时间冲突，请重新制定");
                                } else {
                                    System.out.println("添加成功");
                                }
                                break;
                            } else {
                                System.out.println("影厅编号输入有误，请重新输入");
                            }
                        }
                    }
                    break;
                } else {
                    System.out.println("影片编号输入有误，请重新输入");
                }
            }
        }
    }

    /**
     * 删除播放计划
     */
    public static void deleteFilmPlan() {
        String filmPlanId = InputUtil.getInputText("请输入播放计划编号");
        Message<String> message = new Message<>("deleteFilmPlan", filmPlanId);
        Integer result = SocketUtil.sendMessage(message);
        if (result == null || result == 0) {
            System.out.println("删除失败，请稍后重试");
        } else if (result == 1) {
            System.out.println("删除成功");
        } else {
            System.out.println("未找到与\"" + filmPlanId + "\"相关的播放计划信息");
        }
    }

    /**
     * 修改播放计划
     */
    public static void updateFilmPlan() {
        String filmPlanId = InputUtil.getInputText("请输入播放计划编号");
        Date begin = InputUtil.getInputDate("请输入开始时间");
        Date end = InputUtil.getInputDate("请输入结束时间");
        FilmPlan filmPlan = new FilmPlan(filmPlanId, null, null, begin, end);
        Message<FilmPlan> message = new Message<>("updateFilmPlan", filmPlan);
        Integer result = SocketUtil.sendMessage(message);
        if (result == null || result == 0) {
            System.out.println("更新失败，请稍后重试");
        } else if (result == 1) {
            System.out.println("更新成功");
        } else if (result == -2) {
            System.out.println("未找到与\"" + filmPlanId + "\"相关的播放计划信息");
        } else {
            System.out.println("播放时间冲突，请重新制定");
        }
    }

    /**
     * 查看用户
     */
    public static void getUserList() {
        Message<String> message = new Message<>("getUserList", null);
        List<User> users = SocketUtil.sendMessage(message);
        if (users == null || users.isEmpty()) {
            System.out.println("当前无可展示信息");
        } else {
            System.out.println("账号\t\t角色\t状态");
            users.forEach(System.out::println);
        }
    }

    /**
     * 冻结用户
     */
    public static void frozenUser() {
        String name = InputUtil.getInputText("请输入冻结账号");
        Message<String> msg = new Message<>("frozenUser", name);
        Integer result = SocketUtil.sendMessage(msg);
        if (result == null || result == 0) {
            System.out.println("冻结失败，请稍后重试");
        } else if (result == 1) {
            System.out.println("冻结成功");
        } else if (result == -1){
            System.out.println("未找到与\"" + name + "\"相关的用户信息");
        } else {
            System.out.println("账号\"" + name  + "\"已经被冻结，无需再冻结");
        }
    }

    /**
     * 解冻用户
     */
    public static void unfrozenUser() {
        Message<String> message = new Message<>("getUnfrozenApplyList", null);
        List<UnfrozenApply> unfrozenApplies = SocketUtil.sendMessage(message);
        if (unfrozenApplies == null || unfrozenApplies.isEmpty()) {
            System.out.println("当前无可展示解冻申请信息");
        } else {
            System.out.println("编号\t冻结账号\t\t原因\t状态");
            unfrozenApplies.forEach(System.out::println);
            String id = InputUtil.getInputText("请输入解冻申请编号");
            int number = InputUtil.getInputInteger("请输入解冻状态", 1, 2);
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("number", number);
            Message<Map<String, Object>> msg = new Message<>("unfrozenUser", map);
            Integer result = SocketUtil.sendMessage(msg);
            if (result == null || result == 0) {
                System.out.println("解冻失败，请稍后重试");
            } else if (result == 1) {
                System.out.println("解冻成功");
            } else if (result == -1){
                System.out.println("该解冻申请已处理，无需再次处理");
            } else {
                System.out.println("未找到\"" + id + "\"相关的解冻申请");
            }
        }
    }

    /**
     * 查看解冻申请
     */
    public static void getUnfrozenApplyList() {
        Message<String> message = new Message<>("getUnfrozenApplyList", null);
        List<UnfrozenApply> unfrozenApplies = SocketUtil.sendMessage(message);
        if (unfrozenApplies == null || unfrozenApplies.isEmpty()) {
            System.out.println("当前无可展示解冻申请信息");
        } else {
            System.out.println("编号\t冻结账号\t\t原因\t状态");
            unfrozenApplies.forEach(System.out::println);
        }
    }
 }