package dao;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import entitete.Movie;
import hibernate.HibernateUtil;

public class MovieDao {

	private static MovieDao instance;

	public static MovieDao getInstance() {
		if (instance == null) {
			instance = new MovieDao();
		}
		return instance;
	}

	// Querys for Movie
	private static final String MOVIE_BY_ID = "FROM Movie WHERE id=:id";
	private static final String MOVIE_BY_TITLE = "FROM Movie WHERE title=:title";
	private static final String ALL_MOVIES = "from Movie";
	private static final String CURRENT_MOVIES = "FROM `Movie` WHERE `startDate`<=:today AND `endDate`>:today ";
	private static final String UPDATE_MOVIE = "Update Movie set title=:title , genre=:genre , imdb=:imdb , storyline=:storyline where id=:id";

	private SessionFactory sessionFactory;
	private Transaction trns = null;
	private Session session;

	private MovieDao() {
		sessionFactory = HibernateUtil.getSessionFactory();
	}

	public boolean add(Movie movie) {
		session = sessionFactory.openSession();
		boolean isAdd = true;
		try {
			trns = session.beginTransaction();
			session.save(movie);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
			isAdd = false;
		} finally {
			session.close();
		}

		return isAdd;
	}

	public boolean editMovie(Movie movie) {
		session = sessionFactory.openSession();
		try {
			trns = session.beginTransaction();
			session.createQuery(UPDATE_MOVIE).setParameter("title", movie.getTitle())
					.setParameter("genre", movie.getGenre()).setParameter("imdb", movie.getImdb())
					.setParameter("storyline", movie.getStoryline()).setParameter("id", movie.getId()).executeUpdate();

			session.getTransaction().commit();
			return true;
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			return false;
		} finally {
			session.close();
		}
	}

	public boolean delete(int idMovie) {
		session = sessionFactory.openSession();
		boolean isDelete = true;
		try {
			trns = session.beginTransaction();
			Movie movie = (Movie) session.load(Movie.class, new Integer(idMovie));
			session.delete(movie);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			isDelete = false;
		} finally {
			session.close();
		}

		return isDelete;
	}

	@SuppressWarnings("unchecked")
	public List<Movie> getAllMovies() {
		session = sessionFactory.openSession();
		List<Movie> allMovies = null;
		try {
			trns = session.beginTransaction();
			Query criteria = session.createQuery(ALL_MOVIES);
			allMovies = (List<Movie>) criteria.list();
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
		} finally {
			session.close();
		}
		return allMovies;
	}

	public Movie getMoviebyId(int idMovie) {
		session = sessionFactory.openSession();
		Movie movie = null;
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(MOVIE_BY_ID);
			query.setParameter("id", idMovie);
			movie = (Movie) query.uniqueResult();
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			if (trns != null) {
				trns.rollback();
			}
		} finally {
			session.close();
		}
		return movie;
	}

	public Movie getMoviebyTitle(String title) {
		session = sessionFactory.openSession();
		Movie movie = null;
		try {
			trns = session.beginTransaction();
			Query criteria = session.createQuery(MOVIE_BY_TITLE);
			criteria.setParameter("title", title);
			movie = (Movie) criteria.uniqueResult();
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}

		} finally {
			session.close();
		}
		return movie;
	}

	@SuppressWarnings("unchecked")
	public List<Movie> currentMovies() {
		session = sessionFactory.openSession();
		List<Movie> movies = null;
		try {
			trns = session.beginTransaction();
			Query criteria = session.createQuery(CURRENT_MOVIES);
			criteria.setParameter("today", LocalDate.now());
			movies = (List<Movie>) criteria.list();
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
		} finally {
			session.close();
		}
		return movies;
	}

}
