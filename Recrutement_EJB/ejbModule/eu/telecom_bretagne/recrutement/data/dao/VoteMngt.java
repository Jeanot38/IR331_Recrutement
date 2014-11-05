package eu.telecom_bretagne.recrutement.data.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import eu.telecom_bretagne.recrutement.data.model.Utilisateur;
import eu.telecom_bretagne.recrutement.data.model.Vote;
import eu.telecom_bretagne.recrutement.exception.BadParameterException;

/**
 * Session Bean implementation class VoteDAO
 */
@Stateless
@LocalBean
public class VoteMngt implements DAO<Vote> {
	@PersistenceContext
	EntityManager em;

	public Vote create (Vote entity) throws BadParameterException { 	
		em.persist(entity);
		return em.merge(entity);		
	}
	
	public Vote findById (int id) {
		return em.find(Vote.class, id);
	}
	
	public List <Vote> findAll() {
		Query query = em
				.createQuery("select vote from Vote vote order by vote.id");
		return query.getResultList();
		
	}
	
	public Vote update (Vote entity) throws BadParameterException {  	
		return em.merge(entity);
	}
	
	public  void delete (Vote entity) {
		if(!em.contains(entity)) {
			em.merge(entity);
		}
		em.remove(entity);
	}

}
