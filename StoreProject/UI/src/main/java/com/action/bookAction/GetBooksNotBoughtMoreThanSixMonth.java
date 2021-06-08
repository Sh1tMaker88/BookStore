package com.action.bookAction;

import com.exception.ActionException;
import com.exception.ServiceException;
import com.facade.Facade;
import com.model.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetBooksNotBoughtMoreThanSixMonth {

    private Facade facade;
    private static final Logger LOGGER = LogManager.getLogger(GetBooksNotBoughtMoreThanSixMonth.class.getName());

    @Autowired
    public void setFacade(Facade facade) {
        this.facade = facade;
    }

    public List<Book> doIt() {
        try {
            return facade.getBookService().booksNotBoughtMoreThanSixMonth();
        } catch (ServiceException e) {
            LOGGER.warn("Method doIt from class GetBooksNotBoughtMoreThanSixMonth failed", e);
            throw new ActionException("Method doIt failed", e);
        }
    }
}
