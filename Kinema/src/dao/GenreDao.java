package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import hibernate.HibernateUtil;

public class GenreDao {
	private static GenreDao instance;

	public static GenreDao getInstance() {
		if (instance == null) {
			instance = new GenreDao();
		}
		return instance;
	}
	private static final String GET_ALL_GENRE = "Select genre From genre";
	private SessionFactory sessionFactory;
	private Transaction trns = null;
	private Session session;
	
	private GenreDao() {
		sessionFactory = HibernateUtil.getSessionFactory();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getAllGenres() {
		session = sessionFactory.openSession();
		List<String> allGenres = null;
		try {
			trns = session.beginTransaction();
			Query query = session.createSQLQuery(GET_ALL_GENRE);
			allGenres = (List<String>) query.list();
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
		} finally {
			session.close();
		}
		return allGenres;
	}
}
