package eu.telecom_bretagne.recrutement.data.model2;

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

}
