package eu.telecom_bretagne.recrutement.service;

import javax.ejb.Remote;

@Remote
public interface ICandidats {
	
	public static final String JNDI_NAME = "java:global/Recrutement_EAR/Recrutement_EJB/Candidats!eu.telecom_bretagne.recrutement.service.ICandidats";

}
