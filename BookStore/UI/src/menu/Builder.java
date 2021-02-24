package src.menu;

import src.action.AddOrder;

//singleton
public class Builder {

    private static Builder instance;
    private Menu rootMenu;

    private Builder(){}

    public static Builder getInstance(){
        if (instance == null){
            instance = new Builder();
        }
        return instance;
    }

    public void buildMenu(){
        rootMenu = new Menu();
        rootMenu.addMenuItem(new MenuItem("Order menu",null, createOrderMenu()));
    }

    public Menu getRootMenu(){
        return rootMenu;
    }

    private Menu createOrderMenu() {
        var rootMenu = new Menu();
        rootMenu.addMenuItem(new MenuItem("Some action", () -> {
            System.out.println("Some action doing here and print");
        }, rootMenu));
        rootMenu.addMenuItem(new MenuItem("Add order", new AddOrder(), rootMenu));
        return rootMenu;
    }
}
