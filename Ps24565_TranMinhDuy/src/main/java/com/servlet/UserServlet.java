package com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.Dao.UserDao;
import com.model.User;
import com.utils.HibernateUtil;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet({
    "/user/index",
    "/user/create",
    "/user/update",
    "/user/delete",
    "/user/reset",
    "/user/edit/*",
    "/user/delete/*",
    "/user/update/*",
    "/user/search",
})
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private UserDao userDao;
       
	 private SessionFactory sessionFactory;

	    /**
	     * @see HttpServlet#HttpServlet()
	     */
	    public UserServlet() {
	        super();
	        // TODO Auto-generated constructor stub
	    }
	    
	    @Override
	    public void init() throws ServletException {
	        super.init();
	        sessionFactory = new Configuration().configure().buildSessionFactory();
	        userDao = new UserDao();
	    }
	    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String url = request.getRequestURL().toString();
	        
	        request.setCharacterEncoding("utf-8");
	        
	        if (url.contains("delete")) {
	            delete(request, response);
	        } else if (url.contains("edit")) {
	            edit(request, response);
	        } else if (url.contains("search")) {
	            search(request, response);
	        } else {
	            findAll(request, response);
	        }
	        request.getRequestDispatcher("/views/User.jsp").forward(request, response);
	    }

		private void search(HttpServletRequest request, HttpServletResponse response) {
		    String keyword = request.getParameter("keyword");
		    List<User> users = null;
		    if(keyword != null && !keyword.trim().isEmpty()) {
		        // Tìm kiếm user theo keyword
		        users = userDao.findByUsernameOrId(keyword.trim());
		    }
		    // Đưa danh sách user vào attribute để hiển thị trên trang jsp
		    request.setAttribute("users", users);
		}


		private void edit(HttpServletRequest request, HttpServletResponse response) {
			// TODO Auto-generated method stub
	    	 Session session = sessionFactory.getCurrentSession();
	         session.beginTransaction();
	         User user =  null;
	         try {
	             String id = request.getParameter("id");
	             user = session.get(User.class, id);
	             // lưu trữ
	             session.getTransaction().commit();
	             request.setAttribute("user", user);
	         } catch (Exception e) {
	        	 // hủy bỏ
	             session.getTransaction().rollback();
	             e.printStackTrace();
	             request.setAttribute("error", "Error: "+e.getMessage());
	         }
		}

		private void detele(HttpServletRequest request, HttpServletResponse response) {
			// TODO Auto-generated method stub
			  Session session = sessionFactory.getCurrentSession();
	          session.beginTransaction();
	          
	          try {
	              String id = request.getParameter("id");
	              User deleteCandidate = session.get(User.class, id);
	              session.delete(deleteCandidate);
	              session.getTransaction().commit();
	              request.setAttribute("status", "Delete success");
	          } catch (Exception e) {
	              session.getTransaction().rollback();
	              e.printStackTrace();
	              request.setAttribute("error", "Error: "+e.getMessage());
	          }
		}

		

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
        String url = request.getRequestURL().toString();
        request.setCharacterEncoding("utf-8");
        if(url.contains("create")) {
            create(request,response);
        }
        else if(url.contains("update")){
            update(request,response);
        }
        else if(url.contains("delete")) {
            delete(request,response);
        }
        else if(url.contains("reset")){
            request.setAttribute("user", new User());
        }
        findAll(request, response);
        request.getRequestDispatcher("/views/User.jsp").forward(request, response);
    }
    protected void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
		String use = request.getParameter("username");
		String pass = request.getParameter("password");
		String name = request.getParameter("fullname");
		String mail = request.getParameter("email");
		String ad = request.getParameter("isAdmin");

		User user = new User();
		user.setUsername(use);
		user.setPassword(pass);
		user.setFullname(name);
		user.setEmail(mail);
		user.setIsAdmin(Boolean.parseBoolean(ad));
		transaction = session.beginTransaction();
		session.persist(user);
		transaction.commit();

		request.setAttribute("status", "Insert succsesfully!");
		} catch (Exception e) {
			if (transaction != null) {
			transaction.rollback();
			}
			e.printStackTrace();
			request.setAttribute("error", "Error: " + e.getMessage());
			} finally {
			session.close();
			}
    }

    protected void reset(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("user", new User());
    }

    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            String userId = String.valueOf(Integer.parseInt(request.getParameter("id")));
            User user = session.get(User.class, userId);
	        BeanUtils.populate(user, request.getParameterMap());
            transaction.commit();

            request.setAttribute("status", "Update success");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
        }
    }


    protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	   Session session = sessionFactory.getCurrentSession();
           session.beginTransaction();
           
           try {
               String id = request.getParameter("id");
               User deleteCandidate = session.get(User.class, id);
               session.delete(deleteCandidate);
               session.getTransaction().commit();
               request.setAttribute("status", "Delete success");
           } catch (Exception e) {
               session.getTransaction().rollback();
               e.printStackTrace();
               request.setAttribute("error", "Error: "+e.getMessage());
           }
    }
    
    
    protected void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Session session = null;
    	try {
    	    session = HibernateUtil.getSessionFactory().openSession();
    	    Query<User> query = session.createQuery("from User", User.class);
    	    List<User> userList = query.getResultList();
    	    request.setAttribute("users", userList);
    	    session.close();
    	} catch (Exception e) {
    	    e.printStackTrace();
    	    request.setAttribute("error", "Error: " + e.getMessage());
    	}

    }
}
