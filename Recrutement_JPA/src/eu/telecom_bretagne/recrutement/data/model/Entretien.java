package eu.telecom_bretagne.recrutement.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the entretien database table.
 * 
 */
@Entity
@NamedQuery(name="Entretien.findAll", query="SELECT e FROM Entretien e")
public class Entretien implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Temporal(TemporalType.DATE)
	@Column(name="date_entretien")
	private Date dateEntretien;

	//bi-directional many-to-many association to Candidature
	@ManyToMany(mappedBy="entretiens")
	private List<Candidature> candidatures;

	//bi-directional many-to-many association to ComiteEntretien
	@ManyToMany(mappedBy="entretiens")
	private List<ComiteEntretien> comiteEntretiens;

	//bi-directional many-to-one association to Vote
	@ManyToOne
	@JoinColumn(name="id_vote")
	private Vote vote;

	public Entretien() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateEntretien() {
		return this.dateEntretien;
	}

	public void setDateEntretien(Date dateEntretien) {
		this.dateEntretien = dateEntretien;
	}

	public List<Candidature> getCandidatures() {
		return this.candidatures;
	}

	public void setCandidatures(List<Candidature> candidatures) {
		this.candidatures = candidatures;
	}

	public List<ComiteEntretien> getComiteEntretiens() {
		return this.comiteEntretiens;
	}

	public void setComiteEntretiens(List<ComiteEntretien> comiteEntretiens) {
		this.comiteEntretiens = comiteEntretiens;
	}

	public Vote getVote() {
		return this.vote;
	}

	public void setVote(Vote vote) {
		this.vote = vote;
	}

}