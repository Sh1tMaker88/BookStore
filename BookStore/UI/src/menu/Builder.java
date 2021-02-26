package src.menu;

import src.action.*;
import src.action.bookActions.AddBookToStock;
import src.action.bookActions.DiscardBook;
import src.action.bookActions.GetAllBooks;
import src.action.bookActions.GetBooksNotBoughtMoreThanSixMonth;
import src.action.orderActions.AddOrder;
import src.action.orderActions.CancelOrder;
import src.action.orderActions.ChangeOrderStatus;
import src.action.orderActions.GetAllOrders;
import src.action.requestActions.AddRequest;
import src.action.requestActions.GetAllRequests;

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
        rootMenu.setName("Root menu");
        rootMenu.addMenuItem(new MenuItem("1 - Orders",
                () -> System.out.println("-Going to orders menu-\n"), createOrderMenu()));
        rootMenu.addMenuItem(new MenuItem("2 - Books",
                () -> System.out.println("-Going to books menu-\n"), createBookMenu()));
        rootMenu.addMenuItem(new MenuItem("3 - Requests",
                () -> System.out.println("-Going to requests menu-\n"), createRequestsMenu()));
    }

    public Menu getRootMenu(){
        return rootMenu;
    }

    private Menu createOrderMenu() {
        var rootMenu = new Menu();
        rootMenu.setName("Order menu");
        rootMenu.addMenuItem(new MenuItem("1 - See all orders",
                () -> System.out.println("-Choose sort for output-"), OrderSortMenuGetAll()));
        rootMenu.addMenuItem(new MenuItem("2 - Add order", new AddOrder(), getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("3 - Cancel order", new CancelOrder(), getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("4 - Change order status", new ChangeOrderStatus(), getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("5 - Back to root menu", ()-> System.out.println("-Back-"), getRootMenu()));
        return rootMenu;
    }

    private Menu OrderSortMenuGetAll() {
        var rootMenu = new Menu();
        rootMenu.setName("Get all sort by...");
        rootMenu.addMenuItem(new MenuItem("1 - Sort orders by ID",
                new OrderSorter(1, new GetAllOrders().doIt()), getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("2 - Sort orders by status",
                new OrderSorter(2, new GetAllOrders().doIt()), getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("3 - Sort orders by price",
                new OrderSorter(3, new GetAllOrders().doIt()), getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("4 - Sort orders by date of completion",
                new OrderSorter(4, new GetAllOrders().doIt()), getRootMenu()));
        return rootMenu;
    }

    private Menu createBookMenu(){
        var rootMenu = new Menu();
        rootMenu.setName("Book creation menu");
        rootMenu.addMenuItem(new MenuItem("1 - See all books", ()-> System.out.println("-Choose sort for output-"), BookSortMenuGetAll()));
        rootMenu.addMenuItem(new MenuItem("2 - Add book to stock", new AddBookToStock(), getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("3 - Discard book", new DiscardBook(), getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("4 - See books that not bought more that 6 month",
                ()-> System.out.println("-Choose sort for output-"), BookSortMenuSixMonthOld()));
        rootMenu.addMenuItem(new MenuItem("5 - Back to root menu", ()-> System.out.println("-Back-"), getRootMenu()));
        return rootMenu;
    }

    private Menu BookSortMenuSixMonthOld(){
        var rootMenu = new Menu();
        rootMenu.setName("Book sort menu");
        rootMenu.addMenuItem(new MenuItem("1 - Sort by book ID",
                new BookSorter(1, new GetBooksNotBoughtMoreThanSixMonth().doIt()), getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("2 - Sort by book name",
                new BookSorter(2, new GetBooksNotBoughtMoreThanSixMonth().doIt()), getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("3 - Sort by book price",
                new BookSorter(3, new GetBooksNotBoughtMoreThanSixMonth().doIt()), getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("4 - Sort by book year of publish",
                new BookSorter(4, new GetBooksNotBoughtMoreThanSixMonth().doIt()), getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("5 - Sort by book availability",
                new BookSorter(5, new GetBooksNotBoughtMoreThanSixMonth().doIt()), getRootMenu()));
        return rootMenu;
    }

    private Menu BookSortMenuGetAll() {
        var rootMenu = new Menu();
        rootMenu.setName("Get all sort by...");
        rootMenu.addMenuItem(new MenuItem("1 - Sort by book ID",
                new BookSorter(1, new GetAllBooks().doIt()), getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("2 - Sort by book name",
                new BookSorter(2, new GetAllBooks().doIt()), getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("3 - Sort by book price",
                new BookSorter(3, new GetAllBooks().doIt()), getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("4 - Sort by book year of publish",
                new BookSorter(4, new GetAllBooks().doIt()), getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("5 - Sort by book availability",
                new BookSorter(5, new GetAllBooks().doIt()), getRootMenu()));
        return rootMenu;
    }

    private Menu createRequestsMenu(){
        var rootMenu = new Menu();
        rootMenu.setName("Requests menu");
        rootMenu.addMenuItem(new MenuItem("1 - See all requests",
                ()-> System.out.println("-Choose sort for output-"), RequestSortMenuGetAll()));
        rootMenu.addMenuItem(new MenuItem("2 - Add request",
                new AddRequest(), RequestSortMenuGetAll()));
        rootMenu.addMenuItem(new MenuItem("3 - Close request",
                new AddRequest(), RequestSortMenuGetAll()));
        rootMenu.addMenuItem(new MenuItem("4 - Back to root menu",
                ()-> System.out.println("-Back-"), getRootMenu()));
        return rootMenu;
    }

    private Menu RequestSortMenuGetAll(){
        var rootMenu = new Menu();
        rootMenu.setName("Get all sort by...");
        rootMenu.addMenuItem(new MenuItem("1 - Sort by ID",
                new RequestSorter(1, new GetAllRequests().doIt()), getRootMenu()));
        rootMenu.addMenuItem(new MenuItem("2 - Alphabetical sort",
                new RequestSorter(2, new GetAllRequests().doIt()), getRootMenu()));
        return rootMenu;
    }
}
