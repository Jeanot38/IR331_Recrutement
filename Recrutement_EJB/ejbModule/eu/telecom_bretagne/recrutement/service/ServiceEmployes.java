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
public class ServiceEmployes implements IServiceDirecteur, IServiceRH, IServiceComiteEntretien {
	
	@EJB
	CandidatureMngt candidatureDAO;

    /**
     * Default constructor. 
     */
    public ServiceEmployes() {
        // TODO Auto-generated constructor stub
    }

}
