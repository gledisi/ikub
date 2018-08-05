package entitete;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name = "monitor")
public class Monitor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column
	private String name;

	
	@ManyToOne
	@JoinColumn(name = "idHall")
	private Hall hall;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "monitor", orphanRemoval = true)
	private Set<Chair> chairs = new HashSet<>();
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "monitori")
	private Set<Show> shows = new HashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Chair> getChairs() {
		return chairs;
	}

	public void setChairs(Set<Chair> chairs) {
		this.chairs = chairs;
	}

	public Set<Show> getShows() {
		return shows;
	}

	public void setShows(Set<Show> shows) {
		this.shows = shows;
	}

	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}

}
