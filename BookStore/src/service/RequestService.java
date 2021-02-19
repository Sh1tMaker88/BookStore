package service;

import api.dao.IBookDao;
import api.dao.IRequestDao;
import api.service.IRequestService;
import models.Book;
import models.BookStatus;
import models.Request;
import util.IdGenerator;
import util.comparators.RequestAlphabeticalComparator;

import java.util.List;

public class RequestService implements IRequestService {

    private final IRequestDao requestDao;
    private final IBookDao bookDao;

    public RequestService(IRequestDao requestDao, IBookDao bookDao) {
        this.requestDao = requestDao;
        this.bookDao = bookDao;
    }

    @Override
    public Book closeRequest(int requestID) {
        Request request = requestDao.getById(requestID);
        Book book = bookDao.getAll().stream()
                .filter(e -> e.equals(request.getBook()))
                .findFirst()
                .get();
        Book realBook = bookDao.getById(book.getId());
        realBook.setBookStatus(BookStatus.IN_STOCK);
        bookDao.update(realBook);
        requestDao.delete(request);
        System.out.println("Request for book: " + book.getName() + " closed");
        return book;
    }

    @Override
    public void closeRequest(Book book) {
        if (requestDao.getAll().stream().anyMatch(e->e.getBook().equals(book))){
            Book realBook = bookDao.getById(book.getId());
            Request request = requestDao.getAll().stream()
                    .filter(e->e.equals(book))
                    .findFirst()
                    .get();
            Request realRequest = requestDao.getById(request.getId());
            realBook.setBookStatus(BookStatus.IN_STOCK);
            bookDao.update(realBook);
            requestDao.delete(realRequest);
        }
    }

    @Override
    public Request addRequest(Book book) {
        Request request = new Request(book);
        request.setId(IdGenerator.generateRequestId());
        requestDao.create(request);
        return request;
    }

    @Override
    public List<Request> sortRequestBy(RequestSort requestSort) {
        List<Request> list = requestDao.getAll();
        switch (requestSort){
            case ALPHABETICAL:
                list.sort(new RequestAlphabeticalComparator());
                break;
            case NUMBERS:
//                list.sort(new RequestNumberOfRequestsComparator());
                break;
        }
        return list;
    }
}
