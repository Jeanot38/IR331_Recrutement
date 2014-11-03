import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.telecom_bretagne.recrutement.data.model.Candidat;
import eu.telecom_bretagne.recrutement.data.model.Utilisateur;


public class TestEqualsJPAMethod {
	
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

	@Test
	public void testCandidat() {
		Utilisateur user1 = new Utilisateur();
		Utilisateur user2 = new Utilisateur();
		Candidat candidat1 = new Candidat();
		Candidat candidat2 = new Candidat();
		
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
		
		candidat1.setAddresse("adresseTest");
		candidat1.setTelephone("telTest");
		
		candidat2.setAddresse("adresseTest");
		candidat2.setTelephone("telTest");
		
		candidat1.setUtilisateur(user1);
		candidat2.setUtilisateur(user2);
		
		assertEquals(candidat1, candidat2);
	}

}
