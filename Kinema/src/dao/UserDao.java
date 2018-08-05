package dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import entitete.User;
import hibernate.HibernateUtil;

public class UserDao {

	private static UserDao instance;

	public static UserDao getInstance() {
		if (instance == null) {
			instance = new UserDao();
		}
		return instance;
	}

	private SessionFactory sessionFactory;
	private Transaction trns = null;
	private Session session;

	private UserDao() {
		sessionFactory = HibernateUtil.getSessionFactory();
	}

	public boolean add(User user) {

		session = sessionFactory.openSession();
		try {
			trns = session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {

			trns.rollback();
			return false;

		} finally {

			session.close();
		}
	}

	public boolean edit(User user) {
		session = sessionFactory.openSession();
		try {
			trns = session.beginTransaction();
			session.update(user);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {

			trns.rollback();
			return false;

		} finally {

			session.close();
		}
	}

	public boolean delete(int userId) {
		session = sessionFactory.openSession();
		try {
			trns = session.beginTransaction();
			User user = (User) session.load(User.class, new Integer(userId));
			session.delete(user);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {

			trns.rollback();
			return false;

		} finally {

			session.close();
		}
	}

	public User getUserFromId(int userId) {
		session = sessionFactory.openSession();
		User user = null;
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery("FROM User WHERE id=:id");
			query.setParameter("id", userId);
			user = (User) query.uniqueResult();
			session.getTransaction().commit();
		} catch (RuntimeException e) {

			trns.rollback();

		} finally {

			session.close();
		}
		return user;
	}

	public User getLoggedUser(String email, String pass) {

		session = sessionFactory.openSession();
		User user = null;

		try {
			trns = session.beginTransaction();
			Query query = session.createQuery("FROM User WHERE email=:email And password=:pass");
			query.setParameter("email", email);
			query.setParameter("pass", pass);
			user = (User) query.uniqueResult();
			session.getTransaction().commit();

		} catch (Exception e) {
			trns.rollback();
		} finally {
			session.close();
		}

		return user;
	}

	public User getUserByEmail(String email) {

		session = sessionFactory.openSession();
		User user = null;
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery("FROM User WHERE email=:email");
			query.setParameter("email", email);
			user = (User) query.uniqueResult();
			session.getTransaction().commit();
		
		} catch (Exception e) {
			trns.rollback();
		} finally {
			session.close();
		}
	
		return user;
	}

}
