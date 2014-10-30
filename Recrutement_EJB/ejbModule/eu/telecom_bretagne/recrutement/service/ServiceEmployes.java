package eu.telecom_bretagne.recrutement.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class ServiceEmployes
 */
@Stateless
@LocalBean
public class ServiceEmployes implements IServiceRH,IDirecteur,IComiteEntretien {

    /**
     * Default constructor. 
     */
    public ServiceEmployes() {
        // TODO Auto-generated constructor stub
    }

}
