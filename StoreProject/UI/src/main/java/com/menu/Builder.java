package com.menu;

import com.action.BookSorter;
import com.action.RequestSorter;
import com.action.bookAction.AddBookToStock;
import com.action.bookAction.CreateBook;
import com.action.bookAction.DiscardBook;
import com.action.bookAction.GetBookDescription;
import com.action.orderAction.*;
import com.action.requestAction.AddRequest;
import com.action.requestAction.CloseRequest;
import com.action.OrderSorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Builder {

    private Menu rootMenu;
    private final ApplicationContext context;

    @Autowired
    public Builder(ApplicationContext context) {
        this.context = context;
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
        rootMenu.addMenuItem(new MenuItem("2 - See order details", context.getBean(GetOrderDetails.class),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("3 - Add order", context.getBean(AddOrder.class),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("4 - Cancel order", context.getBean(CancelOrder.class),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("5 - Change order status", context.getBean(ChangeOrderStatus.class),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("6 - Get money earned by period of time", context.getBean(PriceByPeriodOfTime.class),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("7 - Get orders done by period of time", context.getBean(OrderDoneByPeriodOfTime.class),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("8 - Back to root menu", ()-> System.out.println("-Back-"),
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
        rootMenu.addMenuItem(new MenuItem("2 - Create book", context.getBean(CreateBook.class),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("3 - Add book to stock", context.getBean(AddBookToStock.class),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("4 - Discard book", context.getBean(DiscardBook.class),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("5 - See books that not bought more that 6 month",
                ()-> System.out.println("-Choose sort for output-"), BookSortMenuSixMonthOld()));
        rootMenu.addMenuItem(new MenuItem("6 - See book description", context.getBean(GetBookDescription.class),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("7 - Back to root menu", ()-> System.out.println("-Back-"),
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
        rootMenu.addMenuItem(new MenuItem("2 - Add request", context.getBean(AddRequest.class),
                getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("3 - Close request", context.getBean(CloseRequest.class),
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
