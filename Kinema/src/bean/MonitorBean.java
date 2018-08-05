package bean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

import dao.MonitorDao;
import dao.ShowDao;
import entitete.Chair;
import entitete.Monitor;
import entitete.Show;
import utility.Messages;

@ManagedBean
@ViewScoped
public class MonitorBean {

	private Monitor monitor;
	private MonitorDao monitorDao;
	private List<Monitor> monitors;

	private Integer rows;
	private Integer columns;

	@ManagedProperty(value = "#{hallBean}")
	private HallBean hallBean;

	@PostConstruct
	public void init() {
		this.monitorDao = MonitorDao.getInstance();
		refreshBean();
	}

	private void refreshBean() {

		this.rows = null;
		this.columns = null;
		this.monitor = new Monitor();
		this.monitors = monitorDao.getAllMonitors();
	}

	public void getHallsMonitor() {

		int idHall = hallBean.getHall().getId();
		this.monitors = monitorDao.getHallMonitors(idHall);

	}

	public String addMonitor() {

		Set<Chair> chairs = createChairs(monitor);
		monitor.setHall(hallBean.getHall());
		monitor.setName(monitor.getName());
		monitor.setChairs(chairs);

		if (notExistMonitorName(monitor.getName())) {
			if (monitorDao.addMonitor(monitor)) {
				Messages.addMessage("Monitori u shtua!");
				refreshBean();
			} else {
				Messages.addMessage("Monitori nuk u shtua!");
			}
		} else {
			Messages.addMessage("Monitori me kete emer ekziston!");
		}

		return null;
	}

	public String editMonitor() {

		monitor.setHall(hallBean.getHall());

		if (notExistMonitorName(monitor.getName())) {
			if (monitorDao.editMonitor(monitor)) {
				Messages.addMessage("Monitori u editua!");
				refreshBean();
			} else {
				Messages.addMessage("Monitori nuk u editua!");
			}
		} else {
			Messages.addMessage("Monitori me kete emer ekziston!");
		}

		return null;
	}

	public String deleteMonitor(int idMonitor) {

		if (hasNotShow(idMonitor)) {
			if (monitorDao.deleteMonitor(idMonitor)) {
				Messages.addMessage("Monitori u fshi!");
				refreshBean();
			} else {
				Messages.addMessage("Monitori nuk u fshi!");

			}
		} else {
			Messages.addMessage("Ne kete monitor po shfaqen filma!");
		}

		return null;
	}

	public long countMonitors(int idHall) {
		return monitorDao.countMonitors(idHall);
	}

	public long countChairs(int idMonitor) {
		return monitorDao.countChairs(idMonitor);
	}

	public boolean notExistMonitorName(String name) {

		boolean notExist = true;
		Monitor monitor = monitorDao.getMonitorByName(name);
		if (monitor != null) {
			notExist = false;
		}
		return notExist;
	}

	public boolean hasNotShow(int idMonitor) {
		List<Show> shows = ShowDao.getInstance().getMonitorsShow(idMonitor);
		return shows.isEmpty();
	}

	private Set<Chair> createChairs(Monitor addMonitor) {
		Chair chair = null;
		Set<Chair> chairs = new HashSet<>();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				chair = new Chair();
				chair.setRow(i + 1);
				chair.setCol(j + 1);
				chair.setMonitor(addMonitor);
				chairs.add(chair);
			}
		}
		return chairs;
	}
	// GETTERS AND SETTERS

	public Monitor getMonitor() {
		return monitor;
	}

	public void setMonitor(Monitor monitor) {
		this.monitor = monitor;
	}

	public MonitorDao getMonitorDao() {
		return monitorDao;
	}

	public void setMonitorDao(MonitorDao monitorDao) {
		this.monitorDao = monitorDao;
	}

	public List<Monitor> getMonitors() {
		return monitors;
	}

	public void setMonitors(List<Monitor> monitors) {
		this.monitors = monitors;
	}

	public HallBean getHallBean() {
		return hallBean;
	}

	public void setHallBean(HallBean hallBean) {
		this.hallBean = hallBean;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getColumns() {
		return columns;
	}

	public void setColumns(Integer columns) {
		this.columns = columns;
	}

}
