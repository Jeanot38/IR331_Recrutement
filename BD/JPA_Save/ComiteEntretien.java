package eu.telecom_bretagne.recrutement.data.model2;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


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
	@SequenceGenerator(name="COMITE_ENTRETIEN_ID_GENERATOR", sequenceName=" COMITE_ENTRETIEN_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COMITE_ENTRETIEN_ID_GENERATOR")
	private Integer id;

	//bi-directional many-to-one association to Utilisateur
	@ManyToOne
	@JoinColumn(name="membres")
	private Utilisateur utilisateur;

	//bi-directional many-to-one association to Entretien
	@OneToMany(mappedBy="comiteEntretien")
	private Set<Entretien> entretiens;

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

	public Set<Entretien> getEntretiens() {
		return this.entretiens;
	}

	public void setEntretiens(Set<Entretien> entretiens) {
		this.entretiens = entretiens;
	}

	public Entretien addEntretien(Entretien entretien) {
		getEntretiens().add(entretien);
		entretien.setComiteEntretien(this);

		return entretien;
	}

	public Entretien removeEntretien(Entretien entretien) {
		getEntretiens().remove(entretien);
		entretien.setComiteEntretien(null);

		return entretien;
	}

}
