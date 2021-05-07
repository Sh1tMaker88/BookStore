package com.dao;

import com.annotations.InjectByType;
import com.annotations.Singleton;
import com.api.dao.IOrderDao;
import com.dao.util.Connector;
import com.model.Book;
import com.model.Order;
import com.propertyInjector.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class OrderDao extends AbstractDao<Order> implements IOrderDao {

    private static final Logger LOGGER = LogManager.getLogger(OrderDao.class.getName());

    public OrderDao() {

    }

    @Override
    protected Order updateEntityFields(Order entityToUpdate, Order entity) {
        entityToUpdate.setCustomerName(entity.getCustomerName());
        entityToUpdate.setTotalPrice(entity.getTotalPrice());
        entityToUpdate.setStatus(entity.getStatus());
        entityToUpdate.setOrderDate(entity.getOrderDate());
        entityToUpdate.setDateOfDone(entity.getDateOfDone());
        entityToUpdate.setBooks(entity.getBooks());
        return entityToUpdate;
    }

    @Override
    protected String getClassName() {
        return "Order";
    }

    @Override
    protected Class<Order> getClazz() {
        return Order.class;
    }

    @Override
    public Set<Book> getBooksThatAreNotBought(int monthToSetBookAsUnsold) {
        return getAll().stream()
                .filter(order -> order.getOrderDate()
                        .plusMonths(monthToSetBookAsUnsold)
                        .isBefore(LocalDateTime.now()))
                .flatMap(el -> el.getBooks().stream())
                .collect(Collectors.toSet());
    }
}
