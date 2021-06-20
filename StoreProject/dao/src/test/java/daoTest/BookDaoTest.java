package daoTest;

import com.dao.BookDao;
import com.model.Book;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import daoTest.testConfig.TestDaoConfiguration;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDaoConfiguration.class)
@Tag("BookDAO")
@Transactional
@PropertySource("classpath:server.properties")
class BookDaoTest {

    @Value("${MONTH_TO_SET_BOOK_AS_UNSOLD}")
    private int monthToSetBookAsUnsold;

    @Autowired
    private BookDao bookDao;

    private Book setBookForTest() {
        return new Book("Test_book", "Test_author", "Test_isbn",
                350, 25.5, 2021, "Test_description");
    }

    @Test
    void testContext() {
        assertThat(bookDao, notNullValue());
    }

    @Test
    void getBookThatHaveNoOrdersForPeriodOfTime_GetSet_SetIsNotNull() {
        Set<Book> set = bookDao.getBookThatHaveNoOrdersForPeriodOfTime(monthToSetBookAsUnsold);

        assertThat(set, notNullValue());
    }

    @Test
    void getBookThatHaveNoOrdersForPeriodOfTime_GetSet_AllItemsHaveNoOrders() {
        Set<Book> set = bookDao.getBookThatHaveNoOrdersForPeriodOfTime(monthToSetBookAsUnsold);

        assertThat(set, everyItem(hasProperty("orders", empty())));
    }

    @Test
    void getBookThatHaveNoOrdersForPeriodOfTime_GetSet_AllItemsHaveCorrectDate() {
        Set<Book> set = bookDao.getBookThatHaveNoOrdersForPeriodOfTime(monthToSetBookAsUnsold);

        assertThat(set, everyItem(hasProperty("arrivalDate",
                is(lessThan(LocalDate.now().minusMonths(monthToSetBookAsUnsold))))));
    }

    @Test
    @Rollback
    void getBookById_CheckNotNull() {
        Book book = setBookForTest();
        bookDao.saveOrUpdate(book);

        assertThat(bookDao.getById(book.getId()), notNullValue());
    }

    @Test
    @Rollback
    void getBookById_CheckCorrectValues() {
        Book book = setBookForTest();
        bookDao.saveOrUpdate(book);
        Book bookFromDB = bookDao.getById(book.getId());

        assertThat(book, is(equalTo(bookFromDB)));
    }

    @Test
    void getBookById_ThrownException() {
        assertThrows(NoResultException.class, () -> bookDao.getById(Long.MAX_VALUE));
    }

    @Test
    @Rollback
    void deleteBookEntity_Check() {
        Book book = setBookForTest();
        bookDao.saveOrUpdate(book);
        bookDao.delete(book);
    }

    @Test
    @Rollback
    void deleteBookEntity_ThrowsNoResultException() {
        Book book = setBookForTest();
        assertThrows(NoResultException.class, () -> bookDao.delete(book),
                "No entity in DB");
    }

    @Test
    @Rollback
    void deleteOrderById_Check() {
        Book book = setBookForTest();
        bookDao.saveOrUpdate(book);
        bookDao.delete(book.getId());
    }

    @Test
    @Rollback
    void deleteBookById_ThrowsNoResultException() {
        assertThrows(NoResultException.class, () -> bookDao.delete(Long.MAX_VALUE),
                "No such entity in DB");
    }

    @Test
    @Rollback
    void saveOrUpdateBook_Check() {
        Book book = setBookForTest();
        bookDao.saveOrUpdate(book);
        Book bookFromDB = bookDao.getById(book.getId());

        assertThat(bookFromDB, is(equalTo(book)));
    }

    @Test
    void getAllBooks_GetList_NotNull() {
        List<Book> list = bookDao.getAll();

        assertThat(list, is(notNullValue()));
    }

    @Test
    @Rollback
    void updateBook_Check() {
        Book book = bookDao.getById(1L);
        Book mockBook = setBookForTest();
        mockBook.setId(1L);
        book.setOrderCount(mockBook.getOrderCount());
        book.setBookStatus(mockBook.getBookStatus());
        book.setPrice(mockBook.getPrice());
        book.setName(mockBook.getName());
        book.setAuthor(mockBook.getAuthor());
        book.setPageNumber(mockBook.getPageNumber());
        book.setYearOfPublish(mockBook.getYearOfPublish());
        book.setDescription(mockBook.getDescription());
        book.setArrivalDate(mockBook.getArrivalDate());
        book.setIsbn(mockBook.getIsbn());
        bookDao.update(book);
        Book updatedBook = bookDao.getById(1L);

        assertThat(updatedBook, is(equalTo(mockBook)));
    }
}