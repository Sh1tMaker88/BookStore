package com.dao;

import com.api.dao.IBookDao;
import com.annotations.Singleton;
import com.model.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class BookDao extends AbstractDao<Book> implements IBookDao {

    private static final Logger LOGGER = LogManager.getLogger(BookDao.class.getName());

    public BookDao() {
    }

    @Override
    protected Book updateEntityFields(Book entityToUpdate, Book entity) {
        entityToUpdate.setName(entity.getName());
        entityToUpdate.setAuthor(entity.getAuthor());
        entityToUpdate.setYearOfPublish(entity.getYearOfPublish());
        entityToUpdate.setPageNumber(entity.getPageNumber());
        entityToUpdate.setIsbn(entity.getIsbn());
        entityToUpdate.setPrice(entity.getPrice());
        entityToUpdate.setBookStatus(entity.getBookStatus());
        entityToUpdate.setDescription(entity.getDescription());
        entityToUpdate.setArrivalDate(entity.getArrivalDate());
        entityToUpdate.setOrderCount(entity.getOrderCount());
        return entityToUpdate;
    }

    @Override
    protected String getClassName() {
        return "Book";
    }

    @Override
    protected Class<Book> getClazz() {
        return Book.class;
    }

    @Override
    public String getDescription(Long id) {
        return getAll().stream()
                .findFirst()
                .filter(e -> e.getId().equals(id))
                .get()
                .getDescription();
    }

    @Override
    public Set<Book> getBookThatHaveNoOrdersForPeriodOfTime(int monthToSetBookAsUnsold) {
        return getAll().stream()
                .filter(book -> book.getArrivalDate()
                        .plusMonths(monthToSetBookAsUnsold)
                        .isBefore(LocalDate.now()))
                .filter(el -> el.getOrders().isEmpty())
                .collect(Collectors.toSet());
    }

//    @Override
//    public void updateOrderCount(Book book) {
//        Connection connection = connector.getConnection();
//        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER_COUNT_QUERY)) {
//            statement.setLong(1, book.getId());
//            statement.executeUpdate();
//            LOGGER.info("Update order count for book with id=" + book.getId());
//        } catch (SQLException e) {
//            LOGGER.warn(e.getMessage());
//            throw new DaoException(e);
//        }
//    }

}
