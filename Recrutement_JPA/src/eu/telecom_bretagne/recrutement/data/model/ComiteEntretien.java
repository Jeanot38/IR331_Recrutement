package eu.telecom_bretagne.recrutement.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the comite_entretien database table.
 * 
 */
@Entity
@Table(name="comite_entretien")
@NamedQuery(name="ComiteEntretien.findAll", query="SELECT c FROM ComiteEntretien c")
public class ComiteEntretien implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	//bi-directional many-to-one association to Utilisateur
	@ManyToOne
	@JoinColumn(name="membres")
	private Utilisateur utilisateur;

	//bi-directional many-to-many association to Entretien
	@ManyToMany
	@JoinTable(
		name="comite_entretien_entretien"
		, joinColumns={
			@JoinColumn(name="id_comite_entretien")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_entretien")
			}
		)
	private List<Entretien> entretiens;

	public ComiteEntretien() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public List<Entretien> getEntretiens() {
		return this.entretiens;
	}

	public void setEntretiens(List<Entretien> entretiens) {
		this.entretiens = entretiens;
	}

}