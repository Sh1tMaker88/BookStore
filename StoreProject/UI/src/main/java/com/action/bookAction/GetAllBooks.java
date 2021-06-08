package com.action.bookAction;

import com.exception.ActionException;
import com.exception.DaoException;
import com.model.Book;
import com.facade.Facade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllBooks {

    private static final Logger LOGGER = LogManager.getLogger(GetAllBooks.class.getName());
    private Facade facade;

    @Autowired
    public void setFacade(Facade facade) {
        this.facade = facade;
    }

    public List<Book> doIt() {
        try {
            return facade.getBookService().getAllBooks();
        } catch (DaoException e){
            LOGGER.warn("Method doIt from class GetAllBooks failed");
            throw new ActionException("Method doIt failed", e);
        }
    }
}
