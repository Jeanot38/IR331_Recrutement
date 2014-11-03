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

/**
 * Session Bean implementation class EntretienDAO
 */
@Stateless
@LocalBean
public class EntretienMngt implements DAO<Entretien> {
	
	@PersistenceContext
	EntityManager em;

	public Entretien create (Entretien entity) {
		em.persist(entity);
		return entity;
	}
	
	public Entretien findById (int id) {
		return em.find(Entretien.class, id);
	}
	
	public List <Entretien> findAll() {
		Query query = em
				.createQuery("select entretien from Entretien entretien order by entretien.id");
		return query.getResultList();
	}
	
	public Entretien update (Entretien entity) {
		return em.merge(entity);
	}
	
	public  void delete (Entretien entity) {
		if(!em.contains(entity)) {
			em.merge(entity);
		}
		em.remove(entity);
	}

}
