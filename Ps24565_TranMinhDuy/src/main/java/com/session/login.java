package com.session;



import java.io.IOException;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.model.User;

@WebServlet("/login")
public class login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public login() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session sessionHibernate = sessionFactory.openSession();
            org.hibernate.Transaction tx = sessionHibernate.beginTransaction();
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            RequestDispatcher dis = null;
            Query query = sessionHibernate.createQuery("FROM User WHERE username=:username AND password=:password");
            query.setParameter("username", username);
            query.setParameter("password", password);
            try {
            	// chuyển đối tượng truy vấn query sang user
                User user = (User) query.getSingleResult();
                dis = request.getRequestDispatcher("/views/Account.jsp");
            	request.getSession().setAttribute("user", user);
            } catch (NoResultException e) {
                request.setAttribute("status", "Failed");
                dis = request.getRequestDispatcher("/views/login.jsp");
            }
            dis.forward(request, response);
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
