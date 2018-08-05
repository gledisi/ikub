package entitete;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;



@Entity
@Table(name = "movie")
@DynamicUpdate(value = true)
public class Movie {

		
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column
	private String title;
	@Column
	private String genre;
	@Column
	private String storyline;
	@Column
	private Double imdb;
	@Column
	private Integer length;
	@Temporal(TemporalType.DATE)
	private Date startDate;
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy = "movie")
	private Set<Show> shows = new HashSet<>();
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getStoryline() {
		return storyline;
	}
	public void setStoryline(String storyline) {
		this.storyline = storyline;
	}
	public Double getImdb() {
		return imdb;
	}
	public void setImdb(Double imdb) {
		this.imdb = imdb;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Set<Show> getShows() {
		return shows;
	}
	public void setShows(Set<Show> shows) {
		this.shows = shows;
	}  

	
	
}
