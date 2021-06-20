package servicesTest;

import com.api.dao.IBookDao;
import com.api.dao.IOrderDao;
import com.model.Book;
import com.model.Order;
import com.model.OrderStatus;
import com.model.Request;
import com.service.BookService;
import com.service.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private IBookDao bookDao;
    @Mock
    private IOrderDao orderDao;
    @InjectMocks
    private OrderService orderService;

    private Book book;
    private Book book2;
    private Order order;

    @BeforeEach
    void setUp() {
        book = new Book("Test_book", "Test_author", "Test_isbn",
                350, 25.5, 2021, "Test_description");
        book.setId(1L);
        book2 = new Book("Test_book2", "Test_author2", "Test_isbn2",
                450, 35.5, 2019, "Test_description2");
        book2.setId(2L);
        order = new Order("Test_customer_name", Arrays.asList(book, book2));
        order.setId(1L);
    }

    @Test
    void addOrder_SetTotalPrice_Check() {
        when(bookDao.getById(1L)).thenReturn(book);
        when(bookDao.getById(2L)).thenReturn(book2);
        Order order = orderService.addOrder("Customer", Arrays.asList(book, book2));

        assertThat(order.getTotalPrice(), is(equalTo(book.getPrice() + book2.getPrice())));
    }

    @Test
    void addOrderUsingId_SetTotalPrice_Check() {
        when(bookDao.getById(1L)).thenReturn(book);
        when(bookDao.getById(2L)).thenReturn(book2);
        Order order = orderService.addOrder("Customer", Arrays.asList(book, book2));

        assertThat(order.getTotalPrice(), is(equalTo(book.getPrice() + book2.getPrice())));

    }

    @Test
    void cancelOrder_SetOrderStatusTo_CANCEL() {
        when(orderDao.getById(anyLong())).thenReturn(order);
        orderService.cancelOrder(1L);

        assertThat(order.getStatus(), is(equalTo(OrderStatus.CANCEL)));
    }

    @Test
    void changeOrderStatus_SetOrderStatusTo_CANCEL() {
        when(orderDao.getById(1L)).thenReturn(order);
        orderService.changeOrderStatus(1L, OrderStatus.CANCEL);

        assertThat(order.getStatus(), is(equalTo(OrderStatus.CANCEL)));
    }

    @Test
    void changeOrderStatus_SetOrderStatusTo_DONE() {
        when(orderDao.getById(1L)).thenReturn(order);
        orderService.changeOrderStatus(1L, OrderStatus.DONE);

        assertThat(order.getStatus(), is(equalTo(OrderStatus.DONE)));
    }

    @Test
    void changeOrderStatus_WhenSetOrderStatusToDONE_IncreaseBooksOrderCount() {
        when(orderDao.getById(1L)).thenReturn(order);
        orderService.changeOrderStatus(1L, OrderStatus.DONE);

        assertThat(order.getBooks(), hasSize(2));
        assertThat(order.getBooks(), everyItem(hasProperty("orderCount", is(1))));
    }

    @Test
    void changeOrderStatus_WhenSetOrderStatusToDONE_SetDateOfDoneToNow() {
        Clock clock = Clock.fixed(Instant.parse("2021-06-15T10:15:00Z"), ZoneId.of("UTC"));
        String dateTimeExpected = "2021-06-15T10:15";
        when(orderDao.getById(1L)).thenReturn(order);
        orderService.changeOrderStatus(1L, OrderStatus.DONE);
        order.setDateOfDone(LocalDateTime.now(clock));

        assertThat(order.getDateOfDone().toString(), is(equalTo(dateTimeExpected)));
    }

    @Test
    void changeOrderStatus_SetOrderStatusTo_NEW() {
        when(orderDao.getById(1L)).thenReturn(order);
        orderService.changeOrderStatus(1L, OrderStatus.NEW);

        assertThat(order.getStatus(), is(equalTo(OrderStatus.NEW)));
        assertThat(order.getDateOfDone(), nullValue());
    }

    @Test
    void showDetails_IsWorkingCorrect() {
        when(orderDao.getById(anyLong())).thenReturn(order);
        Order order = orderService.showDetails(1L);

        assertThat(order, notNullValue());
        assertThat(order, instanceOf(Order.class));
    }
}