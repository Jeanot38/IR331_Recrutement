package eu.telecom_bretagne.recrutement.data.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import eu.telecom_bretagne.recrutement.data.model.Candidature;

/**
 * Session Bean implementation class CandidatureDAO
 */
@Stateless
@LocalBean
public class CandidatureMngt implements DAO<Candidature> {

	public Candidature create (Candidature entity) {
		return new Candidature();
	}
	
	public Candidature findById (int id) {
		return new Candidature();
	}
	
	public Candidature update (Candidature entity) {
		return new Candidature();
	}
	
	public  void delete (Candidature entity) {
		
	}

}
