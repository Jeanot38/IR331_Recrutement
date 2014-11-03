package eu.telecom_bretagne.recrutement.data.dao;

import java.util.List;

import eu.telecom_bretagne.recrutement.exception.BadParameterException;

public interface DAO <T> {
	
	public abstract T create (T entity) throws BadParameterException;
	
	public abstract T findById (int id);
	
	public abstract List<T> findAll ();
	
	public abstract T update (T entity) throws BadParameterException;
	
	public abstract void delete (T entity);
	
}
