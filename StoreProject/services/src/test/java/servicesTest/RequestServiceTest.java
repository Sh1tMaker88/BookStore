package servicesTest;

import com.api.dao.IBookDao;
import com.api.dao.IRequestDao;
import com.model.Book;
import com.model.BookStatus;
import com.model.Request;
import com.model.RequestStatus;
import com.service.RequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {

    @Mock
    private IRequestDao requestDao;
    @Mock
    private IBookDao bookDao;
    @InjectMocks
    private RequestService requestService;
    private Request request;
    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book("Test_book", "Test_author", "Test_isbn",
                350, 25.5, 2021, "Test_description");
        book.setId(1L);
        request = new Request(book);
        request.setId(1L);
        request.setRequestStatus(RequestStatus.OPEN);
        request.setRequestCount(10);
    }

    @Test
    void closeRequest_RequestAndBook_NotNull() {
//        doReturn(request).when(requestDao.getById(anyLong()));
        when(requestDao.getById(anyLong())).thenReturn(request);
        requestService.closeRequest(1L);

        assertThat(request, is(notNullValue()));
        assertThat(book, is(notNullValue()));
    }

    @Test
    void closeRequest_SetRequestStatusTo_CLOSED() {
//        doReturn(request).when(requestDao.getById(anyLong()));
        when(requestDao.getById(anyLong())).thenReturn(request);
        requestService.closeRequest(1L);

        assertThat(request.getRequestStatus(), is(equalTo(RequestStatus.CLOSED)));
    }

    @Test
    void closeRequest_SetBookStatusTo_IN_STOCK() {
        request.getBook().setBookStatus(BookStatus.OUT_OF_STOCK);
        when(requestDao.getById(anyLong())).thenReturn(request);
        requestService.closeRequest(1L);

        assertThat(request.getBook().getBookStatus(), is(equalTo(BookStatus.IN_STOCK)));
    }

    @Test
    void addRequest_SetRequestCount_PlusOne() {
        when(requestDao.getRequestByBookId(anyLong())).thenReturn(request);
        when(requestDao.checkIfRequestExistForBookID(anyLong())).thenReturn(true);
        requestService.addRequest(1L);

        assertThat(request.getRequestCount(), is(equalTo(11)));
    }
}
