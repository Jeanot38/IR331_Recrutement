package eu.telecom_bretagne.recrutement.data.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import eu.telecom_bretagne.recrutement.data.model.Message;

/**
 * Session Bean implementation class MessageDAO
 */
@Stateless
@LocalBean
public class MessageMngt implements DAO<Message> {
	
	@PersistenceContext
	EntityManager em;

	public Message create (Message entity) {
		em.persist(entity);
		return entity;
	}
	
	public Message findById (int id) {
		return em.find(Message.class, id);
	}
	
	public List <Message> findAll() {
		Query query = em
				.createQuery("select message from Message message order by message.id");
		return query.getResultList();
	}
	
	public Message update (Message entity) {
		return em.merge(entity);
	}
	
	public  void delete (Message entity) {
		if(!em.contains(entity)) {
			em.merge(entity);
		}
		em.remove(entity);
	}

}
