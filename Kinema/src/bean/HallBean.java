package bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import dao.HallDao;
import dao.MonitorDao;
import dao.ShowDao;
import entitete.Hall;
import entitete.Monitor;
import utility.Messages;

@ManagedBean
@ViewScoped
public class HallBean {

	private Hall hall;
	private HallDao hallDao;
	private List<Hall> halls;

	@PostConstruct
	public void init() {

		this.hallDao = HallDao.getInstance();
		refreshBean();
	}

	private void refreshBean() {
		this.hall = new Hall();
		this.halls = hallDao.getAllHalls();
	}

	public String addHall() {

		if (notExistHallName(hall.getName())) {

			if (hallDao.addHall(hall)) {
				Messages.addMessage("Salla u shtua!");
				refreshBean();
			} else {
				Messages.addMessage("Salla nuk u shtua!");
			}

		} else {
			Messages.addMessage("Salla me kete emer ekziston!");
		}

		return null;
	}

	public String editHall() {

		if (notExistHallName(hall.getName())) {

			if (hallDao.editHall(hall)) {
				Messages.addMessage("Salla u editua!");
				refreshBean();
			} else {
				Messages.addMessage("Salla nuk u editua!");
			}

		} else {
			Messages.addMessage("Salla me kete emer ekziston!");
		}

		return null;
	}

	public String deleteHall(int idHall) {

		if (canDeleteHall(idHall)) {

			if (hallDao.deleteHall(idHall)) {
				Messages.addMessage("Salla u fshi!");
				refreshBean();
			} else {
				Messages.addMessage("Salla nuk u fshi!");
			}

		} else {
			Messages.addMessage("Salla ka filmi qe po shfaqen!");
		}

		return null;
	}

	public List<Hall> getAllHalls() {
		List<Hall> allHalls = hallDao.getAllHalls();
		return allHalls;
	}

	private boolean notExistHallName(String name) {
		boolean notExists = true;
		Hall hall = HallDao.getInstance().getHallByName(name);	
		
		if (hall != null) {
			System.out.println(hall.getName());
			notExists= false;
		} 
		
		return notExists;
	}

	private boolean canDeleteHall(int idHall) {
		boolean canDelete = true;
		List<Monitor> monitors = MonitorDao.getInstance().getHallMonitors(idHall);
		for (Monitor monitor : monitors) {
			if (!ShowDao.getInstance().getMonitorsShow(monitor.getId()).isEmpty()) {
				canDelete = false;
			}
		}
		return canDelete;
	}

	// GETTERS AND SETTERS

	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}

	public HallDao getHallDao() {
		return hallDao;
	}

	public void setHallDao(HallDao hallDao) {
		this.hallDao = hallDao;
	}

	public List<Hall> getHalls() {
		return halls;
	}

	public void setHalls(List<Hall> halls) {
		this.halls = halls;
	}

}
