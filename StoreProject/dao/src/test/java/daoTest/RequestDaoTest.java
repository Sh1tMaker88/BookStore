package daoTest;

import com.dao.BookDao;
import com.dao.RequestDao;
import com.exception.DaoException;
import com.model.Book;
import com.model.Request;
import com.model.RequestStatus;
import daoTest.testConfig.TestDaoConfiguration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDaoConfiguration.class)
@Tag("RequestDAO")
@Transactional
public class RequestDaoTest {

    @Autowired
    private RequestDao requestDao;

    @Autowired
    private BookDao bookDao;

    @Test
    void testContext() {
        assertThat(requestDao, notNullValue());
        assertThat(bookDao, notNullValue());
    }

    private Request setRequestForTest() {
        return new Request(bookDao.getById(1L));
    }

    private Book setBookForTest() {
        return new Book("Test_book", "Test_author", "Test_isbn",
                350, 25.5, 2021, "Test_description");
    }

    @Test
    @Rollback
    void checkIfRequestExist_ReturnTrue() {
        Book book = setBookForTest();
        bookDao.saveOrUpdate(book);
        Request request = new Request(bookDao.getById(book.getId()));
        requestDao.saveOrUpdate(request);
        assertThat(requestDao.checkIfRequestExistForBookID(book.getId()), is(true));
    }

    @Test
    @Rollback
    void checkIfRequestExist_ReturnFalse() {
        Book book = setBookForTest();
        bookDao.saveOrUpdate(book);
        assertThat(requestDao.checkIfRequestExistForBookID(book.getId()), is(false));
    }

    @Test
    @Rollback
    void getRequestByBookId_ReturnRequest() {
        Book book = setBookForTest();
        bookDao.saveOrUpdate(book);
        requestDao.saveOrUpdate(new Request(bookDao.getById(book.getId())));

        assertThat(requestDao.getRequestByBookId(book.getId()), is(instanceOf(Request.class)));
    }

    @Test
    @Rollback
    void getRequestByBookId_ThrowDaoException() {
        Book book = setBookForTest();
        bookDao.saveOrUpdate(book);

        assertThrows(DaoException.class, () -> requestDao.getRequestByBookId(book.getId()));
    }

    @Test
    @Rollback
    void getRequestById_CheckNotNull() {
        Book book = setBookForTest();
        bookDao.saveOrUpdate(book);
        Request request = new Request(bookDao.getById(book.getId()));
        requestDao.saveOrUpdate(request);

        assertThat(requestDao.getById(request.getId()), notNullValue());
    }

    @Test
    @Rollback
    void getRequestById_CheckCorrectValues() {
        Book book = setBookForTest();
        bookDao.saveOrUpdate(book);
        Request request = new Request(bookDao.getById(book.getId()));
        requestDao.saveOrUpdate(request);
        Request requestFromDB = requestDao.getById(request.getId());

        assertThat(requestFromDB, is(equalTo(request)));
    }

    @Test
    void getRequestById_ThrownException() {
        assertThrows(NoResultException.class, () -> requestDao.getById(Long.MAX_VALUE));
    }

    @Test
    @Rollback
    void deleteRequestEntity_Check() {
        Book book = setBookForTest();
        bookDao.saveOrUpdate(book);
        Request request = new Request(bookDao.getById(book.getId()));
        requestDao.saveOrUpdate(request);
        requestDao.delete(request);
    }

    @Test
    @Rollback
    void deleteRequestEntity_ThrowsNoResultException() {
        Book book = setBookForTest();
        bookDao.saveOrUpdate(book);
        Request request = new Request(bookDao.getById(book.getId()));
        assertThrows(NoResultException.class, () -> requestDao.delete(request),
                "No entity in DB");
    }

    @Test
    @Rollback
    void deleteRequestById_Check() {
        Book book = setBookForTest();
        bookDao.saveOrUpdate(book);
        Request request = new Request(bookDao.getById(book.getId()));
        requestDao.saveOrUpdate(request);
        requestDao.delete(request.getId());
    }

    @Test
    @Rollback
    void deleteRequestById_ThrowsNoResultException() {
        assertThrows(NoResultException.class, () -> requestDao.delete(Long.MAX_VALUE),
                "No such entity in DB");
    }

    @Test
    @Rollback
    void saveOrUpdateRequest_Check() {
        Book book = setBookForTest();
        bookDao.saveOrUpdate(book);
        Request request = new Request(bookDao.getById(book.getId()));
        requestDao.saveOrUpdate(request);
        Request requestFromDB = requestDao.getById(request.getId());

        assertThat(requestFromDB, is(equalTo(request)));
    }

    @Test
    void getAllRequests_GetList_NotNull() {
        List<Request> list = requestDao.getAll();

        assertThat(list, is(notNullValue()));
    }

    @Test
    @Rollback
    void updateBook_Check() {
        Book book = setBookForTest();
        bookDao.saveOrUpdate(book);
        Request mockRequest = new Request(bookDao.getById(book.getId()));
        mockRequest.setRequestCount(50);
        mockRequest.setId(1L);
        mockRequest.setRequestStatus(RequestStatus.OPEN);
        mockRequest.setRequestDate(LocalDateTime.now().minusDays(1));
        mockRequest.setBook(bookDao.getById(2L));

        Request request = requestDao.getById(1L);
        request.setBook(mockRequest.getBook());
        request.setRequestDate(mockRequest.getRequestDate());
        request.setRequestStatus(mockRequest.getRequestStatus());
        request.setRequestCount(mockRequest.getRequestCount());

        requestDao.update(request);
        Request updatedRequest = requestDao.getById(1L);

        assertThat(updatedRequest, is(equalTo(mockRequest)));
    }
}
