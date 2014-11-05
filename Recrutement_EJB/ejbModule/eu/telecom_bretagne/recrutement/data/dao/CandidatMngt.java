package eu.telecom_bretagne.recrutement.data.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import eu.telecom_bretagne.recrutement.data.model.Candidat;
import eu.telecom_bretagne.recrutement.exception.BadParameterException;

/**
 * Session Bean implementation class CandidatDAO
 */
@Stateless
@LocalBean
public class CandidatMngt implements DAO<Candidat> {
	
	@PersistenceContext
	EntityManager em;

	public Candidat create (Candidat entity) throws BadParameterException {
		
		if(entity.getAddresse() == null || entity.getAddresse() == "") {
    		throw new BadParameterException("Une addresse doit être indiquée");
    	}
    	
    	if(entity.getTelephone() == null || entity.getTelephone() == "") {
    		throw new BadParameterException("Le numéro de téléphone doit être renseigné");
    	}
    	
		em.persist(entity);
		return em.merge(entity);		
	}
	
	public Candidat findById (int id) {
		return em.find(Candidat.class, id);
	}
	
	public List <Candidat> findAll() {
		Query query = em
				.createQuery("select candidat from Candidat candidat order by candidat.id");
		return query.getResultList();
		
	}
	
	public Candidat update (Candidat entity) throws BadParameterException {
		
		if(entity.getAddresse() == null || entity.getAddresse() == "") {
    		throw new BadParameterException("Une addresse doit être indiquée");
    	}
    	
    	if(entity.getTelephone() == null || entity.getTelephone() == "") {
    		throw new BadParameterException("Le numéro de téléphone doit être renseigné");
    	}
    	
		return em.merge(entity);
	}
	
	public  void delete (Candidat entity) {
		if(!em.contains(entity)) {
			em.merge(entity);
		}
		em.remove(entity);
	}

}
