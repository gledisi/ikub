package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import entitete.Hall;
import hibernate.HibernateUtil;

public class HallDao {

	private static HallDao instance;

	public static HallDao getInstance() {
		if (instance == null) {
			instance = new HallDao();
		}
		return instance;
	}

	private static final String GET_ALL_HALLS = "from Hall";
	private static final String GET_HALLS_BY_NAME = "from Hall Where name=:name";

	private SessionFactory sessionFactory;
	private Transaction trns = null;
	private Session session;

	private HallDao() {
		sessionFactory = HibernateUtil.getSessionFactory();
	}

	public boolean addHall(Hall hall) {
		session = sessionFactory.openSession();
		try {
			trns = session.beginTransaction();
			session.save(hall);
			session.getTransaction().commit();
		} catch (Exception e) {
			trns.rollback();
			return false;
		} finally {
			session.close();
		}

		return true;
	}

	public boolean editHall(Hall hall) {
		session = sessionFactory.openSession();
		try {
			trns = session.beginTransaction();
			session.update(hall);
			session.getTransaction().commit();

			return true;
		} catch (Exception e) {

			trns.rollback();
			return false;

		} finally {
			session.close();
		}

	}

	public boolean deleteHall(int idHall) {
		session = sessionFactory.openSession();

		try {
			trns = session.beginTransaction();
			Hall hall = (Hall) session.load(Hall.class, new Integer(idHall));
			session.delete(hall);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {

			trns.rollback();
			return false;

		} finally {

			session.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<Hall> getAllHalls() {
		session = sessionFactory.openSession();
		List<Hall> allHalls = null;
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(GET_ALL_HALLS);
			allHalls = (List<Hall>) query.list();
			session.getTransaction().commit();

		} catch (Exception e) {

			trns.rollback();

		} finally {

			session.close();
		}
		return allHalls;
	}

	public Hall getHallByName(String name) {
		session = sessionFactory.openSession();
		Hall hall = null;
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(GET_HALLS_BY_NAME);
			query.setParameter("name", name);
			hall = (Hall) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {

			if(trns==null) {
			trns.rollback();
			}

		} finally {

			session.close();
		}
		return hall;
	}

}
