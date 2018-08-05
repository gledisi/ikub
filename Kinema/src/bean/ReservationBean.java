package bean;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

import dao.ReservationDao;
import dao.ShowDao;
import entitete.Reservation;
import entitete.Show;
import utility.Messages;

@ManagedBean(name="reservationBean")
@ViewScoped
public class ReservationBean {
	
	private Reservation reservation ;
	private List<Reservation> reservations;
	private ReservationDao reservationDao;
	
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean ;
	@ManagedProperty(value = "#{showBean}")
	private ShowBean showBean ;
		
	
	@PostConstruct
	public void init() {
		this.reservationDao = ReservationDao.getInstance();
		refreshBean();
	}

	public void refreshBean() {

		this.reservation = new Reservation();
		this.reservations = reservationDao.getUsersReservation(userBean.getUser().getId());

	}
	
	public String addReservation(int idShow) {
		
		Show show = ShowDao.getInstance().getShow(idShow);
		reservation.setDate(new Date());
		reservation.setShow(show);
		reservation.setPrice(23);
		reservation.setUser(userBean.getUser());

		if(reservationDao.addReservation(reservation)) {
			Messages.addMessage("Rezervimi u krye!");
			System.out.println("u shtua.");
		}else {
			Messages.addMessage("Rezervimi nuk u krye!");
		}
	
		return null;
	}
	
	public String deleteReservation(int idRes) {
		
		if(reservationDao.deleteReservation(idRes)) {
			Messages.addMessage("Rezervimi u Fshi!");
			refreshBean();
		}else {
			Messages.addMessage("Rezervimi nuk Fshi!");
			System.out.println("nuk u shtua.");
		}
		
		return null;
	}
	
	
	
	//GETTERS AND SETTERS

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public ReservationDao getReservationDao() {
		return reservationDao;
	}

	public void setReservationDao(ReservationDao reservationDao) {
		this.reservationDao = reservationDao;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public ShowBean getShowBean() {
		return showBean;
	}

	public void setShowBean(ShowBean showBean) {
		this.showBean = showBean;
	}
	
}
