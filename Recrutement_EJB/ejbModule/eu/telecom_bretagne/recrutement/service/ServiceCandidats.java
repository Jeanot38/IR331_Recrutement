package eu.telecom_bretagne.recrutement.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import eu.telecom_bretagne.recrutement.data.dao.CandidatMngt;
import eu.telecom_bretagne.recrutement.data.dao.CandidatureMngt;
import eu.telecom_bretagne.recrutement.data.dao.EntretienMngt;
import eu.telecom_bretagne.recrutement.data.model.Candidat;
import eu.telecom_bretagne.recrutement.data.model.Candidature;
import eu.telecom_bretagne.recrutement.data.model.Entretien;
import eu.telecom_bretagne.recrutement.exception.*;

/**
 * Session Bean implementation class Candidats
 */
@Stateless
@LocalBean
public class ServiceCandidats implements IServiceCandidats {
	
	@EJB
	CandidatMngt candidatDAO;
	
	@EJB
	CandidatureMngt candidatureDAO;
	
	@EJB
	EntretienMngt entretienDAO;

    /**
     * Default constructor. 
     */
    public ServiceCandidats() {
        // TODO Auto-generated constructor stub
    }
    
    public Candidature creerCandidature(Candidat candidat, String cv, String lettreMotivation) throws InvalidUserException,BadParameterException{
    	
    	List<Candidat> listCandidats = candidatDAO.findAll();
    	Date date= new Date();
    	
    	if(candidat == null) {
    		throw new InvalidUserException("Veuillez vous connecter à l'application avant de créer une candidature");
    	}
    	
    	if(!listCandidats.contains(candidat)) {
    		throw new InvalidUserException("Le candidat souhaitant déposé la candidature n'est pas dans la base de donnée");
    	}
    	
    	Candidature candidature = new Candidature();
    	
    	candidature.setCandidat(candidat);
    	candidature.setCv(cv);
    	candidature.setLettreMotivation(lettreMotivation);
    	candidature.setDateCreation(new Timestamp(date.getTime()));
    	
    	return candidatureDAO.create(candidature);
    	
    }
    
    public Entretien valideEntretien(Candidat candidat, Entretien entretien) throws InvalidUserException,BadParameterException,BadStateException {
    	
    	List<Entretien> listEntretiens = entretienDAO.findAll();
    	
    	if(entretien == null || ! listEntretiens.contains(entretien)) {
    		throw new BadParameterException("L'entretien sélectionné n'est pas valide");
    	}
    	
    	if(candidat == null) {
    		throw new InvalidUserException("Vous ne pouvez valider cet entretien car vous n'êtes pas connecté");
    	}
    	
    	if(!entretien.getCandidature().getCandidat().equals(candidat)) {
    		throw new InvalidUserException("Vous ne pouvez valider cet entretien car celui-ci ne correspond pas à votre candidature");
    	}

    	if(!entretien.getCandidature().getEtat().equals("valide")) {
    		throw new BadStateException("Votre candidature doit avoir été validé pour valider l'entretien");
    	}
    	
    	if(entretien.getEtat().equals("valide_candidat") || entretien.getEtat().equals("valide")) {
    		throw new BadStateException("Vous avez déjà validé cet entretien");
    	}
    	
    	String etat = "";
    	if(entretien.getEtat().equals("valide_comite_entretien")) {
    		etat = "valide";
    	} else {
    		etat = "valide_candidat";
    	}
    	
    	entretien.setEtat(etat);
    	
    	return entretienDAO.update(entretien);
    }
    
    public Candidature annuleCandidature(Candidat candidat, Candidature candidature) throws InvalidUserException, BadStateException, BadParameterException {
    	
    	List<Candidature> listCandidatures = candidatureDAO.findAll();
    	
    	if(candidature == null || ! listCandidatures.contains(candidature)) {
    		throw new BadParameterException("La candidature sélectionné n'est pas valide");
    	}
    	
    	if(candidat == null) {
    		throw new InvalidUserException("Vous ne pouvez valider cet entretien car vous n'êtes pas connecté");
    	}
    	
    	if(!candidature.getCandidat().equals(candidat)) {
    		throw new InvalidUserException("Vous n'êtes pas autoriser à annuler une candidature qui ne vous appartient pas");
    	}
    	
    	if(candidature.getEtat() == "" || candidature.getEtat() == "accepte" || candidature.getEtat() == "refuse") {
    		throw new BadStateException("L'état actuel de votre candidature ne vous permet pas de l'annuler");
    	}
    	
    	candidature.setEtat("annule");
    	
    	return candidatureDAO.update(candidature);
    } 

}
