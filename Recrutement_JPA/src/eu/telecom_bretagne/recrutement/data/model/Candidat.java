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
	@SequenceGenerator(name="CANDIDAT_ID_GENERATOR", sequenceName=" CANDIDAT_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CANDIDAT_ID_GENERATOR")
	private Integer id;

	private String addresse;

	private String telephone;

	//bi-directional one-to-one association to Utilisateur
	@OneToOne
	@PrimaryKeyJoinColumn(name="id")
	private Utilisateur utilisateur;

	//bi-directional many-to-one association to Candidature
	@OneToMany(mappedBy="candidat", fetch=FetchType.EAGER)
	private List<Candidature> candidatures;

	//bi-directional many-to-one association to Message
	@OneToMany(mappedBy="candidat")
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

	public Candidature addCandidature(Candidature candidature) {
		getCandidatures().add(candidature);
		candidature.setCandidat(this);

		return candidature;
	}

	public Candidature removeCandidature(Candidature candidature) {
		getCandidatures().remove(candidature);
		candidature.setCandidat(null);

		return candidature;
	}

	public List<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Message addMessage(Message message) {
		getMessages().add(message);
		message.setCandidat(this);

		return message;
	}

	public Message removeMessage(Message message) {
		getMessages().remove(message);
		message.setCandidat(null);

		return message;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Candidat)) {
			return false;
		}
		
		Candidat candidat = (Candidat) o;
		
		if(this.getId() != null && !this.getId().equals(candidat.getId())) {
			return false;
		} else if(this.getId() == null && candidat.getId() != null) {
			return false;
		}
		
		if(this.getAddresse() != null && !this.getAddresse().equals(candidat.getAddresse())) {
			return false;
		} else if(this.getAddresse() == null && candidat.getAddresse() != null) {
			return false;
		}
		
		/*if(this.getCandidatures() != null && !this.getCandidatures().equals(candidat.getCandidatures())) {
			return false;
		} else if(this.getCandidatures() == null && candidat.getCandidatures() != null) {
			return false;
		}*/
		
		/*if(this.getMessages() != null && !this.getMessages().equals(candidat.getMessages())) {
			return false;
		} else if(this.getMessages() == null && candidat.getMessages() != null) {
			return false;
		}*/
		
		if(this.getTelephone() != null && !this.getTelephone().equals(candidat.getTelephone())) {
			return false;
		} else if(this.getTelephone() == null && candidat.getTelephone() != null) {
			return false;
		}
		
		/*if(this.getUtilisateur() != null && !this.getUtilisateur().equals(candidat.getUtilisateur())) {
			return false;
		} else if(this.getUtilisateur() == null && candidat.getUtilisateur() != null) {
			return false;
		}*/
		
		return true;
	}

}
