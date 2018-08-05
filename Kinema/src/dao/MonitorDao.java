package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import entitete.Monitor;
import hibernate.HibernateUtil;

public class MonitorDao {

	private static MonitorDao instance;

	public static MonitorDao getInstance() {
		if (instance == null) {
			instance = new MonitorDao();
		}
		return instance;
	}

	private static final String GET_MONITOR_BY_ID = "FROM Monitor WHERE id=:id";
	private static final String GET_MONITOR_BY_NAME = "FROM Monitor WHERE name=:name";
	private static final String GET_ALL_MONITORS = "from Monitor";
	private static final String GET_HALL_MONITORS = "from Monitor Where idHall=:idHall";
	private static final String COUNT_MONITORS = "select count(*) from Monitor Where idHall=:idHall";
	private static final String COUNT_CHAIRS = "select count(*) from Chair Where idMonitor=:idMonitor";

	private SessionFactory sessionFactory;
	private Transaction trns = null;
	private Session session;

	private MonitorDao() {
		sessionFactory = HibernateUtil.getSessionFactory();
	}

	public boolean addMonitor(Monitor monitor) {
		session = sessionFactory.openSession();

		try {
			trns = session.beginTransaction();
			session.save(monitor);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {

			trns.rollback();
			return false;

		} finally {

			session.close();
		}

	}

	public boolean editMonitor(Monitor monitor) {
		session = sessionFactory.openSession();
		try {
			trns = session.beginTransaction();
			session.update(monitor);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			trns.rollback();
			return false;

		} finally {

			session.close();
		}

	}

	public boolean deleteMonitor(int idMonitor) {
		session = sessionFactory.openSession();

		try {
			trns = session.beginTransaction();
			Monitor monitor = (Monitor) session.load(Monitor.class, new Integer(idMonitor));
			session.delete(monitor);
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
	public List<Monitor> getAllMonitors() {
		session = sessionFactory.openSession();
		List<Monitor> allMonitors = null;
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(GET_ALL_MONITORS);
			allMonitors = (List<Monitor>) query.list();
			session.getTransaction().commit();
		} catch (RuntimeException e) {

			trns.rollback();

		} finally {

			session.close();
		}
		return allMonitors;
	}

	@SuppressWarnings("unchecked")
	public List<Monitor> getHallMonitors(int idHall) {
		session = sessionFactory.openSession();
		List<Monitor> hallMonitors = null;
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(GET_HALL_MONITORS);
			query.setParameter("idHall", idHall);
			hallMonitors = (List<Monitor>) query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			trns.rollback();

		} finally {

			session.close();
		}
		return hallMonitors;
	}

	public Monitor getMonitorById(int idMonitor) {
		session = sessionFactory.openSession();
		Monitor monitor = null;
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(GET_MONITOR_BY_ID);
			query.setParameter("id", idMonitor);
			monitor = (Monitor) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {

			trns.rollback();

		} finally {

			session.close();
		}
		return monitor;
	}

	public Monitor getMonitorByName(String name) {
		session = sessionFactory.openSession();
		Monitor monitor = null;
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(GET_MONITOR_BY_NAME);
			query.setParameter("name", name);
			monitor = (Monitor) query.uniqueResult();
			session.getTransaction().commit();
		} catch (RuntimeException e) {

			trns.rollback();

		} finally {

			session.close();
		}
		return monitor;
	}

	public long countMonitors(int idHall) {
		session = sessionFactory.openSession();
		long nrMonitors = 0;
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(COUNT_MONITORS);
			query.setParameter("idHall", idHall);
			nrMonitors = (long) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			trns.rollback();

		} finally {

			session.close();
		}
		return nrMonitors;
	}

	public long countChairs(int idMonitor) {
		session = sessionFactory.openSession();
		long nrChairs = 0;
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(COUNT_CHAIRS);
			query.setParameter("idMonitor", idMonitor);
			nrChairs = (long) query.uniqueResult();
			session.getTransaction().commit();
		} catch (RuntimeException e) {

			trns.rollback();

		} finally {

			session.close();
		}
		return nrChairs;
	}

}
