package eu.telecom_bretagne.recrutement.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the entretien database table.
 * 
 */
@Entity
@NamedQuery(name="Entretien.findAll", query="SELECT e FROM Entretien e")
public class Entretien implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ENTRETIEN_ID_GENERATOR", sequenceName=" ENTRETIEN_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ENTRETIEN_ID_GENERATOR")
	private Integer id;

	@Column(name="date_entretien")
	private Timestamp dateEntretien;

	private String etat;

	//bi-directional many-to-one association to Candidature
	@ManyToOne
	@JoinColumn(name="id_candidature")
	private Candidature candidature;

	//bi-directional many-to-one association to ComiteEntretien
	@ManyToOne
	@JoinColumn(name="id_comite_entretien")
	private ComiteEntretien comiteEntretien;

	//bi-directional one-to-one association to Vote
	@OneToOne(mappedBy="entretien")
	private Vote vote;

	public Entretien() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getDateEntretien() {
		return this.dateEntretien;
	}

	public void setDateEntretien(Timestamp dateEntretien) {
		this.dateEntretien = dateEntretien;
	}

	public String getEtat() {
		return this.etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public Candidature getCandidature() {
		return this.candidature;
	}

	public void setCandidature(Candidature candidature) {
		this.candidature = candidature;
	}

	public ComiteEntretien getComiteEntretien() {
		return this.comiteEntretien;
	}

	public void setComiteEntretien(ComiteEntretien comiteEntretien) {
		this.comiteEntretien = comiteEntretien;
	}

	public Vote getVote() {
		return this.vote;
	}

	public void setVote(Vote vote) {
		this.vote = vote;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Entretien)) {
			return false;
		}
		
		Entretien entretien = (Entretien) o;
		
		if(this.getId() != null && !this.getId().equals(entretien.getId())) {
			return false;
		} else if(this.getId() == null && entretien.getId() != null) {
			return false;
		}
		
		/*if(this.getCandidature() != null && !this.getCandidature().equals(entretien.getCandidature())) {
			return false;
		} else if(this.getCandidature() == null && entretien.getCandidature() != null) {
			return false;
		}*/
		
		/*if(this.getComiteEntretien() != null && !this.getComiteEntretien().equals(entretien.getComiteEntretien())) {
			return false;
		} else if(this.getComiteEntretien() == null && entretien.getComiteEntretien() != null) {
			return false;
		}*/
		
		if(this.getDateEntretien() != null && !this.getDateEntretien().equals(entretien.getDateEntretien())) {
			return false;
		} else if(this.getDateEntretien() == null && entretien.getDateEntretien() != null) {
			return false;
		}
		
		if(this.getEtat() != null && !this.getEtat().equals(entretien.getEtat())) {
			return false;
		} else if(this.getEtat() == null && entretien.getEtat() != null) {
			return false;
		}
		
		/*if(this.getVote() != null && !this.getVote().equals(entretien.getVote())) {
			return false;
		} else if(this.getVote() == null && entretien.getVote() != null) {
			return false;
		}*/
		
		return true;
	}

}
