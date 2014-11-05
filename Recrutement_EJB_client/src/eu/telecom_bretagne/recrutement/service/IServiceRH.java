package eu.telecom_bretagne.recrutement.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import eu.telecom_bretagne.recrutement.data.model.Candidat;
import eu.telecom_bretagne.recrutement.data.model.Candidature;
import eu.telecom_bretagne.recrutement.data.model.Entretien;
import eu.telecom_bretagne.recrutement.data.model.Message;
import eu.telecom_bretagne.recrutement.data.model.Utilisateur;
import eu.telecom_bretagne.recrutement.exception.BadParameterException;
import eu.telecom_bretagne.recrutement.exception.BadStateException;

@Remote
public interface IServiceRH {
	
	public static final String JNDI_NAME = "java:global/Recrutement_EAR/Recrutement_EJB/ServiceEmployes!eu.telecom_bretagne.recrutement.service.IServiceRH";
	
	public Candidature etudierCandidature(Candidature candidature, String etat) throws BadParameterException,BadStateException;
	
	public Message informerCandidat(Candidat candidat, String sujet, String contenu ) throws BadParameterException;
	
	public Entretien proposerDateEntretien(Candidature candidature, List <Utilisateur> users, Date dateEntretien) throws BadStateException, BadParameterException;
	
}
