package eu.telecom_bretagne.recrutement.data.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the vote database table.
 * 
 */
@Entity
@NamedQuery(name="Vote.findAll", query="SELECT v FROM Vote v")
public class Vote implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="VOTE_ID_GENERATOR", sequenceName=" VOTE_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="VOTE_ID_GENERATOR")
	private Integer id;

	private String commentaires;

	private Integer note;

	//bi-directional one-to-one association to Entretien
	@OneToOne
	@PrimaryKeyJoinColumn(name="id")
	private Entretien entretien;

	public Vote() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCommentaires() {
		return this.commentaires;
	}

	public void setCommentaires(String commentaires) {
		this.commentaires = commentaires;
	}

	public Integer getNote() {
		return this.note;
	}

	public void setNote(Integer note) {
		this.note = note;
	}

	public Entretien getEntretien() {
		return this.entretien;
	}

	public void setEntretien(Entretien entretien) {
		this.entretien = entretien;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Vote)) {
			return false;
		}
		
		Vote vote = (Vote) o;
		
		if(this.getId() != null && !this.getId().equals(vote.getId())) {
			return false;
		} else if(this.getId() == null && vote.getId() != null) {
			return false;
		}
		
		if(this.getCommentaires() != null && !this.getCommentaires().equals(vote.getCommentaires())) {
			return false;
		} else if(this.getCommentaires() == null && vote.getCommentaires() != null) {
			return false;
		}
		
		/*if(this.getEntretien() != null && !this.getEntretien().equals(vote.getEntretien())) {
			return false;
		} else if(this.getEntretien() == null && vote.getEntretien() != null) {
			return false;
		}*/
		
		if(this.getNote() != null && !this.getNote().equals(vote.getNote())) {
			return false;
		} else if(this.getNote() == null && vote.getNote() != null) {
			return false;
		}
		
		return true;
	}
	
}
