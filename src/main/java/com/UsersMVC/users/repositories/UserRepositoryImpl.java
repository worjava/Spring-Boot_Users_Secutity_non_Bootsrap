package com.UsersMVC.users.repositories;


import com.UsersMVC.users.models.User;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> index() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User show(int id) {
        return entityManager.find(User.class, id);
    }

    public void save(User person) {
        entityManager.persist(person);
    }

    @Override
    public void update(int id, User user) {
        User user1 = entityManager.find(User.class, id);
        if (user1 != null) {
            user1.setEmail(user.getEmail());
            user1.setAge(user.getAge());
            user1.setName(user.getName());
            user1.setPassword(user.getPassword());
            entityManager.merge(user);

        }
    }

    @Override
    public void delete(int id) {
        User person = entityManager.find(User.class, id);
        if (person != null) {
            entityManager.remove(person);
        }
    }

    @Override
    public User findByUsername(String name) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.name = :name",
                User.class);
        query.setParameter("name", name);
        List<User> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
