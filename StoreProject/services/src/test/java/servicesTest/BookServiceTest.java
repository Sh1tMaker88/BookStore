package servicesTest;

import com.api.dao.IBookDao;
import com.api.dao.IOrderDao;
import com.api.dao.IRequestDao;
import com.model.Book;
import com.model.BookStatus;
import com.model.Request;
import com.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private IBookDao bookDao;
    @Mock
    private IOrderDao orderDao;
    @InjectMocks
    private BookService bookService;

    private Book book;
    private Request request;

    @BeforeEach
    void setUp() {
        book = new Book("Test_book", "Test_author", "Test_isbn",
                350, 25.5, 2021, "Test_description");
        book.setId(1L);
        request = new Request(book);
        request.setId(1L);
    }

    @Test
    void addBookToStock_setBookStatusTo_IN_STOCK() {
        book.setBookStatus(BookStatus.OUT_OF_STOCK);
        when(bookDao.getById(anyLong())).thenReturn(book);
        when(bookDao.update(book)).thenReturn(book);
        bookService.addBookToStock(1L);

        assertThat(book.getBookStatus(), is(equalTo(BookStatus.IN_STOCK)));
    }

    @Test
    void discardBook_SetBookStatusTo_OUT_OF_STOCK() {
        when(bookDao.getById(anyLong())).thenReturn(book);
        when(bookDao.update(book)).thenReturn(book);
        bookService.discardBook(1L);

        assertThat(book.getBookStatus(), is(equalTo(BookStatus.OUT_OF_STOCK)));
    }

    @Test
    void showDescription_GetBookDescription() {
        when(bookDao.getDescription(anyLong())).thenReturn(book.getDescription());
        String descr = bookService.showDescription(1L);

        assertThat(descr, is(equalTo(book.getDescription())));
    }

    @Test
    void unsoldBooks_GetListUnsoldBooks_NotEmptyListAndHasSize3() {
        Book book2 = new Book("Test_book2", "Test_author2", "Test_isbn2",
                450, 35.5, 2019, "Test_description2");
        Book book3 = new Book("Test_book3", "Test_author3", "Test_isbn3",
                550, 45.5, 2020, "Test_description3");
        Set<Book> list1 = new HashSet<>();
        list1.add(book);
        Set<Book> list2 = new HashSet<>();
        list2.add(book2);
        list2.add(book3);
        when(orderDao.getBooksThatAreNotBought(anyInt())).thenReturn(list1);
        when(bookDao.getBookThatHaveNoOrdersForPeriodOfTime(anyInt())).thenReturn(list2);
        List<Book> books = bookService.unsoldBooks();

        assertThat(books, is(not(empty())));
        assertThat(books, hasSize(3));
    }
}
