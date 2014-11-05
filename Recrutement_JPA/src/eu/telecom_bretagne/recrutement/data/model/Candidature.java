package eu.telecom_bretagne.recrutement.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the candidature database table.
 * 
 */
@Entity
@NamedQuery(name="Candidature.findAll", query="SELECT c FROM Candidature c")
public class Candidature implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CANDIDATURE_ID_GENERATOR", sequenceName=" CANDIDATURE_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CANDIDATURE_ID_GENERATOR")
	private Integer id;

	private String cv;

	@Column(name="date_creation")
	private Timestamp dateCreation;

	private String etat;

	@Column(name="lettre_motivation")
	private String lettreMotivation;

	//bi-directional many-to-one association to Candidat
	@ManyToOne
	@JoinColumn(name="id_candidat")
	private Candidat candidat;

	//bi-directional many-to-one association to Entretien
	@OneToMany(mappedBy="candidature", fetch=FetchType.EAGER)
	private List<Entretien> entretiens;

	public Candidature() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCv() {
		return this.cv;
	}

	public void setCv(String cv) {
		this.cv = cv;
	}

	public Timestamp getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(Timestamp dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getEtat() {
		return this.etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public String getLettreMotivation() {
		return this.lettreMotivation;
	}

	public void setLettreMotivation(String lettreMotivation) {
		this.lettreMotivation = lettreMotivation;
	}

	public Candidat getCandidat() {
		return this.candidat;
	}

	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
	}

	public List<Entretien> getEntretiens() {
		return this.entretiens;
	}

	public void setEntretiens(List<Entretien> entretiens) {
		this.entretiens = entretiens;
	}

	public Entretien addEntretien(Entretien entretien) {
		getEntretiens().add(entretien);
		entretien.setCandidature(this);

		return entretien;
	}

	public Entretien removeEntretien(Entretien entretien) {
		getEntretiens().remove(entretien);
		entretien.setCandidature(null);

		return entretien;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Candidature)) {
			return false;
		}
		
		Candidature candidature = (Candidature) o;
		
		if(this.getId() != null && !this.getId().equals(candidature.getId())) {
			return false;
		} else if(this.getId() == null && candidature.getId() != null) {
			return false;
		}
		
		/*if(this.getCandidat() != null && !this.getCandidat().equals(candidature.getCandidat())) {
			return false;
		} else if(this.getCandidat() == null && candidature.getCandidat() != null) {
			return false;
		}*/
		
		if(this.getCv() != null && !this.getCv().equals(candidature.getCv())) {
			return false;
		} else if(this.getCv() == null && candidature.getCv() != null) {
			return false;
		}
		
		if(this.getDateCreation() != null && !this.getDateCreation().equals(candidature.getDateCreation())) {
			return false;
		} else if(this.getDateCreation() == null && candidature.getDateCreation	() != null) {
			return false;
		}
		
		/*if(this.getEntretiens() != null && !this.getEntretiens().equals(candidature.getEntretiens())) {
			return false;
		} else if(this.getEntretiens() == null && candidature.getEntretiens() != null) {
			return false;
		}*/
		
		if(this.getEtat() != null && !this.getEtat().equals(candidature.getEtat())) {
			return false;
		} else if(this.getEtat() == null && candidature.getEtat() != null) {
			return false;
		}
		
		if(this.getLettreMotivation() != null && !this.getLettreMotivation().equals(candidature.getLettreMotivation())) {
			return false;
		} else if(this.getLettreMotivation() == null && candidature.getLettreMotivation() != null) {
			return false;
		}
		
		return true;		
	}

}
