package eu.telecom_bretagne.recrutement.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the candidat database table.
 * 
 */
@Entity
@NamedQuery(name="Candidat.findAll", query="SELECT c FROM Candidat c")
public class Candidat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String addresse;

	private String login;

	private String nom;

	private String password;

	private String prenom;

	private String telephone;

	//bi-directional one-to-one association to Utilisateur
	@OneToOne
	@JoinColumn(name="id")
	private Utilisateur utilisateur;

	//bi-directional many-to-many association to Candidature
	@ManyToMany
	@JoinTable(
		name="candidat_candidature"
		, joinColumns={
			@JoinColumn(name="id_candidat")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_candidature")
			}
		)
	private List<Candidature> candidatures;

	//bi-directional many-to-many association to Message
	@ManyToMany
	@JoinTable(
		name="candidat_message"
		, joinColumns={
			@JoinColumn(name="id_candidat")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_message")
			}
		)
	private List<Message> messages;

	public Candidat() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddresse() {
		return this.addresse;
	}

	public void setAddresse(String addresse) {
		this.addresse = addresse;
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

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public List<Candidature> getCandidatures() {
		return this.candidatures;
	}

	public void setCandidatures(List<Candidature> candidatures) {
		this.candidatures = candidatures;
	}

	public List<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

}