package eu.telecom_bretagne.recrutement.data.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the service_rh database table.
 * 
 */
@Entity
@Table(name="service_rh")
@NamedQuery(name="ServiceRh.findAll", query="SELECT s FROM ServiceRh s")
public class ServiceRh implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SERVICE_RH_ID_GENERATOR", sequenceName=" SERVICE_RH_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SERVICE_RH_ID_GENERATOR")
	private Integer id;

	private String localisation;

	//bi-directional one-to-one association to Utilisateur
	@OneToOne
	@PrimaryKeyJoinColumn(name="id")
	private Utilisateur utilisateur;

	public ServiceRh() {
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

	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof ServiceRh)) {
			return false;
		}
		
		ServiceRh serviceRH = (ServiceRh) o;
		
		if(this.getId() != null && !this.getId().equals(serviceRH.getId())) {
			return false;
		} else if(this.getId() == null && serviceRH.getId() != null) {
			return false;
		}
		
		if(this.getLocalisation() != null && !this.getLocalisation().equals(serviceRH.getLocalisation())) {
			return false;
		} else if(this.getLocalisation() == null && serviceRH.getLocalisation() != null) {
			return false;
		}
		
		/*if(this.getUtilisateur() != null && !this.getUtilisateur().equals(serviceRH.getUtilisateur())) {
			return false;
		} else if(this.getUtilisateur() == null && serviceRH.getUtilisateur() != null) {
			return false;
		}*/
		
		return true;
	}
	
}
