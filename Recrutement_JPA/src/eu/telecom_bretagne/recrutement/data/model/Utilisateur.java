package eu.telecom_bretagne.recrutement.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the utilisateur database table.
 * 
 */
@Entity
@NamedQuery(name="Utilisateur.findAll", query="SELECT u FROM Utilisateur u")
public class Utilisateur implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="UTILISATEUR_ID_GENERATOR", sequenceName=" UTILISATEUR_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="UTILISATEUR_ID_GENERATOR")
	private Integer id;

	private String login;

	private String nom;

	private String password;

	private String prenom;

	//bi-directional one-to-one association to Candidat
	@OneToOne(mappedBy="utilisateur")
	private Candidat candidat;

	//bi-directional one-to-one association to Directeur
	@OneToOne(mappedBy="utilisateur")
	private Directeur directeur;

	//bi-directional one-to-one association to ServiceRh
	@OneToOne(mappedBy="utilisateur")
	private ServiceRh serviceRh;

	//bi-directional many-to-many association to ComiteEntretien
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="utilisateur_comite_entretien"
		, joinColumns={
			@JoinColumn(name="id_utilisateur")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_comite_entretien")
			}
		)
	private List<ComiteEntretien> comiteEntretiens;

	public Utilisateur() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Candidat getCandidat() {
		return this.candidat;
	}

	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
	}

	public Directeur getDirecteur() {
		return this.directeur;
	}

	public void setDirecteur(Directeur directeur) {
		this.directeur = directeur;
	}

	public ServiceRh getServiceRh() {
		return this.serviceRh;
	}

	public void setServiceRh(ServiceRh serviceRh) {
		this.serviceRh = serviceRh;
	}

	public List<ComiteEntretien> getComiteEntretiens() {
		return this.comiteEntretiens;
	}

	public void setComiteEntretiens(List<ComiteEntretien> comiteEntretiens) {
		this.comiteEntretiens = comiteEntretiens;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Utilisateur)) {
			return false;
		}
		
		Utilisateur utilisateur = (Utilisateur) o;
		
		if(this.getId() != null && !this.getId().equals(utilisateur.getId())) {
			return false;
		} else if(this.getId() == null && utilisateur.getId() != null) {
			return false;
		}
		
		/*if(this.getCandidat() != null && !this.getCandidat().equals(utilisateur.getCandidat())) {
			return false;
		} else if(this.getCandidat() == null && utilisateur.getCandidat() != null) {
			return false;
		}*/
		
		/*if(this.getComiteEntretiens() != null && !this.getComiteEntretiens().equals(utilisateur.getComiteEntretiens())) {
			return false;
		} else if(this.getComiteEntretiens() == null && utilisateur.getComiteEntretiens() != null) {
			return false;
		}*/
		
		/*if(this.getDirecteur() != null && !this.getDirecteur().equals(utilisateur.getDirecteur())) {
			return false;
		} else if(this.getDirecteur() == null && utilisateur.getDirecteur() != null) {
			return false;
		}*/
		
		if(this.getLogin() != null && !this.getLogin().equals(utilisateur.getLogin())) {
			return false;
		} else if(this.getLogin() == null && utilisateur.getLogin() != null) {
			return false;
		}
		
		if(this.getNom() != null && !this.getNom().equals(utilisateur.getNom())) {
			return false;
		} else if(this.getNom() == null && utilisateur.getNom() != null) {
			return false;
		}
		
		if(this.getPassword() != null && !this.getPassword().equals(utilisateur.getPassword())) {
			return false;
		} else if(this.getPassword() == null && utilisateur.getPassword() != null) {
			return false;
		}
		
		if(this.getPrenom() != null && !this.getPrenom().equals(utilisateur.getPrenom())) {
			return false;
		} else if(this.getPrenom() == null && utilisateur.getPrenom() != null) {
			return false;
		}
		
		/*if(this.getServiceRh() != null && !this.getServiceRh().equals(utilisateur.getServiceRh())) {
			return false;
		} else if(this.getServiceRh() == null && utilisateur.getServiceRh() != null) {
			return false;
		}*/
		
		return true;
	}
	
}
