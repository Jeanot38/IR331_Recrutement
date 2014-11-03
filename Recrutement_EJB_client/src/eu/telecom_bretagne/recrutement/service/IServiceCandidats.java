package eu.telecom_bretagne.recrutement.service;

import javax.ejb.Remote;

import eu.telecom_bretagne.recrutement.data.model.Candidat;
import eu.telecom_bretagne.recrutement.data.model.Candidature;
import eu.telecom_bretagne.recrutement.data.model.Entretien;
import eu.telecom_bretagne.recrutement.exception.BadParameterException;
import eu.telecom_bretagne.recrutement.exception.BadStateException;
import eu.telecom_bretagne.recrutement.exception.InvalidUserException;

@Remote
public interface IServiceCandidats {
	
	public static final String JNDI_NAME = "java:global/Recrutement_EAR/Recrutement_EJB/ServiceCandidats!eu.telecom_bretagne.recrutement.service.IServiceCandidats";
	
	public Candidature creerCandidature(Candidat candidat, String cv, String lettreMotivation) throws InvalidUserException,BadParameterException;
	
	public Entretien valideEntretien(Candidature candidat, Entretien entretien) throws InvalidUserException,BadStateException;
	
	public Candidature annuleCandidature(Candidat candidat, Candidature candidature) throws InvalidUserException, BadStateException, BadParameterException;

}
