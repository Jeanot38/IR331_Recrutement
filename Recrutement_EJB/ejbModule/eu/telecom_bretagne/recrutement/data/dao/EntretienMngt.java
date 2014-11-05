package eu.telecom_bretagne.recrutement.data.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import eu.telecom_bretagne.recrutement.data.model.Candidat;
import eu.telecom_bretagne.recrutement.data.model.Entretien;
import eu.telecom_bretagne.recrutement.exception.BadParameterException;

/**
 * Session Bean implementation class EntretienDAO
 */
@Stateless
@LocalBean
public class EntretienMngt implements DAO<Entretien> {
	
	@PersistenceContext
	EntityManager em;

	public Entretien create (Entretien entity) throws BadParameterException {
		
		if(entity.getDateEntretien() == null) {
    		throw new BadParameterException("La date de l'entretien doit être indiquée");
    	}
    	
    	if(entity.getEtat() == null || entity.getEtat() == "") {
    		throw new BadParameterException("L'entretien ne peut avoir un état nul");
    	}
    	
		em.persist(entity);
		return em.merge(entity);
	}
	
	public Entretien findById (int id) {
		return em.find(Entretien.class, id);
	}
	
	public List <Entretien> findAll() {
		Query query = em
				.createQuery("select entretien from Entretien entretien order by entretien.id");
		return query.getResultList();
	}
	
	public Entretien update (Entretien entity) throws BadParameterException {
		
		if(entity.getDateEntretien() == null) {
    		throw new BadParameterException("La date de l'entretien doit être indiquée");
    	}
    	
    	if(entity.getEtat() == null || entity.getEtat() == "") {
    		throw new BadParameterException("L'entretien ne peut avoir un état nul");
    	}
    	
		return em.merge(entity);
	}
	
	public  void delete (Entretien entity) {
		if(!em.contains(entity)) {
			em.merge(entity);
		}
		em.remove(entity);
	}

}
