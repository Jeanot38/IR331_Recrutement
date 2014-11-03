package eu.telecom_bretagne.recrutement.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import eu.telecom_bretagne.recrutement.data.dao.EntretienMngt;
import eu.telecom_bretagne.recrutement.data.dao.MessageMngt;
import eu.telecom_bretagne.recrutement.data.dao.CandidatureMngt;
import eu.telecom_bretagne.recrutement.data.model.Candidature;
import eu.telecom_bretagne.recrutement.data.model.Candidat;
import eu.telecom_bretagne.recrutement.data.model.ComiteEntretien;
import eu.telecom_bretagne.recrutement.data.model.Entretien;
import eu.telecom_bretagne.recrutement.data.model.Message;
import eu.telecom_bretagne.recrutement.data.model.Utilisateur;
import eu.telecom_bretagne.recrutement.exception.BadParameterException;

/**
 * Session Bean implementation class Employes
 */
@Stateless
@LocalBean
public class ServiceEmployes implements IServiceDirecteur, IServiceRH, IServiceComiteEntretien {
	
	@EJB
	CandidatureMngt candidatureDAO;
	
	@EJB
	MessageMngt messageDAO;
	
	@EJB
	EntretienMngt entretienDAO;

    /**
     * Default constructor. 
     */
    public ServiceEmployes() {
        // TODO Auto-generated constructor stub
    }
    
    public Candidature etudierCandidature(Candidature candidature, String etat) throws BadParameterException{
    	switch (etat) {
    	
    	case "valide" :
    		candidature.setEtat("valide");
    		break;
    		
    	case "refuse" :
    		candidature.setEtat("refuse");
    		break;
    		
    	default :
    		System.out.println("Etat de candidature invalide.");
     	}
    	return candidatureDAO.update(candidature);
    }
    
    public Message informerCandidat(Candidat candidat, String sujet, String contenu ){
    	Message msg = new Message();
    	Date date = new Date();
    	msg.setCandidat(candidat);
		msg.setDateCreation(new Timestamp(date.getTime()));
		msg.setSujet(sujet);
    	msg.setContenu(contenu);
    	return messageDAO.create(msg);
    }
    
    public Entretien proposerDateEntretien(Candidature candidature, List <Utilisateur> users, Date dateEntretien){
    	Entretien ent = new Entretien();
    	ComiteEntretien com = new ComiteEntretien();
    	com.setUtilisateurs(users);
    	/*for (Utilisateur utilisateur : users) {
			utilisateur.
		}*/
    	ent.setCandidature(candidature);
    	ent.setComiteEntretien(com);
    	ent.setDateEntretien(new Timestamp(dateEntretien.getTime()));
    	//ent.setEtat("cree");
    	return entretienDAO.create(ent);
    }
}
