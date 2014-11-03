package eu.telecom_bretagne.recrutement.service;

import javax.ejb.Remote;

@Remote
public interface IServiceComiteEntretien {
	
	public static final String JNDI_NAME = "java:global/Recrutement_EAR/Recrutement_EJB/ServiceEmployes!eu.telecom_bretagne.recrutement.service.IServiceComiteEntretien";

}
