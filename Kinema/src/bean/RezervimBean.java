package bean;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

import entitete.Reservation;

@ManagedBean
@ViewScoped
public class RezervimBean {
	
	private Reservation rezervim ;
	private ArrayList<Reservation> rezervime;
	//private RezervimDao rezervimDao = new RezervimDao();
	
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean ;
	

}
