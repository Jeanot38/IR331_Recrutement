package eu.telecom_bretagne.recrutement.data.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import eu.telecom_bretagne.recrutement.data.model.Vote;

/**
 * Session Bean implementation class VoteDAO
 */
@Stateless
@LocalBean
public class VoteMngt implements DAO<Vote> {

	public Vote create (Vote entity) {
		return new Vote();
	}
	
	public Vote findById (int id) {
		return new Vote();
	}
	
	public Vote update (Vote entity) {
		return new Vote();
	}
	
	public  void delete (Vote entity) {
		
	}

}