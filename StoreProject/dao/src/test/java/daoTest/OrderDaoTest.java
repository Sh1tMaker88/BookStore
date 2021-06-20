package daoTest;

import com.dao.BookDao;
import com.dao.OrderDao;
import com.model.Book;
import com.model.Order;
import com.model.OrderStatus;
import daoTest.testConfig.TestDaoConfiguration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(TestDaoConfiguration.class)
@Tag("OrderDAO")
@PropertySource("classpath:server.properties")
@Transactional
public class OrderDaoTest {

    @Value("${MONTH_TO_SET_BOOK_AS_UNSOLD}")
    private int monthToSetBookAsUnsold;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private BookDao bookDao;

    private Order setOrderToTestCRUD() {
        Book book1 = bookDao.getById(2L);
        Book book2 = bookDao.getById(10L);
        Order order = new Order("Name", Arrays.asList(book1, book2));
        order.setDateOfDone(LocalDateTime.now().minusDays(1));
        order.setStatus(OrderStatus.DONE);
        order.setTotalPrice(756);
        return order;
    }

    @Test
    void testContext() {
        assertThat(orderDao, notNullValue());
        assertThat(bookDao, notNullValue());
    }

    @Test
    void getBooksThatAreNotBought_GetSet_SetIsNotNull() {
        Set<Book> books = orderDao.getBooksThatAreNotBought(monthToSetBookAsUnsold);

        assertThat(books, notNullValue());
    }

    @Test
    void getBooksThatAreNotBought_GetSet_EveryItemWithCorrectDate() {
        Set<Book> books = orderDao.getBooksThatAreNotBought(monthToSetBookAsUnsold);
        Set<Book> checkedDateBooks = new HashSet<>();

        for (Book book : books) {
            Set<Book> orderList = book.getOrders().stream()
                    .filter(el -> el.getOrderDate()
                            .plusMonths(monthToSetBookAsUnsold)
                            .isBefore(LocalDateTime.now()))
                    .flatMap(el -> el.getBooks().stream())
                    .collect(Collectors.toSet());
            checkedDateBooks.addAll(orderList);
        }

        assertThat(books, is(equalTo(checkedDateBooks)));
    }

    @Test
    void getPriceByPeriodOfTime_ReturnDouble() {
        LocalDate from = LocalDate.from(LocalDate.parse("2020-05-05"));
        assertThat(orderDao.getPriceByPeriodOfTime(from, LocalDate.now()), instanceOf(Double.class));
    }

    @Test
    @Rollback
    void getPriceByPeriodOfTime_CountCorrectValue() {
        Book book1 = new Book("Test_book", "Test_author", "Test_isbn",
                350, 25.5, 2021, "Test_description");
        Book book2 = new Book("Test_book2", "Test_author2", "Test_isbn2",
                350, 1375.2, 2021, "Test_description2");
        Order order = new Order("Name", Arrays.asList(book1, book2));
        order.setDateOfDone(LocalDateTime.now().minusDays(1));
        order.setStatus(OrderStatus.DONE);
        order.setTotalPrice(1400.7);
        bookDao.saveOrUpdate(book1);
        bookDao.saveOrUpdate(book2);
        orderDao.saveOrUpdate(order);
        Double price = orderDao.getPriceByPeriodOfTime(LocalDate.now().minusDays(2), LocalDate.now());
        System.out.println(orderDao.getAll());
        System.out.println(bookDao.getAll());
        assertThat(price, is(equalTo(1400.7)));
    }

    @Test
    void ordersDoneByPeriodOfTime_GetList_ReturnNoNull() {
        List<Order> list = orderDao.getOrdersDoneByPeriod(LocalDate.now(), LocalDate.now());
        assertThat(list, notNullValue());
    }

    @Test
    void ordersDoneByPeriodOfTime_GetList_AllItemsHaveStatusDONE() {
        LocalDate from = LocalDate.from(LocalDate.parse("2019-05-05"));
        List<Order> list = orderDao.getOrdersDoneByPeriod(from, LocalDate.now());
        assertThat(list, everyItem(hasProperty("status", is(OrderStatus.DONE))));
    }

    @Test
    void ordersDoneByPeriodOfTime_GetList_AllItemsHaveProperDateOfDone() {
        LocalDate from = LocalDate.from(LocalDate.parse("2020-05-05"));
        List<Order> list = orderDao.getOrdersDoneByPeriod(from, LocalDate.now());
        LocalDateTime fromLC = LocalDateTime.from(LocalDateTime.parse("2020-05-05T12:00:00"));
        assertThat(list, everyItem(hasProperty("dateOfDone", is(greaterThanOrEqualTo(fromLC)))));
    }

    @Test
    @Rollback
    void getOrderById_CheckNotNull() {
        Order order = setOrderToTestCRUD();
        orderDao.saveOrUpdate(order);
        Order orderFromDB = orderDao.getById(order.getId());

        assertThat(orderFromDB, notNullValue());
    }

    @Test
    @Rollback
    void getOrderById_CheckCorrectValues() {
        Order order = setOrderToTestCRUD();
        orderDao.saveOrUpdate(order);
        Order orderFromDB = orderDao.getById(order.getId());

        assertThat(orderFromDB, is(equalTo(order)));
    }

    @Test
    void getOrderById_ThrownException() {
        assertThrows(NoResultException.class, () -> orderDao.getById(Long.MAX_VALUE));
    }

    @Test
    @Rollback
    void deleteOrderEntity_Check() {
        Order order = setOrderToTestCRUD();
        orderDao.saveOrUpdate(order);
        orderDao.delete(order);
    }

    @Test
    @Rollback
    void deleteOrderEntity_ThrowsNoResultException() {
        Order order = setOrderToTestCRUD();
        assertThrows(NoResultException.class, () -> orderDao.delete(order),
                "No entity in DB");
    }

    @Test
    @Rollback
    void deleteOrderById_Check() {
        Order order = setOrderToTestCRUD();
        orderDao.saveOrUpdate(order);
        orderDao.delete(order.getId());
    }

    @Test
    @Rollback
    void deleteOrderById_ThrowsNoResultException() {
        assertThrows(NoResultException.class, () -> orderDao.delete(Long.MAX_VALUE),
                "No such entity in DB");
    }

    @Test
    @Rollback
    void saveOrUpdateOrder_Check() {
        Order order = setOrderToTestCRUD();
        orderDao.saveOrUpdate(order);
        Order orderFromDB = orderDao.getById(order.getId());

        assertThat(orderFromDB, is(equalTo(order)));
    }

    @Test
    void getAllOrders_GetList_NotNull() {
        List<Order> list = orderDao.getAll();

        assertThat(list, is(notNullValue()));
    }

    @Test
    @Rollback
    void updateOrder_Check() {
        Order order = orderDao.getById(1L);
        Order mockOrder = new Order("Changed_customer_name", order.getBooks());
        mockOrder.setId(1L);
        mockOrder.setStatus(OrderStatus.CANCEL);
        mockOrder.setTotalPrice(10000);
        mockOrder.setDateOfDone(LocalDateTime.now());
        mockOrder.setOrderDate(LocalDateTime.now().minusDays(3));

        order.setStatus(mockOrder.getStatus());
        order.setTotalPrice(mockOrder.getTotalPrice());
        order.setDateOfDone(mockOrder.getDateOfDone());
        order.setOrderDate(mockOrder.getOrderDate());
        order.setCustomerName(mockOrder.getCustomerName());
        orderDao.update(order);

        assertThat(order, is(equalTo(mockOrder)));
    }
}
