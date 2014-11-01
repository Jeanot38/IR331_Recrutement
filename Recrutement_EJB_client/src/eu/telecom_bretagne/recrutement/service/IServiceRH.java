package eu.telecom_bretagne.recrutement.service;

import java.util.List;

import javax.ejb.Remote;

import eu.telecom_bretagne.recrutement.data.model.Candidature;

@Remote
public interface IServiceRH {
	
	public static final String JNDI_NAME = "java:global/Recrutement_EAR/Recrutement_EJB/Employes!eu.telecom_bretagne.recrutement.service.IServiceRH";
	
	public List <Candidature> getCandidatures();

}
