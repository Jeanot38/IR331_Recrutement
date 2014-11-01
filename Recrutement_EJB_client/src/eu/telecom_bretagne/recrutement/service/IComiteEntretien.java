package eu.telecom_bretagne.recrutement.service;

import javax.ejb.Remote;

@Remote
public interface IComiteEntretien {
	
	public static final String JNDI_NAME = "java:global/Recrutement_EAR/Recrutement_EJB/Employes!eu.telecom_bretagne.recrutement.service.IComiteEntretien";

}
