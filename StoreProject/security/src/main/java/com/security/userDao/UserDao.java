package com.security.userDao;

import com.security.userModel.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao implements IUserDao{

    private final PasswordEncoder passwordEncoder;
    private final SessionFactory sessionFactory;

    @Autowired
    public UserDao(PasswordEncoder passwordEncoder, SessionFactory sessionFactory) {
        this.passwordEncoder = passwordEncoder;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User findByUserName(String username) {
//        Session session = sessionFactory.getCurrentSession();
//        String qu = String.format("SELECT us.* FROM com.security.userModel.User AS us WHERE us.username=%s", username);
//        Query<User> query = session.createQuery("FROM com.security.userModel.User AS us WHERE us.username=?1");
//        Query<User> query = session.createQuery(qu, User.class);
//        query.setParameter(1, username);
//        List<User> list = query.list();
//        User user = query.getSingleResult();

        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(com.security.userModel.User.class);
        ParameterExpression<String> nameParam = builder.parameter(String.class);
        criteriaQuery.select(root)
                .where(builder.equal(root.get("username"), nameParam));

        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery(criteriaQuery);
        query.setParameter(nameParam, username);
        User user = query.getSingleResult();
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("FROM User AS u JOIN FETCH u.roles", User.class);
        return (List<User>) query.list();
    }

    @Override
    public void createUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
    }

    @Override
    public User findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM User WHERE id=?1");
        query.setParameter(1, id);
        return (User) query.getSingleResult();
    }

    @Override
    public boolean deleteUserById(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        User user = findById(userId);
        if (user != null) {
            session.delete(user);
            return true;
        }
        return false;
    }
}
