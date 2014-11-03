package eu.telecom_bretagne.recrutement.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import eu.telecom_bretagne.recrutement.data.dao.CandidatMngt;
import eu.telecom_bretagne.recrutement.data.dao.CandidatureMngt;
import eu.telecom_bretagne.recrutement.data.model.Candidat;
import eu.telecom_bretagne.recrutement.data.model.Candidature;

/**
 * Session Bean implementation class ServiceAuthentification
 */
@Stateless
@LocalBean
public class ServiceCommon implements IServiceCommon {
	
	@EJB
	CandidatMngt candidatDAO;
	
	@EJB
	CandidatureMngt candidatureDAO;

    /**
     * Default constructor. 
     */
    public ServiceCommon() {
        // TODO Auto-generated constructor stub
    }
    
    public List<Candidat> getListCandidats() {
    	return candidatDAO.findAll();
    }
    
    public Candidat findCandidatById(int id) {
    	return candidatDAO.findById(id);
    }
    
    public List <Candidature> getListCandidatures() {
		return candidatureDAO.findAll();
    }
    
    public Candidature findCandidatureById(int id) {
		return candidatureDAO.findById(id);
    }
    
    

}
