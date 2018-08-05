
package bean;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

import dao.ReservationDao;
import dao.ShowDao;
import entitete.Monitor;
import entitete.Movie;
import entitete.Reservation;
import entitete.Show;
import utility.Messages;

@ManagedBean
@ViewScoped
public class ShowBean {

	private Show show;
	private ShowDao showDao;
	private List<Show> shows;
	
	@ManagedProperty(value = "#{movieBean}")
	private MovieBean movieBean;

	@ManagedProperty(value = "#{monitorBean}")
	private MonitorBean monitorBean;

	@PostConstruct
	public void init() {
		this.showDao = ShowDao.getInstance();
		refreshBean();
	}

	public void refreshBean() {

		this.show = new Show();
		this.shows = showDao.getAllShows();

	}

	public String addShow() {

		Show showAdd = new Show();
		
		Monitor monitor = new Monitor();
		Movie movie = movieBean.getMovieFromId();
		monitor.setId(monitorBean.getMonitor().getId());
	
		showAdd.setMovie(movie);
		showAdd.setMonitori(monitor);
		showAdd.setDate(show.getDate());
		showAdd.setTime(show.getDate());

		if (canAddShow(showAdd)) {

			if(validateDate(movie.getStartDate(),movie.getEndDate(),show.getDate())) {
			if (showDao.add(showAdd)) {
				Messages.addMessage("Shfaqja u shtua!");
				refreshBean();
			}
			} else {
				Messages.addMessage("Shfaqja nuk u shtua!");
			}

		} else {
			Messages.addMessage("Shfaqja nuk mund te shtohet!\nPo transmetohet shfaqje ne kete orar!");
		}
		return null;
	}

	public String deleteShow(int idShow) {
		
		if (canDeleteShow(idShow)) {
			if (showDao.delete(idShow)) {
				Messages.addMessage("Shfaqja u fshi!");
				refreshBean();
			} else {
				Messages.addMessage("Shfaqja nuk u fshi!");
			}
		} else {
			Messages.addMessage("Shfaqja nuk mund te fshihet!\n Ka rezervime per kete shfaqje!!");
		}
		
		return null;
	}

	private boolean canAddShow(Show show) {
		List<Show> shows = showDao.getMonitorsShowByTime(show.getDate(), show.getMovie().getLength());
		return shows.isEmpty();
	}

	private boolean canDeleteShow(int idShow) {
		List<Reservation> reservations = ReservationDao.getInstance().getShowsReservation(idShow);
		return reservations.isEmpty();
	}
	
	public List<Show> getMoviesShow(){
		List<Show> moviesShow = showDao.getMoviesShow(movieBean.getThisMovie().getId());
		return moviesShow;
	}
	
	private boolean validateDate(Date movieStartDate,Date movieEndDate,Date showDate) {
		if (showDate.compareTo(movieEndDate) < 0 && showDate.compareTo(movieStartDate)>0 ) {
			return true;
		} else {
			Messages.addMessage("Data e shfaqjes duhet te jete midis "+movieStartDate +" dhe "+ movieEndDate);
			return false;
		}
	}

	// GETTERS AND SETTERS

	public Show getShow() {
		return show;
	}

	public MovieBean getMovieBean() {
		return movieBean;
	}

	public void setMovieBean(MovieBean movieBean) {
		this.movieBean = movieBean;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public List<Show> getShows() {
		return shows;
	}

	public void setShows(List<Show> shows) {
		this.shows = shows;
	}

	public MonitorBean getMonitorBean() {
		return monitorBean;
	}

	public void setMonitorBean(MonitorBean monitorBean) {
		this.monitorBean = monitorBean;
	}


}
