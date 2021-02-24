package src.menu;

import src.action.IAction;

public class MenuItem implements IAction{

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

    public void doAction(){}

    public Menu getNextMenu(){
        return nextMenu;
    }
}
