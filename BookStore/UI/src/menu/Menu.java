package src.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu {

    private String name;
    private List<MenuItem> menuItems = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void addMenuItem(MenuItem item){
        menuItems.add(item);
    }

    @Override
    public String toString() {
        return "  " + name + Arrays.toString(menuItems.toArray()).replace("[", "")
                .replace("]", "")
                .replace(",", "");
    }
}
