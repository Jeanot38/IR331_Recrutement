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
import eu.telecom_bretagne.recrutement.data.model.Vote;
import eu.telecom_bretagne.recrutement.exception.BadParameterException;
import eu.telecom_bretagne.recrutement.exception.BadStateException;
import eu.telecom_bretagne.recrutement.exception.InvalidUserException;

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
    
    public Entretien valideEntretien(List <Utilisateur> users, Entretien entretien) throws InvalidUserException,BadStateException {
    	
    	/*
    	if(users == null) {
    		throw new InvalidUserException("Vous ne pouvez valider cet entretien car vous n'êtes pas connecté");
    	}
    	
    	if(!entretien.getCandidature().getCandidat().equals(candidat)) {
    		throw new InvalidUserException("Vous ne pouvez valider cet entretien car celui-ci ne correspond pas à votre candidature");
    	}
    	*/
    	
    	if(entretien.getEtat().equals("valide_comite_entretien") || entretien.getEtat().equals("valide")) {
    		throw new BadStateException("Vous avez déjà validé cet entretien");
    	}
    	
    	String etat = "";
    	if(entretien.getEtat().equals("valide_candidat")) {
    		etat = "valide";
    	}
    	else {
    		etat = "valide_comite_entretien";
    	}
    	
    	entretien.setEtat(etat);
    	
    	return entretienDAO.update(entretien);
    }
    
    public Vote donnerAvis(Entretien entretien, int note, String commentaire ) throws BadStateException{
    	Vote vote = new Vote();
    	if ((entretien.getCandidature().getEtat()=="valide") && (entretien.getEtat()=="accepte")){
    		vote.setEntretien(entretien);
	    	vote.setNote(note);
	    	vote.setCommentaires(commentaire);
	    	return vote;
    	}
    	else{
    		throw new BadStateException("Cet entretien n'est pas valide.");
    	}  	
    }
    
    public Candidature validerCandidature(Candidature candidature, String resultat) throws BadStateException, BadParameterException{
    	if (candidature.getEtat()=="valide"){ //Ajouter une condition sur le vote reçu ? Un test...
    		switch (resultat){
    		case "accepte" :
    			candidature.setEtat("accepte");
    		case "refuse" :
    			candidature.setEtat("refuse");
    		default :
    			System.out.println("Etat de candidature invalide.");
    		}
    		return candidatureDAO.update(candidature);
    	}
    	else{
    		throw new BadStateException("Cette candidature n'est pas valide.");
    	}
    }
}
