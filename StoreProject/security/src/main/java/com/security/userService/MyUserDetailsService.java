package com.security.userService;

import com.security.userDao.IUserDao;
import com.security.userModel.Role;
import com.security.userModel.User;
import com.security.userModel.UserRole;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.security.userDao.UserDao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.*;

@Component
@Transactional
public class MyUserDetailsService implements UserDetailsService {

//    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final SessionFactory sessionFactory;

    @Autowired
    public MyUserDetailsService(
//            UserDao userDao,
            SessionFactory sessionFactory,
            PasswordEncoder passwordEncoder) {
//        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userDao.findByUserName(username);
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
//                .orElseThrow(() -> new UsernameNotFoundException("No such user was found"));
        if (user == null) {
            throw new UsernameNotFoundException("Unknown user:" + username);
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), grantedAuthorities);
    }

}
