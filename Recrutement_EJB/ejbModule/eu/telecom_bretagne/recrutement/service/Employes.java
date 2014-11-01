package eu.telecom_bretagne.recrutement.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import eu.telecom_bretagne.recrutement.data.dao.CandidatureMngt;
import eu.telecom_bretagne.recrutement.data.model.Candidature;

/**
 * Session Bean implementation class Employes
 */
@Stateless
@LocalBean
public class Employes implements IDirecteur, IServiceRH, IComiteEntretien {
	
	@EJB
	CandidatureMngt candidatureDAO;

    /**
     * Default constructor. 
     */
    public Employes() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public List <Candidature> getCandidatures() {
    		return candidatureDAO.findAll();
    }

}
