package bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import dao.GenreDao;
import dao.MovieDao;
import dao.ReservationDao;
import dao.ShowDao;
import entitete.Movie;
import entitete.Show;
import utility.Messages;

@ManagedBean
@ViewScoped
public class MovieBean {

	private Movie movie;
	private MovieDao movieDao;
	private List<Movie> movies;
	private List<String> allGenres;

	@PostConstruct
	public void init() {
		this.movieDao = MovieDao.getInstance();
		this.allGenres = GenreDao.getInstance().getAllGenres();

		refreshBean();
	}

	public void refreshBean() {
		this.movie = new Movie();
		this.movies = movieDao.getAllMovies();
	}

	public String addMovie() {

		if (notExistMovieTitle(movie.getTitle())) {
			if (validateDate(movie.getStartDate(), movie.getEndDate(), new Date())) {
				if (movieDao.add(movie)) {
					Messages.addMessage("Filmi u shtua!");
					refreshBean();
				} else {
					Messages.addMessage("Filmi nuk u shtua!");
				}
			}
		} else {
			Messages.addMessage("Filmi nuk mund te shtohet!Ekziston film me kete titull!");
		}

		return null;
	}

	public String editMovie() {

		if (notExistMovieTitle(movie.getTitle())) {
			if (movieDao.editMovie(movie)) {
				Messages.addMessage("Filmi u editua!");
				refreshBean();
			} else {
				Messages.addMessage("Filmi nuk u editua!");
			}
		} else {
			Messages.addMessage("Filmi nuk mund te editohet!Ekziston film me kete titull!");
			return null;
		}

		return "movie?faces-redirect=true";
	}

	public String deleteMovie(int idMovie) {
		if (hasNotReservationForMoviesShow(idMovie)) {
			if (movieDao.delete(idMovie)) {
				Messages.addMessage("Filmi u fshi!");
				refreshBean();
			} else {
				Messages.addMessage("Filmi nuk u fshi!");
			}
		} else {
			Messages.addMessage("Filmi nuk mund te fshihet!\nKa rezervime per kete film!!");
		}

		return null;
	}

	public String editMovieRedirect() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		int id = Integer.parseInt(params.get("movieId"));

		movie.setId(id);
		
		return "editMovie?faces-redirect=true&includeViewParams=true";
	}

	public String movieDetail(int idMovie) {

		Movie movie = movieDao.getMoviebyId(idMovie);

		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().put("movie", movie.getId());

		return "movieDetail?faces-redirect=true";

	}

	public Movie getThisMovie() {
		FacesContext context = FacesContext.getCurrentInstance();
		Integer id = (Integer) context.getExternalContext().getSessionMap().get("movie");

		return movieDao.getMoviebyId(id);
	}

	private boolean hasNotReservationForMoviesShow(int idMovie) {
		boolean canDelete = true;
		List<Show> shows = ShowDao.getInstance().getMoviesShow(idMovie);
		for (Show show : shows) {
			if (!ReservationDao.getInstance().getShowsReservation(show.getId()).isEmpty()) {
				canDelete = false;
				break;
			}
		}
		return canDelete;
	}

	private boolean notExistMovieTitle(String title) {
		boolean notExist = true;
		Movie movie = movieDao.getMoviebyTitle(title);
		if (this.movie.getId() != null && this.movie.getId() != movie.getId()) {
			notExist = false;
		}
		return notExist;
	}

	public Movie getMovieFromId() {
		Movie test = movieDao.getMoviebyId(movie.getId());
		return test;
	}

	public void getMovieById() {
		this.movie = movieDao.getMoviebyId(movie.getId());
	}

	private boolean validateDate(Date dateFillimi, Date datePerfundimi, Date dataSot) {
		if (dateFillimi.compareTo(dataSot) < 0) {
			Messages.addMessage("Data e fillimit duhet te jete pas dates se sotme");
			return false;
		} else {
			if (datePerfundimi.compareTo(dateFillimi) < 0) {
				Messages.addMessage("Data e perfundimit duhet te jete pas dates se nisjes");
				return false;
			} else {
				return true;
			}
		}
	}

	// GETTERS AND SETTERS

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public MovieDao getMovieDao() {
		return movieDao;
	}

	public void setMovieDao(MovieDao movieDao) {
		this.movieDao = movieDao;
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

	public List<String> getAllGenres() {
		return allGenres;
	}

	public void setAllGenres(List<String> allGenres) {
		this.allGenres = allGenres;
	}

}
