package com;

import com.model.Book;
import com.model.Order;
import com.model.Request;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class TestHibernate {
    public static void main(String[] args) {

        Book book = new Book("test", "author", "iasdf"
                , 333, 42.2, 2020, "description");

        List<Book> books = new ArrayList<>();
        books.add(book);
        Order order = new Order("Customer", books);

        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Session session = factory.openSession();
        session.beginTransaction();
        session.save(order);
        session.getTransaction().commit();
        session.close();
    }
}
