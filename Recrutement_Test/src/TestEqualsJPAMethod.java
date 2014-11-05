import static org.junit.Assert.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.telecom_bretagne.recrutement.data.model.Candidat;
import eu.telecom_bretagne.recrutement.data.model.Utilisateur;
import eu.telecom_bretagne.recrutement.exception.InvalidUserException;
import eu.telecom_bretagne.recrutement.service.IServiceCandidats;
import eu.telecom_bretagne.recrutement.service.IServiceCommon;


public class TestEqualsJPAMethod {
	
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
	
	
	@Test
	public void testUtilisateur() {
		Utilisateur user1 = new Utilisateur();
		Utilisateur user2 = new Utilisateur();
		
		user1.setId(1);
		user1.setLogin("loginTest");
		user1.setNom("nomTest");
		user1.setPassword("passwordTest");
		user1.setPrenom("prenomTest");
		
		user2.setId(1);
		user2.setLogin("loginTest");
		user2.setNom("nomTest");
		user2.setPassword("passwordTest");
		user2.setPrenom("prenomTest");
		
		assertEquals(user1, user2);
	}

	/*@Test
	public void testCandidat() {
		IServiceCandidats serviceCandidats = this.getServiceCandidat();
		
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
		
		Candidat candidat = new Candidat();
			
		Candidat candidat1 = serviceCommon.findCandidatById(1);
		Candidat candidat2 = serviceCommon.findCandidatById(1);
		Candidat candidat3 = serviceCommon.findCandidatById(1);
		
		System.out.println("Candidat 1 : "+candidat1);
		System.out.println("Candidat 2 : "+candidat2);
		System.out.println("Est-ce égal ? "+(candidat2.equals(candidat3)));
		
	}*/
	
	@Test
	public void testCandidat() {
		
		Candidat candidat1 = new Candidat();
		Candidat candidat2 = new Candidat();
		
		candidat1.setAddresse("Adresse");
		candidat1.setId(1);
		candidat1.setTelephone("Tel");
		
		candidat2.setAddresse("Adresse");
		candidat2.setId(1);
		candidat2.setTelephone("Tel");
		
		assertEquals(candidat1, candidat2);
	}

}
