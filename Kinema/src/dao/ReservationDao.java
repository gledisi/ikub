package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import entitete.Reservation;
import hibernate.HibernateUtil;

public class ReservationDao {

	private static ReservationDao instance;

	public static ReservationDao getInstance() {
		if (instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}

	// Querys for Reservation
	private static final String ALL_RESERVATIONS = "from Reservation";
	private static final String SHOWS_RESERVATION = "FROM Reservation WHERE idShow=:idShow";
	private static final String USERS_RESERVATION = "FROM Reservation WHERE idUser=:idUser";

	private SessionFactory sessionFactory;
	private Transaction trns = null;
	private Session session;

	private ReservationDao() {
		sessionFactory = HibernateUtil.getSessionFactory();
	}

	public boolean addReservation(Reservation reservation) {

		session = sessionFactory.openSession();

		try {
			trns = session.beginTransaction();
			session.save(reservation);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {

			trns.rollback();
			return false;

		} finally {

			session.close();
		}
	}

	public boolean update(Reservation reservation) {
		session = sessionFactory.openSession();

		try {
			trns = session.beginTransaction();
			session.update(reservation);
			session.getTransaction().commit();
			return true;

		} catch (Exception e) {

			trns.rollback();
			return false;

		} finally {

			session.close();
		}
	}

	public boolean delete(int idReservation) {
		session = sessionFactory.openSession();

		try {
			trns = session.beginTransaction();
			Reservation reservation = (Reservation) session.load(Reservation.class, new Integer(idReservation));
			session.delete(reservation);
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
	public List<Reservation> getAllReservations() {

		session = sessionFactory.openSession();
		List<Reservation> allReservations = null;

		try {
			trns = session.beginTransaction();
			Query criteria = session.createQuery(ALL_RESERVATIONS);
			allReservations = (List<Reservation>) criteria.list();
			session.getTransaction().commit();

		} catch (Exception e) {

			trns.rollback();

		} finally {

			session.close();
		}
		return allReservations;
	}

	@SuppressWarnings("unchecked")
	public List<Reservation> getShowsReservation(int idShow) {

		session = sessionFactory.openSession();
		List<Reservation> showsReservation = null;

		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(SHOWS_RESERVATION);
			query.setParameter("idShow", idShow);
			showsReservation = (List<Reservation>) query.list();
			session.getTransaction().commit();
		} catch (Exception e) {

			trns.rollback();

		} finally {

			session.close();
		}
		return showsReservation;
	}

	@SuppressWarnings("unchecked")
	public List<Reservation> getUsersReservation(int idUser) {
		session = sessionFactory.openSession();
		List<Reservation> userReservation = null;
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(USERS_RESERVATION);
			query.setParameter("idUser", idUser);
			userReservation = (List<Reservation>) query.list();
			session.getTransaction().commit();
		} catch (Exception e) {

			trns.rollback();

		} finally {

			session.close();
		}
		return userReservation;
	}

	public boolean deleteReservation(int idRes) {
		session = sessionFactory.openSession();

		try {
			trns = session.beginTransaction();
			Reservation reservation = (Reservation) session.load(Reservation.class, new Integer(idRes));
			session.delete(reservation);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {

			trns.rollback();
			return false;

		} finally {

			session.close();
		}
	}

}
