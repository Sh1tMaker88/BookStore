package com.menu;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Navigator {

    private Menu currentMenu;

    public void printMenu() {
        System.out.println(currentMenu);
    }

    public void navigate(int index) {
        if (currentMenu != null) {
            MenuItem menuItem = currentMenu.getMenuItems().get(index);
            menuItem.doAction();
            currentMenu = menuItem.getNextMenu();
        }
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }
}
