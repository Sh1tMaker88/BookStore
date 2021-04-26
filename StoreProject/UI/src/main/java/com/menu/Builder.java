package com.menu;

import com.action.BookSorter;
import com.action.RequestSorter;
import com.action.bookAction.AddBookToStock;
import com.action.bookAction.DiscardBook;
import com.action.orderAction.AddOrder;
import com.action.orderAction.CancelOrder;
import com.action.orderAction.ChangeOrderStatus;
import com.action.requestAction.AddRequest;
import com.action.requestAction.CloseRequest;
import com.action.OrderSorter;


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
        rootMenu.setName("<Root menu>");
        rootMenu.addMenuItem(new MenuItem("1 - Orders",() -> System.out.println("-Going to orders menu-\n"),
                createOrderMenu()));
        rootMenu.addMenuItem(new MenuItem("2 - Books", () -> System.out.println("-Going to books menu-\n"),
                createBookMenu()));
        rootMenu.addMenuItem(new MenuItem("3 - Requests", () -> System.out.println("-Going to requests menu-\n"),
                createRequestsMenu()));
        rootMenu.addMenuItem(new MenuItem("0 - Shut down program", null,null));
    }

    public Menu getRootMenu(){
        return rootMenu;
    }

    private Menu createOrderMenu() {
        var rootMenu = new Menu();
        rootMenu.setName("<Order menu>");
        rootMenu.addMenuItem(new MenuItem("1 - See all orders", () -> System.out.println("-Choose sort for output-"),
                OrderSortMenuGetAll()));
        rootMenu.addMenuItem(new MenuItem("2 - Add order", new AddOrder(),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("3 - Cancel order", new CancelOrder(),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("4 - Change order status", new ChangeOrderStatus(),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("5 - Back to root menu", ()-> System.out.println("-Back-"),
                getRootMenu()));
        return rootMenu;
    }

    private Menu OrderSortMenuGetAll() {
        var rootMenu = new Menu();
        rootMenu.setName("Get all sort by...");
        rootMenu.addMenuItem(new MenuItem("1 - Sort orders by ID", new OrderSorter(1, "getAll"),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("2 - Sort orders by status", new OrderSorter(2, "getAll"),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("3 - Sort orders by price", new OrderSorter(3, "getAll"),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("4 - Sort orders by date of completion", new OrderSorter(4, "getAll"),
                getRootMenu()));
        return rootMenu;
    }

    private Menu createBookMenu(){
        var rootMenu = new Menu();
        rootMenu.setName("<Book menu>");
        rootMenu.addMenuItem(new MenuItem("1 - See all books", ()-> System.out.println("-Choose sort for output-"),
                BookSortMenuGetAll()));
        rootMenu.addMenuItem(new MenuItem("2 - Add book to stock", new AddBookToStock(),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("3 - Discard book", new DiscardBook(),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("4 - See books that not bought more that 6 month",
                ()-> System.out.println("-Choose sort for output-"), BookSortMenuSixMonthOld()));
        rootMenu.addMenuItem(new MenuItem("5 - Back to root menu", ()-> System.out.println("-Back-"),
                getRootMenu()));
        return rootMenu;
    }

    private Menu BookSortMenuSixMonthOld(){
        var rootMenu = new Menu();
        rootMenu.setName("<Book sort menu>");
        rootMenu.addMenuItem(new MenuItem("1 - Sort by book ID", new BookSorter(1, "oldBooks"),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("2 - Sort by book name", new BookSorter(2, "oldBooks"),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("3 - Sort by book price", new BookSorter(3, "oldBooks"),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("4 - Sort by book year of publish", new BookSorter(4, "oldBooks"),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("5 - Sort by book availability", new BookSorter(5, "oldBooks"),
                getRootMenu()));
        return rootMenu;
    }

    private Menu BookSortMenuGetAll() {
        var rootMenu = new Menu();
        rootMenu.setName("Get all sort by...");
        rootMenu.addMenuItem(new MenuItem("1 - Sort by book ID", new BookSorter(1, "getAll"),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("2 - Sort by book name", new BookSorter(2, "getAll"),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("3 - Sort by book price", new BookSorter(3, "getAll"),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("4 - Sort by book year of publish", new BookSorter(4, "getAll"),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("5 - Sort by book availability", new BookSorter(5, "getAll"),
                getRootMenu()));
        return rootMenu;
    }

    private Menu createRequestsMenu(){
        var rootMenu = new Menu();
        rootMenu.setName("<Requests menu>");
        rootMenu.addMenuItem(new MenuItem("1 - See all requests", ()-> System.out.println("-Choose sort for output-"),
                RequestSortMenuGetAll()));
        rootMenu.addMenuItem(new MenuItem("2 - Add request", new AddRequest(),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("3 - Close request", new CloseRequest(),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("4 - Back to root menu", ()-> System.out.println("-Back-"),
                getRootMenu()));
        return rootMenu;
    }

    private Menu RequestSortMenuGetAll(){
        var rootMenu = new Menu();
        rootMenu.setName("Get all sort by...");
        rootMenu.addMenuItem(new MenuItem("1 - Sort by ID", new RequestSorter(1, "getAll"),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("2 - Sort by count of requests", new RequestSorter(2, "getAll"),
                getRootMenu()));
        return rootMenu;
    }
}
