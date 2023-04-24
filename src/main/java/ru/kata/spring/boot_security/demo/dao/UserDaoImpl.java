package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }


    @Override
    public void removeUserById(Long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {entityManager.remove(user);
        }
    }

    @Override
    public void updateUser(User updateUser) {
        entityManager.merge(updateUser);
    }



    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT user from User user", User.class).getResultList();
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByLogin(String login) {
//        return entityManager.find(User.class, login);
        return (User) entityManager.createQuery("select u from User u join fetch u.roles where u.login =:login").setParameter("login", login).getResultList().get(0);
    }

}


