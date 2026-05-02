package com.lullaby.cinema.sys.starter;

import com.lullaby.cinema.sys.action.UserAction;
import com.lullaby.cinema.sys.menu.Menu;
import com.lullaby.cinema.sys.menu.MenuManger;
import com.lullaby.cinema.sys.util.InputUtil;

import java.util.List;
import java.util.Map;

/**
 * 影院客户端
 */
public class CinemaClient {

    public static void main(String[] args) {
//        MenuManger.showMenu(MenuManger.LOGIN_MENUS);
//        int input = InputUtil.getInputInteger("请选择菜单编号", 1, MenuManger.LOGIN_MENUS.length);
//        System.out.println(input);
//        Date date = InputUtil.getInputDate("请输入日期：");
//        System.out.println(date);
        showInterface(MenuManger.LOGIN_MENUS);
    }

    /**
     * 展示界面
     * @param menus 菜单
     */
    public static void showInterface(Menu[] menus) {
        MenuManger.showMenu(menus);
        int number = InputUtil.getInputInteger("请选择菜单编号", 1, MenuManger.LOGIN_MENUS.length);
        Menu select = menus[number - 1];
        switch (select.getAction()) {
            case "login":
                Map<String, Object> result = UserAction.login();
                if (result == null) {   // 登录失败
                    System.out.println("账号或密码错误，请稍后重试");
                    showInterface(MenuManger.LOGIN_MENUS);
                } else {
                    int process = (int) result.get("process");
                    if (process == 1) { // 登录成功
                        boolean isManager = (boolean) result.get("manager");
                        Menu[] mainMenus = isManager ? MenuManger.MANAGER_MENUS : MenuManger.USER_MENUS;
                        showInterface(mainMenus);
                    } else {
                        String msg;
                        if (process == 0) { // 账号或密码错误
                            msg = "账号或密码错误，请稍后重试";
                        } else if (process == -1) { // 账号不存在
                            msg = "账号不存在，请先注册";
                        } else {
                            msg = "账号已被冻结，请申请解冻";
                        }
                        System.out.println(msg);
                        showInterface(MenuManger.LOGIN_MENUS);
                    }
                }
                break;
            case "register":
                UserAction.register();
                showInterface(MenuManger.LOGIN_MENUS);
                break;
            case "getPasswordBack":
                UserAction.getPasswordBack();
                showInterface(MenuManger.LOGIN_MENUS);
                break;
            case "unfrozenApply":
                UserAction.unfrozenApply();
                showInterface(MenuManger.LOGIN_MENUS);
                break;
            case "quit":
                UserAction.quit();
                break;
            case "showChildren":
                List<Menu> children = select.getChildren();
                Menu[] childrenMenus = children.toArray(new Menu[children.size()]);
                showInterface(childrenMenus);
                break;
            case "goBackLogin":
                showInterface(MenuManger.LOGIN_MENUS);
                break;
            case "goBackOrder":
                showInterface(MenuManger.USER_MENUS);
                break;
            case "addFile": // 增加影片
                UserAction.addFilm();
                showSameLevelMenu(select);
                break;
            case "updateFile": // 修改影片
                UserAction.updateFilm();
                showSameLevelMenu(select);
                break;
            case "deleteFile": // 删除影片
                UserAction.deleteFilm();
                showSameLevelMenu(select);
                break;
            case "getFilmList": // 查看影片
                UserAction.getFilmList();
                showSameLevelMenu(select);
                break;
            default:    // 其他子菜单操作，需要重新展示与该子菜单同级的菜单
                showSameLevelMenu(select);
        }
    }

    /**
     * 展示与选择的子菜单同级的子菜单列表
     * @param select 选择的菜单
     */
    private static void showSameLevelMenu(Menu select) {
        Menu parent = select.getParent();
        List<Menu> menuList = parent.getChildren();
        Menu[] menus = menuList.toArray(new Menu[menuList.size()]);
        showInterface(menus);
    }

//    private static void showLoginMenu() {
//        MenuManger.showMenu(MenuManger.LOGIN_MENUS);
//        int number = InputUtil.getInputInteger("请选择菜单编号", 1, MenuManger.LOGIN_MENUS.length);
//        Menu select = MenuManger.LOGIN_MENUS[number - 1];
//        switch (select.getAction()) {
//            case "login":
//                UserAction.login();
//                showMainMenu();
//                break;
//            case "register":
//                UserAction.register();
//                showLoginMenu();
//                break;
//            case "getPasswordBack":
//                UserAction.getPasswordBack();
//                showLoginMenu();
//                break;
//            case "unfrozenApply":
//                UserAction.unfrozenApply();
//                showLoginMenu();
//                break;
//            case "quit":
//                UserAction.quit();
//                break;
//        }
//    }

//    public static void showMainMenu() {
//        MenuManger.showMenu(MenuManger.USER_MENUS);
//        int number = InputUtil.getInputInteger("请选择菜单编号", 1, MenuManger.LOGIN_MENUS.length);
//        Menu select = MenuManger.USER_MENUS[number - 1];
//        switch (select.getAction()) {
//            case "showChildren":
//                showChildren(select);
//                break;
//            case "goBackLogin":
//                showLoginMenu();
//                break;
//        }
//    }

//    private static void showChildren(Menu parent) {
//        List<Menu> children = parent.getChildren();
//        Menu[] menus = children.toArray(new Menu[children.size()]);
//        MenuManger.showMenu(menus);
//        MenuManger.showMenu(MenuManger.USER_MENUS);
//        int number = InputUtil.getInputInteger("请选择菜单编号", 1, menus.length);
//        Menu select = menus[number - 1];
//        switch (select.getAction()) {
//            case "goBackLogin":
//                showMainMenu();
//                break;
//            default:
//                showChildren(parent);
//        }
//    }
}
