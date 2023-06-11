package com.Dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.model.User;
import com.utils.HibernateUtil;

public class UserDao {
    private Session session = HibernateUtil.getSessionFactory().openSession();

    @Override
    protected void finalize() throws Throwable {
        session.close();
    }

    public User create(User entity) {
        try {
            session.getTransaction().begin();

            session.save(entity);

            session.getTransaction().commit();
            return entity;
        } catch (Exception e) {

            session.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }

    public User update(User entity) {
        try {
            session.getTransaction().begin();

            session.update(entity);

            session.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }

    public User remove(String id) {
        try {
            session.getTransaction().begin();
            User entity = this.findById(id);
            session.remove(entity);
            session.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }

    public User findById(String id) {
        return session.find(User.class, id);
    }

    public List<User> findByRole(boolean role) {
        String hql = "Select o from User o where o.isAdmin=:role";
        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("role", role);
        return query.getResultList();
    }

    public List<User> findByKeyWord(String keyword) {
        String hql = "Select o from User o where o.username like :keyword";
        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("keyword", "%" + keyword + "%");
        return query.getResultList();
    }

    public User findOne(String username, String password) {
    	String hql = "Select o from User o where o.username=:username and o.password=:password";
    	Query<User> query = session.createQuery(hql, User.class);
    	query.setParameter("username", username);
    	query.setParameter("password", password);
    	return query.uniqueResult();
    }
    public List<User> findByUsernameOrId(String keyword) {
        String hql = "SELECT o FROM User o WHERE o.username LIKE :keyword";
        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("keyword", "%" + keyword + "%");
        List<User> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            hql = "SELECT o FROM User o WHERE o.id = :id";
            query = session.createQuery(hql, User.class);
            query.setParameter("id", keyword);
            resultList = query.getResultList();
        }
        return resultList; 
    }
}
        
