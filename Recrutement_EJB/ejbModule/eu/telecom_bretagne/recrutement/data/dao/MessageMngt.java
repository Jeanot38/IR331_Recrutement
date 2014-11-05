package eu.telecom_bretagne.recrutement.data.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import eu.telecom_bretagne.recrutement.data.model.Message;
import eu.telecom_bretagne.recrutement.exception.BadParameterException;

/**
 * Session Bean implementation class MessageDAO
 */
@Stateless
@LocalBean
public class MessageMngt implements DAO<Message> {
	
	@PersistenceContext
	EntityManager em;

	public Message create (Message entity) throws BadParameterException {
		
		if(entity.getDateCreation() == null) {
			Date date= new Date();
    		entity.setDateCreation(new Timestamp(date.getTime()));
    	}

		if(entity.getSujet() == null || entity.getSujet().equals("")) {
    		throw new BadParameterException("Le sujet du message doit être renseigné");
    	}
    	
    	if(entity.getContenu() == null || entity.getContenu().equals("")) {
    		throw new BadParameterException("Le contenu du message doit être renseigné");
    	}
    	
		em.persist(entity);
		return em.merge(entity);
	}
	
	public Message findById (int id) {
		return em.find(Message.class, id);
	}
	
	public List <Message> findAll() {
		Query query = em
				.createQuery("select message from Message message order by message.id");
		return query.getResultList();
	}
	
	public Message update (Message entity) throws BadParameterException {
		
		if(entity.getDateCreation() == null) {
			throw new BadParameterException("La date de création du message ne peut être nulle lors de sa modification");
    	}
    	
    	if(entity.getSujet() == null || entity.getSujet() == "") {
    		throw new BadParameterException("Le sujet du message doit être renseigné");
    	}
    	
    	if(entity.getContenu() == null || entity.getContenu() == "") {
    		throw new BadParameterException("Le contenu du message doit être renseigné");
    	}
    	
		return em.merge(entity);
	}
	
	public  void delete (Message entity) {
		if(!em.contains(entity)) {
			em.merge(entity);
		}
		em.remove(entity);
	}

}
