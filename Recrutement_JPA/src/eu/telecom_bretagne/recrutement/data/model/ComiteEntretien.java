package eu.telecom_bretagne.recrutement.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the comite_entretien database table.
 * 
 */
@Entity
@Table(name="comite_entretien")
@NamedQuery(name="ComiteEntretien.findAll", query="SELECT c FROM ComiteEntretien c")
public class ComiteEntretien implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="COMITE_ENTRETIEN_ID_GENERATOR", sequenceName=" COMITE_ENTRETIEN_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COMITE_ENTRETIEN_ID_GENERATOR")
	private Integer id;

	private String membres;

	//bi-directional many-to-one association to Entretien
	@OneToMany(mappedBy="comiteEntretien", fetch=FetchType.EAGER)
	private List<Entretien> entretiens;

	//bi-directional many-to-many association to Utilisateur
	@ManyToMany(mappedBy="comiteEntretiens")
	private List<Utilisateur> utilisateurs;

	public ComiteEntretien() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMembres() {
		return this.membres;
	}

	public void setMembres(String membres) {
		this.membres = membres;
	}

	public List<Entretien> getEntretiens() {
		return this.entretiens;
	}

	public void setEntretiens(List<Entretien> entretiens) {
		this.entretiens = entretiens;
	}

	public Entretien addEntretien(Entretien entretien) {
		getEntretiens().add(entretien);
		entretien.setComiteEntretien(this);

		return entretien;
	}

	public Entretien removeEntretien(Entretien entretien) {
		getEntretiens().remove(entretien);
		entretien.setComiteEntretien(null);

		return entretien;
	}

	public List<Utilisateur> getUtilisateurs() {
		return this.utilisateurs;
	}

	public void setUtilisateurs(List<Utilisateur> utilisateurs) {
		this.utilisateurs = utilisateurs;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof ComiteEntretien)) {
			return false;
		}
		
		ComiteEntretien comiteEntretien = (ComiteEntretien) o;
		
		if(this.getId() != null && !this.getId().equals(comiteEntretien.getId())) {
			return false;
		} else if(this.getId() == null && comiteEntretien.getId() != null) {
			return false;
		}
		
		/*if(this.getEntretiens() != null && !this.getEntretiens().equals(comiteEntretien.getEntretiens())) {
			return false;
		} else if(this.getEntretiens() == null && comiteEntretien.getEntretiens() != null) {
			return false;
		}*/
		
		if(this.getMembres() != null && !this.getMembres().equals(comiteEntretien.getMembres())) {
			return false;
		} else if(this.getMembres() == null && comiteEntretien.getMembres() != null) {
			return false;
		}
		
		/*if(this.getUtilisateurs() != null && !this.getUtilisateurs().equals(comiteEntretien.getUtilisateurs())) {
			return false;
		} else if(this.getUtilisateurs() == null && comiteEntretien.getUtilisateurs() != null) {
			return false;
		}*/
		
		return true;
	}

}
