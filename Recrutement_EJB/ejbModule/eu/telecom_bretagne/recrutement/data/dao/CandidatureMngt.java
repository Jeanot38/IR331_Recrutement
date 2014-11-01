package eu.telecom_bretagne.recrutement.data.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import eu.telecom_bretagne.recrutement.data.model.Candidature;

/**
 * Session Bean implementation class CandidatureDAO
 */
@Stateless
@LocalBean
public class CandidatureMngt implements DAO<Candidature> {
	@PersistenceContext
	EntityManager em;

	public Candidature create (Candidature entity) {
		em.persist(entity);
		return entity;		
	}
	
	public Candidature findById (int id) {
		return em.find(Candidature.class, id);
	}
	
	public List <Candidature> findAll() {
		Query query = em
				.createQuery("select candidature from Candidature candidature order by candidature.id");
		return query.getResultList();
		
	}
	
	public Candidature update (Candidature entity) {
		return em.merge(entity);
	}
	
	public  void delete (Candidature entity) {
		if(!em.contains(entity)) {
			em.merge(entity);
		}
		em.remove(entity);
	}

}
