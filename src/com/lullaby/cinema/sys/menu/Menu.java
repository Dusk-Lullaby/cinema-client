package com.lullaby.cinema.sys.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 菜单
 */
public class Menu {
    /**
     * 菜单编号
     */
    private int order;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 触发的行为
     */
    private String action;
    /**
     * 子菜单列表
     */
    private List<Menu> children = new ArrayList<>();
    /**
     * 父菜单
     */
    private Menu parent;

    public Menu(int order, String name, String action) {
       this(order, name, action, null);
    }

    public Menu(int order, String name, String action, Menu parent) {
        this.order = order;
        this.name = name;
        this.action = action;
        this.parent = parent;
    }

    /**
     * 添加子菜单
     * @param child 子菜单
     */
    public void addChild(Menu child) {
        children.add(child);
    }

    @Override
    public String toString() {
        return order + "." + name;
    }

    public String getAction() {
        return action;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public Menu getParent() {
        return parent;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Menu menu = (Menu) object;
        return order == menu.order && Objects.equals(name, menu.name) && Objects.equals(action, menu.action) && Objects.equals(children, menu.children) && Objects.equals(parent, menu.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, name, action, children, parent);
    }
}
