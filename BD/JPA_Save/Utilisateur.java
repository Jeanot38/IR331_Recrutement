package eu.telecom_bretagne.recrutement.data.model2;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Set;


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

	//bi-directional many-to-one association to ComiteEntretien
	@OneToMany(mappedBy="utilisateur")
	private Set<ComiteEntretien> comiteEntretiens;

	//bi-directional one-to-one association to Directeur
	@OneToOne(mappedBy="utilisateur")
	private Directeur directeur;

	//bi-directional one-to-one association to ServiceRh
	@OneToOne(mappedBy="utilisateur")
	private ServiceRh serviceRh;

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

	public Set<ComiteEntretien> getComiteEntretiens() {
		return this.comiteEntretiens;
	}

	public void setComiteEntretiens(Set<ComiteEntretien> comiteEntretiens) {
		this.comiteEntretiens = comiteEntretiens;
	}

	public ComiteEntretien addComiteEntretien(ComiteEntretien comiteEntretien) {
		getComiteEntretiens().add(comiteEntretien);
		comiteEntretien.setUtilisateur(this);

		return comiteEntretien;
	}

	public ComiteEntretien removeComiteEntretien(ComiteEntretien comiteEntretien) {
		getComiteEntretiens().remove(comiteEntretien);
		comiteEntretien.setUtilisateur(null);

		return comiteEntretien;
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
	
	public boolean equals(Object o) {
		if(!(o instanceof Utilisateur)) {
			return false;
		}
		
		Utilisateur utilisateur = (Utilisateur) o;
		
		if(this.getId() == null && utilisateur.getId() != null || !this.getId().equals(utilisateur.getId())) {
			return false;
		}
		
		if(this.getLogin() == null && utilisateur.getLogin() != null || !this.getLogin().equals(utilisateur.getLogin())) {
			return false;
		}
		
		if(this.getNom() == null && utilisateur.getNom() != null || !this.getNom().equals(utilisateur.getNom())) {
			return false;
		}
		
		if(this.getPrenom() == null && utilisateur.getPrenom() != null || !this.getPrenom().equals(utilisateur.getPrenom())) {
			return false;
		}
		
		return true;
	}

}
