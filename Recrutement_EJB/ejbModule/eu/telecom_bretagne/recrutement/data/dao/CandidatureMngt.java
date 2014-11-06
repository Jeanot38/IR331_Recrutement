
package eu.telecom_bretagne.recrutement.data.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import eu.telecom_bretagne.recrutement.data.model.Candidature;
import eu.telecom_bretagne.recrutement.exception.BadParameterException;

/**
 * Session Bean implementation class CandidatureDAO
 */
@Stateless
@LocalBean
public class CandidatureMngt implements DAO<Candidature> {
	@PersistenceContext
	EntityManager em;

	public Candidature create (Candidature entity) throws BadParameterException {
		
		if(entity.getCv() == null || entity.getCv().equals("")) {
    		throw new BadParameterException("Le contenu du CV ne peut être nul");
    	}
		
		if(entity.getEtat() == null || entity.getEtat().equals("")) {
    		throw new BadParameterException("L'etat ne peut être nul");
    	}
		
    	if(entity.getLettreMotivation() == null || entity.getLettreMotivation().equals("")) {
    		throw new BadParameterException("Le contenu de la lettre de motivation ne peut être nul");
    	}
    	
    	if(entity.getCandidat() == null) {
    		throw new BadParameterException("La candidature doit être lié à un candidat");
    	}
    	
    	if(entity.getDateCreation() == null) {
    		Date date= new Date();
    		entity.setDateCreation(new Timestamp(date.getTime()));
    	}
    	
		em.persist(entity);
		return em.merge(entity);		
	}
	
	public Candidature findById (int id) {
		return em.find(Candidature.class, id);
	}
	
	public List <Candidature> findAll() {
		Query query = em
				.createQuery("select candidature from Candidature candidature order by candidature.id");
		return query.getResultList();
		
	}
	
	public Candidature update (Candidature entity) throws BadParameterException {
		
		if(entity.getCv() == null || entity.getCv() == "") {
    		throw new BadParameterException("Le contenu du CV ne peut être nul");
    	}
    	
		if(entity.getEtat() == null || entity.getEtat().equals("")) {
    		throw new BadParameterException("L'etat ne peut être nul");
    	}
		
    	if(entity.getLettreMotivation() == null || entity.getLettreMotivation() == "") {
    		throw new BadParameterException("Le contenu de la lettre de motivation ne peut être nul");
    	}
    	
    	if(entity.getCandidat() == null) {
    		throw new BadParameterException("La candidature doit être lié à un candidat");
    	}
    	
    	if(entity.getDateCreation() == null) {
    		throw new BadParameterException("La date de création de la candidature doit être renseignée");
    	}
    	
		return em.merge(entity);
	}
	
	public  void delete (Candidature entity) {
		if(!em.contains(entity)) {
			em.merge(entity);
		}
		em.remove(entity);
	}

}
