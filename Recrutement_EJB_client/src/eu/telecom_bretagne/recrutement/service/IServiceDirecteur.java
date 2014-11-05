package eu.telecom_bretagne.recrutement.service;

import javax.ejb.Remote;

import eu.telecom_bretagne.recrutement.data.model.Candidature;
import eu.telecom_bretagne.recrutement.exception.BadParameterException;
import eu.telecom_bretagne.recrutement.exception.BadStateException;

@Remote
public interface IServiceDirecteur {
	
	public static final String JNDI_NAME = "java:global/Recrutement_EAR/Recrutement_EJB/ServiceEmployes!eu.telecom_bretagne.recrutement.service.IServiceDirecteur";
	
	public Candidature validerCandidature(Candidature candidature, String resultat) throws BadStateException, BadParameterException;

}
