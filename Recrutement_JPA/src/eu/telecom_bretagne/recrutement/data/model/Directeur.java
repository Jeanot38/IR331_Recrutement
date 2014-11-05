package eu.telecom_bretagne.recrutement.data.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the directeur database table.
 * 
 */
@Entity
@NamedQuery(name="Directeur.findAll", query="SELECT d FROM Directeur d")
public class Directeur implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIRECTEUR_ID_GENERATOR", sequenceName=" DIRECTEUR_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIRECTEUR_ID_GENERATOR")
	private Integer id;

	private String localisation;

	private String service;

	//bi-directional one-to-one association to Utilisateur
	@OneToOne
	@PrimaryKeyJoinColumn(name="id")
	private Utilisateur utilisateur;

	public Directeur() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLocalisation() {
		return this.localisation;
	}

	public void setLocalisation(String localisation) {
		this.localisation = localisation;
	}

	public String getService() {
		return this.service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Directeur)) {
			return false;
		}
		
		Directeur directeur = (Directeur) o;
		
		if(this.getId() != null && !this.getId().equals(directeur.getId())) {
			return false;
		} else if(this.getId() == null && directeur.getId() != null) {
			return false;
		}
		
		if(this.getLocalisation() != null && !this.getLocalisation().equals(directeur.getLocalisation())) {
			return false;
		} else if(this.getLocalisation() == null && directeur.getLocalisation() != null) {
			return false;
		}
		
		if(this.getService() != null && !this.getService().equals(directeur.getService())) {
			return false;
		} else if(this.getService() == null && directeur.getService() != null) {
			return false;
		}
		
		/*if(this.getUtilisateur() != null && !this.getUtilisateur().equals(directeur.getUtilisateur())) {
			return false;
		} else if(this.getUtilisateur() == null && directeur.getUtilisateur() != null) {
			return false;
		}*/
		
		return true;
	}

}
