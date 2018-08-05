package dao;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import entitete.Show;
import hibernate.HibernateUtil;

public class ShowDao {

	private static ShowDao instance;

	public static ShowDao getInstance() {
		if (instance == null) {
			instance = new ShowDao();
		}
		return instance;
	}

	private static final String GET_SHOW_BY_ID = "FROM Show WHERE id=:id";
	private static final String GET_MONITORS_SHOW = "FROM Show WHERE idMonitori=:idMonitor";

	private static final String GET_MONITORS_SHOW_BY_TIME = "Select show FROM Show show,Movie movie "
			+ " WHERE ADDTIME(show.time,SEC_TO_TIME(movie.length*60))>=:thisTime and TIMEDIFF(show.time,SEC_TO_TIME(:thisLength*60))<=:thisTime"
			+ " and show.date=:thisDate";

	private static final String GET_MOVIES_SHOW = "FROM Show WHERE idMovie=:idMovie";
	private static final String GET_ALL_SHOWS = "from Show";

	private SessionFactory sessionFactory;
	private Transaction trns = null;
	private Session session;

	private ShowDao() {
		sessionFactory = HibernateUtil.getSessionFactory();
	}

	public boolean add(Show show) {
		session = sessionFactory.openSession();

		try {
			trns = session.beginTransaction();
			session.save(show);
			session.getTransaction().commit();
			return true;

		} catch (Exception e) {

			trns.rollback();
			return false;

		} finally {

			session.close();
		}
	}

	public boolean update(Show show) {
		session = sessionFactory.openSession();

		try {
			trns = session.beginTransaction();
			session.update(show);
			session.getTransaction().commit();
			return true;

		} catch (Exception e) {

			trns.rollback();
			return false;
		} finally {

			session.close();
		}
	}

	public boolean delete(int idShow) {
		session = sessionFactory.openSession();

		try {
			trns = session.beginTransaction();
			Show show = (Show) session.load(Show.class, new Integer(idShow));
			session.delete(show);
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
	public List<Show> getAllShows() {

		session = sessionFactory.openSession();
		List<Show> allShows = null;

		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(GET_ALL_SHOWS);
			allShows = (List<Show>) query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			trns.rollback();

		} finally {

			session.close();
		}
		return allShows;
	}

	@SuppressWarnings("unchecked")
	public List<Show> getMonitorsShow(int idMonitor) {
		session = sessionFactory.openSession();
		List<Show> shows = null;
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(GET_MONITORS_SHOW);
			query.setParameter("idMonitor", idMonitor);
			shows = (List<Show>) query.list();
			session.getTransaction().commit();
		} catch (RuntimeException e) {

			trns.rollback();

		} finally {

			session.close();
		}
		return shows;
	}

	@SuppressWarnings("unchecked")
	public List<Show> getMonitorsShowByTime(Date showDate, int movieLength) {

		Time time = new Time(showDate.getTime());

		session = sessionFactory.openSession();
		List<Show> shows = null;
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(GET_MONITORS_SHOW_BY_TIME);
			query.setParameter("thisTime", time);
			query.setParameter("thisLength", movieLength);
			query.setParameter("thisDate", showDate);
			shows = (List<Show>) query.list();
			session.getTransaction().commit();
		} catch (Exception e) {

			trns.rollback();

		} finally {

			session.close();
		}
		return shows;
	}

	@SuppressWarnings("unchecked")
	public List<Show> getMoviesShow(int idMovie) {
		session = sessionFactory.openSession();
		List<Show> shows = null;
		try {
			trns = session.beginTransaction();
			Query criteria = session.createQuery(GET_MOVIES_SHOW);
			criteria.setParameter("idMovie", idMovie);
			shows = (List<Show>) criteria.list();
			session.getTransaction().commit();
		} catch (Exception e) {

			trns.rollback();

		} finally {

			session.close();
		}
		return shows;
	}

	public Show getShow(int idShow) {
		session = sessionFactory.openSession();
		Show show = null;
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(GET_SHOW_BY_ID);
			query.setParameter("id", idShow);
			show = (Show) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {

			trns.rollback();

		} finally {
			
			session.close();
		}
		return show;
	}

}
