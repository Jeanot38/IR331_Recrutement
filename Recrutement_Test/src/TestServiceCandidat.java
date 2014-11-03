import static org.junit.Assert.*;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.telecom_bretagne.recrutement.data.model.Candidat;
import eu.telecom_bretagne.recrutement.data.model.Candidature;
import eu.telecom_bretagne.recrutement.data.model.Utilisateur;
import eu.telecom_bretagne.recrutement.exception.BadParameterException;
import eu.telecom_bretagne.recrutement.exception.InvalidUserException;
import eu.telecom_bretagne.recrutement.service.IServiceCandidats;
import eu.telecom_bretagne.recrutement.service.IServiceCommon;


public class TestServiceCandidat {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	private IServiceCommon getServiceCommon() {
		InitialContext ctx;
		IServiceCommon serviceCommon = null;
		try {
			ctx = new InitialContext();
			serviceCommon = (IServiceCommon) ctx.lookup(IServiceCommon.JNDI_NAME);
		}
		catch (NamingException e)
		{
			// Unable to retrieve the context or the service
			e.printStackTrace();
			System.exit(-1);
		}
		return serviceCommon;
	}
	
	private IServiceCandidats getServiceCandidat() {
		InitialContext ctx;
		IServiceCandidats serviceCandidats = null;
		try {
			ctx = new InitialContext();
			serviceCandidats = (IServiceCandidats) ctx.lookup(IServiceCandidats.JNDI_NAME);
		}
		catch (NamingException e)
		{
			// Unable to retrieve the context or the service
			e.printStackTrace();
			System.exit(-1);
		}
		return serviceCandidats;
	}
	
	// Doit être exécuté avec des datas à l'intérieur
	
	@Test
	public void testCreerCandidatureCandidatNull(){
		
		IServiceCandidats serviceCandidats = this.getServiceCandidat();
		IServiceCommon serviceCommon = this.getServiceCommon();
		
		int nombreCandidature = serviceCommon.getListCandidatures().size();
		Candidat candidat = serviceCommon.findCandidatById(1);
		
		try {
			serviceCandidats.creerCandidature(null, "Voici mon CV", "Voici ma lettre de motivation");
		} catch (InvalidUserException e) {
			
		} catch (Exception e) {
			fail("InvalidUserException should be catch, "+e.getClass()+" is the real.");
		}
		
		assertEquals(nombreCandidature, serviceCommon.getListCandidatures().size());
		
	}
	
	@Test
	public void testCreerCandidatureCandidatNotInDB(){
		
		IServiceCandidats serviceCandidats = this.getServiceCandidat();
		IServiceCommon serviceCommon = this.getServiceCommon();
		
		int nombreCandidature = serviceCommon.getListCandidatures().size();
		Utilisateur user = new Utilisateur();
		Candidat candidat = new Candidat();
		candidat.setUtilisateur(user);
		candidat.getUtilisateur().setNom("Durand");
		candidat.getUtilisateur().setPrenom("Jean");
		
		try {
			serviceCandidats.creerCandidature(candidat, "Voici mon CV", "Voici ma lettre de motivation");
		} catch (InvalidUserException e) {
			
		} catch (Exception e) {
			fail("InvalidUserException should be catch, "+e.getClass()+" is the real.");
		}
		
		assertEquals(nombreCandidature, serviceCommon.getListCandidatures().size());
		
	}
	
	@Test
	public void testCreerCandidatureCVNull(){
		
		IServiceCandidats serviceCandidats = this.getServiceCandidat();
		IServiceCommon serviceCommon = this.getServiceCommon();
		
		int nombreCandidature = serviceCommon.getListCandidatures().size();
		Candidat candidat = serviceCommon.findCandidatById(1);
		
		try {
			serviceCandidats.creerCandidature(candidat, null, "Voici ma lettre de motivation");
		} catch (BadParameterException e) {
			
		} catch (Exception e) {
			fail("BadParameterException should be catch, "+e.getClass()+" is the real.");
		}
		
		assertEquals(nombreCandidature, serviceCommon.getListCandidatures().size());
		
	}
	
	@Test
	public void testCreerCandidatureLettreMotivationNull(){
		
		IServiceCandidats serviceCandidats = this.getServiceCandidat();
		IServiceCommon serviceCommon = this.getServiceCommon();
		
		int nombreCandidature = serviceCommon.getListCandidatures().size();
		Candidat candidat = serviceCommon.findCandidatById(1);
		
		try {
			serviceCandidats.creerCandidature(candidat, "Voici mon cv", null);
		} catch (BadParameterException e) {
			
		} catch (Exception e) {
			fail("BadParameterException should be catch, "+e.getClass()+" is the real.");
		}
		
		assertEquals(nombreCandidature, serviceCommon.getListCandidatures().size());
		
	}
	
	@Test
	public void testCreerCandidatureValide(){
		
		IServiceCandidats serviceCandidats = this.getServiceCandidat();
		IServiceCommon serviceCommon = this.getServiceCommon();
		
		int nombreCandidature = serviceCommon.getListCandidatures().size();
		Candidat candidat = serviceCommon.findCandidatById(1);
		
		try {
			serviceCandidats.creerCandidature(candidat, "Voici mon cv", "Voici ma lettre de motiv");
		} catch (Exception e) {
			fail("No exception should be catch, "+e.getClass()+" is the real.");
		}
		
		assertEquals(nombreCandidature+1, serviceCommon.getListCandidatures().size());
		
	}
	
	@Test
	public void testValideEntretienCandidatNull(){
		
		IServiceCandidats serviceCandidats = this.getServiceCandidat();
		IServiceCommon serviceCommon = this.getServiceCommon();
		
		Candidat candidat = serviceCommon.findCandidatById(1);
		
		List<Candidature> candidatures = candidat.getCandidatures();
		
		if(candidatures.isEmpty()) {
			fail("False");
		}
		
		Candidature candidature = candidatures.get(1);
		
		try {
			serviceCandidats.valideEntretien(null, candidature.getEntretiens().get(0));
		} catch (InvalidUserException e) {
			
		} catch (Exception e) {
			fail("InvalidUserException should be catch, "+e.getClass()+" is the real.");
		}
		
		assertEquals("", candidature.getEntretiens().get(0).getEtat());
		
	}

}
