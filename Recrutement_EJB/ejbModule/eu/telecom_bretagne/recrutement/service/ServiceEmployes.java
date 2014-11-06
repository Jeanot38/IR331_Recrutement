package eu.telecom_bretagne.recrutement.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import eu.telecom_bretagne.recrutement.data.dao.ComiteEntretienMngt;
import eu.telecom_bretagne.recrutement.data.dao.EntretienMngt;
import eu.telecom_bretagne.recrutement.data.dao.VoteMngt;
import eu.telecom_bretagne.recrutement.data.dao.MessageMngt;
import eu.telecom_bretagne.recrutement.data.dao.CandidatureMngt;
import eu.telecom_bretagne.recrutement.data.dao.UtilisateurMngt;
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
	
	@EJB
	UtilisateurMngt utilisateurDAO;
	
	@EJB
	ComiteEntretienMngt comiteEntretienDAO;
	
	@EJB
	VoteMngt voteDAO;

    /**
     * Default constructor. 
     */
    public ServiceEmployes() {
        // TODO Auto-generated constructor stub
    }
    
    public Candidature etudierCandidature(Candidature candidature, String etat) throws BadParameterException,BadStateException{
    	if(candidature==null){
    		throw new BadParameterException("La candidature est vide.");
    	}
    	if (!candidature.getEtat().equalsIgnoreCase("cree")){
    		throw new BadStateException("La candidature n'est pas créée.");
    	}
    	
    	if (etat==null){
    		throw new BadParameterException("L'état de la candidature n'est pas renseigné.");
    	}
    	if (candidature.getCandidat()==null){
    		throw new BadParameterException("Aucun candidat renseigné pour cette candidature.");
    	}
    	
	    	switch (etat) {
	    	
	    	case "valide" :
	    		candidature.setEtat("valide");
	    		break;
	    		
	    	case "refuse" :
	    		candidature.setEtat("refuse");
	    		break;
	    		
	    	default :
	    		throw new BadStateException("Etat non valide.");
	     	}
	    	return candidatureDAO.update(candidature);
    }
    
    public Message informerCandidat(Candidat candidat, String sujet, String contenu ) throws BadParameterException {  	
    	if(candidat==null){
    		throw new BadParameterException("Aucun candidat n'est renseigné.");
    	}
    	Message msg = new Message();
    	Date date = new Date();
    	msg.setCandidat(candidat);
		msg.setDateCreation(new Timestamp(date.getTime()));
		msg.setSujet(sujet);
    	msg.setContenu(contenu);
    	return messageDAO.create(msg);
    }
    
    public Entretien proposerDateEntretien(Candidature candidature, List <Utilisateur> users, Date dateEntretien) throws BadStateException, BadParameterException{
    	if (candidature==null){
    		throw new BadParameterException("Erreur, la candidature n'est pas remplie.");
    	}
    	if(users==null){
    		throw new BadParameterException("La liste d'utilisateur est vide.");
    	}
    	if(!candidature.getEtat().equalsIgnoreCase("valide")){
    		throw new BadStateException("La candidature n'est pas validée, impossible de proposer une date.");
    	}
		if(users.size()==0){
			throw new BadParameterException("Aucune personne disponible pour faire partie du comité.");
		}
		for (Utilisateur user:users){
			if (user.getCandidat()!=null){
				throw new BadParameterException("Un candidat ne peut pas proposer de date d'entretien.");
			}
		}
		if((dateEntretien==null)||(dateEntretien!=null && dateEntretien.getTime()<new Date().getTime())){
			throw new BadParameterException("Merci de saisir une date valide.");
		}
		
    	Entretien ent = new Entretien();
    	ComiteEntretien com = new ComiteEntretien();
    	com.setUtilisateurs(users);
    	for (Utilisateur utilisateur : users) {
			utilisateur.getComiteEntretiens().add(com);
			utilisateurDAO.update(utilisateur);
    	}
    	com = comiteEntretienDAO.create(com);
    	ent.setCandidature(candidature);
    	ent.setComiteEntretien(com);
    	ent.setDateEntretien(new Timestamp(dateEntretien.getTime()));
    	ent.setEtat("cree");
    	return entretienDAO.create(ent);
    	//return ent;
    	}
    
    public Entretien valideEntretien(Utilisateur user, Entretien entretien) throws InvalidUserException,BadStateException,BadParameterException {
    	if(user==null){
    		throw new BadParameterException("Utilisateur mal renseigné.");
    	}
    	if(entretien==null){
    		throw new BadParameterException("Audun entretien n'est renseigné.");
    	}    	
    	if(!entretien.getComiteEntretien().getUtilisateurs().contains(user)) {
    		throw new InvalidUserException("Vous ne pouvez pas valider car vous ne faites pas parti du comité de cet entretien.");
    	}
    	/*
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
    
    public Vote donnerAvis(Entretien entretien, int note, String commentaire ) throws BadStateException,BadParameterException{
    	if(entretien==null){
    		throw new BadParameterException("Aucun entretien n'est sélectionné.");
    	}
    	if(commentaire==null){
    		throw new BadParameterException("Merci d'inscrire votre commentaire.");
    	}
    	if (note<0 || note>20){
    		throw new BadParameterException("Veuillez entrer une note comprise entre 0 et 20.");
    	}
    	if (!(entretien.getCandidature().getEtat().equalsIgnoreCase("valide")) && (entretien.getEtat().equalsIgnoreCase("accepte"))){
    		throw new BadStateException("Cet entretien n'est pas valide.");
    	}
    	Vote vote = new Vote();
		vote.setEntretien(entretien);
		
    	vote.setNote(note);
    	vote.setCommentaires(commentaire);
    	return voteDAO.update(vote);  	
    }
    
    public Candidature validerCandidature(Candidature candidature, String resultat) throws BadStateException, BadParameterException{
    	if(candidature==null){
    		throw new BadParameterException("La candidature est vide.");
    	}
    	if(resultat==null){
    		throw new BadParameterException("Merci de choisir sur la candidature est acceptee ou refusee.");
    	}
    	if(candidature.getEntretiens().size()<=0){
    		throw new BadParameterException("Ancun entretien n'est renseigne pour cette candidature.");
    	}
    	if (!candidature.getEtat().equalsIgnoreCase("valide")){
    		throw new BadStateException("Cette candidature n'est pas valide.");
    	}
    		switch (resultat){
    		case "accepte" :
    			candidature.setEtat("accepte");
    			break;
    		case "refuse" :
    			candidature.setEtat("refuse");
    			break;
    		default :
    			throw new BadStateException("Le resultat n'a pas le bon format.");
    		}
    		return candidatureDAO.update(candidature);
    }
}
