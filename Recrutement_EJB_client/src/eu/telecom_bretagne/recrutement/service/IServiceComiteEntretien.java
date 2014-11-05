package eu.telecom_bretagne.recrutement.service;

import java.util.List;

import javax.ejb.Remote;

import eu.telecom_bretagne.recrutement.data.model.Entretien;
import eu.telecom_bretagne.recrutement.data.model.Utilisateur;
import eu.telecom_bretagne.recrutement.data.model.Vote;
import eu.telecom_bretagne.recrutement.exception.BadParameterException;
import eu.telecom_bretagne.recrutement.exception.BadStateException;
import eu.telecom_bretagne.recrutement.exception.InvalidUserException;

@Remote
public interface IServiceComiteEntretien {
	
	public static final String JNDI_NAME = "java:global/Recrutement_EAR/Recrutement_EJB/ServiceEmployes!eu.telecom_bretagne.recrutement.service.IServiceComiteEntretien";
	
	public Entretien valideEntretien(Utilisateur user, Entretien entretien) throws InvalidUserException,BadStateException,BadParameterException;
	
	public Vote donnerAvis(Entretien entretien, int note, String commentaire ) throws BadStateException,BadParameterException;

}
