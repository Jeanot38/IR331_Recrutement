package eu.telecom_bretagne.recrutement.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the message database table.
 * 
 */
@Entity
@NamedQuery(name="Message.findAll", query="SELECT m FROM Message m")
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MESSAGE_ID_GENERATOR", sequenceName=" MESSAGE_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MESSAGE_ID_GENERATOR")
	private Integer id;

	private String contenu;

	@Column(name="date_creation")
	private Timestamp dateCreation;

	private String sujet;

	//bi-directional many-to-one association to Candidat
	@ManyToOne
	@JoinColumn(name="id_candidat")
	private Candidat candidat;

	public Message() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContenu() {
		return this.contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public Timestamp getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(Timestamp dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getSujet() {
		return this.sujet;
	}

	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	public Candidat getCandidat() {
		return this.candidat;
	}

	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Message)) {
			return false;
		}
		
		Message message = (Message) o;
		
		if(this.getId() != null && !this.getId().equals(message.getId())) {
			return false;
		} else if(this.getId() == null && message.getId() != null) {
			return false;
		}
		
		/*if(this.getCandidat() != null && !this.getCandidat().equals(message.getCandidat())) {
			return false;
		} else if(this.getCandidat() == null && message.getCandidat() != null) {
			return false;
		}*/
		
		if(this.getContenu() != null && !this.getContenu().equals(message.getContenu())) {
			return false;
		} else if(this.getContenu() == null && message.getContenu() != null) {
			return false;
		}
		
		if(this.getDateCreation() != null && !this.getDateCreation().equals(message.getDateCreation())) {
			return false;
		} else if(this.getDateCreation() == null && message.getDateCreation() != null) {
			return false;
		}
		
		if(this.getSujet() != null && !this.getSujet().equals(message.getSujet())) {
			return false;
		} else if(this.getSujet() == null && message.getSujet() != null) {
			return false;
		}
		
		return true;
	}
	
}
