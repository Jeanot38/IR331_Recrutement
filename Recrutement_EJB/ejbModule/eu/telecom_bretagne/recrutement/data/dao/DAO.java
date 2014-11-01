package eu.telecom_bretagne.recrutement.data.dao;

import java.util.List;

public interface DAO <T> {
	
	public abstract T create (T entity);
	
	public abstract T findById (int id);
	
	public abstract List<T> findAll ();
	
	public abstract T update (T entity);
	
	public abstract void delete (T entity);
	
}
