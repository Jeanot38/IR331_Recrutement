
package eu.telecom_bretagne.recrutement.data.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import eu.telecom_bretagne.recrutement.data.model.Utilisateur;
import eu.telecom_bretagne.recrutement.exception.BadParameterException;

/**
 * Session Bean implementation class UtilisateurDAO
 */
@Stateless
@LocalBean
public class UtilisateurMngt implements DAO<Utilisateur> {
	@PersistenceContext
	EntityManager em;

	public Utilisateur create (Utilisateur entity) throws BadParameterException { 	
		
		if(entity.getNom() == null || entity.getNom().equals("")) {
    		throw new BadParameterException("Le nom de l'utilisateur doit être renseigné");
    	}
    	
    	if(entity.getPrenom() == null || entity.getPrenom().equals("")) {
    		throw new BadParameterException("Le prenom de l'utilisateur doit être renseigné");
    	}
    	
    	if(entity.getLogin() == null || entity.getLogin().equals("")) {
    		throw new BadParameterException("Le login de l'utilisateur doit être renseigné");
    	}
    	
    	if(entity.getPassword() == null || entity.getPassword().equals("")) {
    		throw new BadParameterException("Le password doit être renseigné");
    	}
    	
		em.persist(entity);
		return em.merge(entity);		
	}
	
	public Utilisateur findById (int id) {
		return em.find(Utilisateur.class, id);
	}
	
	public List <Utilisateur> findAll() {
		Query query = em
				.createQuery("select utilisateur from Utilisateur utilisateur order by utilisateur.id");
		return query.getResultList();
		
	}
	
	public Utilisateur update (Utilisateur entity) throws BadParameterException {  	
		
		if(entity.getNom() == null || entity.getNom() == "") {
    		throw new BadParameterException("Le nom de l'utilisateur doit être renseigné");
    	}
    	
    	if(entity.getPrenom() == null || entity.getPrenom() == "") {
    		throw new BadParameterException("Le prenom de l'utilisateur doit être renseigné");
    	}
    	
    	if(entity.getLogin() == null || entity.getLogin() == "") {
    		throw new BadParameterException("Le login de l'utilisateur doit être renseigné");
    	}
    	
    	if(entity.getPassword() == null || entity.getPassword() == "") {
    		throw new BadParameterException("Le password doit être renseigné");
    	}
    	
		return em.merge(entity);
	}
	
	public  void delete (Utilisateur entity) {
		if(!em.contains(entity)) {
			em.merge(entity);
		}
		em.remove(entity);
	}

}
