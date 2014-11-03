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

}
