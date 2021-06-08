package com.security.userDao;

import com.security.userModel.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {

    private final PasswordEncoder passwordEncoder;
    private final SessionFactory sessionFactory;

    @Autowired
    public UserDao(PasswordEncoder passwordEncoder, SessionFactory sessionFactory) {
        this.passwordEncoder = passwordEncoder;
        this.sessionFactory = sessionFactory;
    }

    public User findByUserName(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT FROM user WHERE username=(:username)");
        query.setParameter("username", username);
        User user = (User) query.getSingleResult();
        return user;
    }

    public List<User> findAllUsers() {
        return null;
    }

    public void createUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
    }

    public User findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT FROM user WHERE id=(:userId)");
        query.setParameter("userId", id);
        return (User) query.getSingleResult();
    }
}
