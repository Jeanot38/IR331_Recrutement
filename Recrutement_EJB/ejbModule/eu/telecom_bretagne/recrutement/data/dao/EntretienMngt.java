package eu.telecom_bretagne.recrutement.data.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import eu.telecom_bretagne.recrutement.data.model.Entretien;

/**
 * Session Bean implementation class EntretienDAO
 */
@Stateless
@LocalBean
public class EntretienMngt implements DAO<Entretien> {

	public Entretien create (Entretien entity) {
		return new Entretien();
	}
	
	public Entretien findById (int id) {
		return new Entretien();
	}
	
	public Entretien update (Entretien entity) {
		return new Entretien();
	}
	
	public  void delete (Entretien entity) {
		
	}

}
