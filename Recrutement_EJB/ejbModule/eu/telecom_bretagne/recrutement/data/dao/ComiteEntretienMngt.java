
package eu.telecom_bretagne.recrutement.data.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import eu.telecom_bretagne.recrutement.data.model.ComiteEntretien;
import eu.telecom_bretagne.recrutement.data.model.Utilisateur;
import eu.telecom_bretagne.recrutement.exception.BadParameterException;

/**
 * Session Bean implementation class ComiteEntretienDAO
 */
@Stateless
@LocalBean
public class ComiteEntretienMngt implements DAO<ComiteEntretien> {
	@PersistenceContext
	EntityManager em;

	public ComiteEntretien create (ComiteEntretien entity) throws BadParameterException { 	
		em.persist(entity);
		return em.merge(entity);		
	}
	
	public ComiteEntretien findById (int id) {
		return em.find(ComiteEntretien.class, id);
	}
	
	public List <ComiteEntretien> findAll() {
		Query query = em
				.createQuery("select comiteEntretien from ComiteEntretien comiteEntretien order by comiteEntretien.id");
		return query.getResultList();
		
	}
	
	public ComiteEntretien update (ComiteEntretien entity) throws BadParameterException {  	
		return em.merge(entity);
	}
	
	public  void delete (ComiteEntretien entity) {
		if(!em.contains(entity)) {
			em.merge(entity);
		}
		em.remove(entity);
	}

}
