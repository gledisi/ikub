package entitete;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ticket")
public class Ticket {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ElementCollection
	@ManyToOne
    @JoinColumn(name="idReservation", nullable=false)
	private Reservation reservation;
	@ElementCollection
	@ManyToOne
    @JoinColumn(name="idChair", nullable=false)
	private Chair chair ;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Reservation getReservation() {
		return reservation;
	}
	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
	public Chair getChair() {
		return chair;
	}
	public void setChair(Chair chair) {
		this.chair = chair;
	}

	
}
