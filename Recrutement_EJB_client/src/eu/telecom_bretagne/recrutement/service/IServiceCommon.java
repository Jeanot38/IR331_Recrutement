package eu.telecom_bretagne.recrutement.service;

import java.util.List;

import javax.ejb.Remote;

import eu.telecom_bretagne.recrutement.data.model.Candidat;
import eu.telecom_bretagne.recrutement.data.model.Candidature;

@Remote
public interface IServiceCommon {
	
	public static final String JNDI_NAME = "java:global/Recrutement_EAR/Recrutement_EJB/ServiceCommon!eu.telecom_bretagne.recrutement.service.IServiceCommon";
	
	public List<Candidat> getListCandidats();
	
	public Candidat findCandidatById(int id);
	
	public List <Candidature> getListCandidatures();
	
	public Candidature findCandidatureById(int id);

}
