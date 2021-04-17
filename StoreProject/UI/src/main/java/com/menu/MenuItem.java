package com.menu;

import com.action.IAction;

public class MenuItem implements IAction {

    private String title;
    private IAction action;
    private Menu nextMenu;

    public MenuItem(String title, IAction action, Menu nextMenu) {
        this.title = title;
        this.action = action;
        this.nextMenu = nextMenu;
    }

    @Override
    public void execute() {

    }

    public void doAction(){
        action.execute();
    }

    public Menu getNextMenu(){
        return nextMenu;
    }

    @Override
    public String toString() {
        return "\n" + title;
    }
}
