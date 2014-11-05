import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eu.telecom_bretagne.recrutement.data.model.Candidat;
import eu.telecom_bretagne.recrutement.data.model.Candidature;
import eu.telecom_bretagne.recrutement.data.model.Entretien;
import eu.telecom_bretagne.recrutement.data.model.Utilisateur;
import eu.telecom_bretagne.recrutement.exception.BadParameterException;
import eu.telecom_bretagne.recrutement.exception.BadStateException;
import eu.telecom_bretagne.recrutement.exception.InvalidUserException;
import eu.telecom_bretagne.recrutement.service.IServiceCandidats;
import eu.telecom_bretagne.recrutement.service.IServiceCommon;


public class TestServiceCandidat {
	
	private Connection connectionBase;
	private Statement sql;
	private ResultSet rs;
	
	public TestServiceCandidat() throws Exception {
		try {
		      Class.forName("org.postgresql.Driver" );
		     } catch (ClassNotFoundException e) {
		        throw new Exception("Impossible de charger le pilote jdbc:odbc" );
		     }
		   
		     //connection a la base de donnÃ©es
		     try {
		        String DBurl = "jdbc:postgresql://localhost:54321/recrutement";
		        connectionBase = DriverManager.getConnection(DBurl, "postgres", "postgres" );
		     } catch (Exception e) {
		        throw new Exception("Connection Ã  la base de donnÃ©es impossible" );
	     }
	}
	
	private String readFile(String filename) {
        File f = new File(filename);
        try {
            byte[] bytes = Files.readAllBytes(f.toPath());
            return new String(bytes,"UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
	}
	
	public void loadDatabase() throws Exception {
	   
	     sql = connectionBase.createStatement();
	     String delete = "drop schema public cascade; create schema public;";
	     String structure = this.readFile(System.getProperty("user.dir")+"/src/structure.sql").replace("\n", "").replace("\t", " ").substring(1);
	     String datas = this.readFile(System.getProperty("user.dir")+"/src/datas.sql").replace("\n", "").replace("\t", " ");
	     
	   //creation et execution de la requete
	     try {
	    	 sql.executeUpdate(delete);
	    	 sql.executeUpdate(structure);
	    	 sql.executeUpdate(datas);
	     } catch (SQLException e) {
	    	 //if(!e.getMessage().equals("Aucun résultat retourné par la requête.")) {
	    		 e.printStackTrace();
		    	 throw new Exception("Anomalie lors de l'execution de la requÃªte" );
	    	 //}
	     } catch (Exception e) {
			System.out.println("Ca marche pas");
		}
	}
	
	private void checkResultats() throws Exception {
		
		String etatCandidat = "";
		String etatEntretien = "";
		
		sql = connectionBase.createStatement();
		try {
			rs = sql.executeQuery("SELECT * FROM candidature WHERE id=2;");
	    	 rs.next();
	    	 etatCandidat = rs.getString("etat");
	    	 
	    	 rs = sql.executeQuery("SELECT * FROM entretien WHERE id=1;");
	    	 rs.next();
	    	 etatEntretien = rs.getString("etat");
		} catch (SQLException e) {
		    	 //if(!e.getMessage().equals("Aucun résultat retourné par la requête.")) {
	   		 e.printStackTrace();
		    	 throw new Exception("Anomalie lors de l'execution de la requÃªte" );
	   	 //}
	    } catch (Exception e) {
			System.out.println("Ca marche pas");
		}
		
		 IServiceCommon serviceCommon = this.getServiceCommon();
	     //serviceCommon.flushEntityManager();
	     Candidature candidature = serviceCommon.findCandidatureById(2);
	     Entretien entretien = serviceCommon.findEntretienById(1);
	     System.out.println("Etat de la candidature : "+etatCandidat);
	     System.out.println("Etat supposé : "+candidature.getEtat());
	     System.out.println("Etat de l'entretien : "+etatEntretien);
	     System.out.println("Etat supposé : "+entretien.getEtat());
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
	@Before
    public void setUp() throws Exception {
		this.loadDatabase();
	}
	
	@After
	public void tearDown() throws Exception {
		//this.checkResultats();
	}
	
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
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("InvalidUserException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
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
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("InvalidUserException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
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
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("BadParameterException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
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
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("BadParameterException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
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
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("No exception should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		
		assertEquals(nombreCandidature+1, serviceCommon.getListCandidatures().size());
		
	}
	
	@Test
	public void testValideEntretienCandidatNull(){
		
		IServiceCandidats serviceCandidats = this.getServiceCandidat();
		IServiceCommon serviceCommon = this.getServiceCommon();
		
		Candidat candidat = serviceCommon.findCandidatById(1);
		
		List<Candidature> candidatures = candidat.getCandidatures();
		
		Candidature candidature = candidatures.get(1);
		
		Entretien entretien =  candidature.getEntretiens().get(0);
		
		try {
			entretien = serviceCandidats.valideEntretien(null, candidature.getEntretiens().get(0));
		} catch (InvalidUserException e) {
			
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("InvalidUserException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		
		assertEquals("cree",serviceCommon.findEntretienById(1).getEtat());
		
	}
	
	@Test
	public void testValideEntretienNull(){
		
		IServiceCandidats serviceCandidats = this.getServiceCandidat();
		IServiceCommon serviceCommon = this.getServiceCommon();
		
		Candidat candidat = serviceCommon.findCandidatById(1);
		
		List<Candidature> candidatures = candidat.getCandidatures();
		
		Candidature candidature = candidatures.get(1);
		
		Entretien entretien =  candidature.getEntretiens().get(0);
		
		try {
			entretien = serviceCandidats.valideEntretien(candidat, null);
		} catch (BadParameterException e) {
			
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("BadParameterException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		
		assertEquals("cree", serviceCommon.findEntretienById(1).getEtat());
		
	}
	
	@Test
	public void testValideEntretienBadUser(){
		
		IServiceCandidats serviceCandidats = this.getServiceCandidat();
		IServiceCommon serviceCommon = this.getServiceCommon();
		
		Candidat candidat1 = serviceCommon.findCandidatById(1);
		Entretien entretien = candidat1.getCandidatures().get(1).getEntretiens().get(0);
		Candidat candidat2 = serviceCommon.findCandidatById(2);
		
		try {
			entretien = serviceCandidats.valideEntretien(candidat2, entretien);
		} catch (InvalidUserException e) {
			
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("InvalidUserException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		
		assertEquals("cree", serviceCommon.findEntretienById(1).getEtat());
		
	}
	
	@Test
	public void testValideEntretienNotInDb(){
		
		IServiceCandidats serviceCandidats = this.getServiceCandidat();
		IServiceCommon serviceCommon = this.getServiceCommon();
		
		Candidat candidat = serviceCommon.findCandidatById(1);
		
		Entretien entretien = new Entretien();
		
		try {
			entretien = serviceCandidats.valideEntretien(candidat, entretien);
		} catch (BadParameterException e) {
			
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("BadParameterException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		
		assertEquals(null, entretien.getEtat());
		
	}
	
	@Test
	public void testValideEntretienValide(){
		
		IServiceCandidats serviceCandidats = this.getServiceCandidat();
		IServiceCommon serviceCommon = this.getServiceCommon();
		
		Candidat candidat = serviceCommon.findCandidatById(1);
		
		Entretien entretien = candidat.getCandidatures().get(1).getEntretiens().get(0);
		
		try {
			entretien = serviceCandidats.valideEntretien(candidat, entretien);
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("No exception should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		
		assertEquals("valide_candidat", serviceCommon.findEntretienById(1).getEtat());
		
	}
	
	@Test
	public void testAnnuleCandidatureCandidatNull(){
		
		IServiceCandidats serviceCandidats = this.getServiceCandidat();
		IServiceCommon serviceCommon = this.getServiceCommon();
		
		Candidat candidat = serviceCommon.findCandidatById(1);
		
		Candidature candidature = candidat.getCandidatures().get(1);
		
		try {
			candidature = serviceCandidats.annuleCandidature(null, candidature);
		} catch (InvalidUserException e) {
			
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("InvalidUserException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		
		assertEquals("valide", serviceCommon.findCandidatureById(2).getEtat());
		
	}
	
	@Test
	public void testAnnuleCandidatureNull(){
		
		IServiceCandidats serviceCandidats = this.getServiceCandidat();
		IServiceCommon serviceCommon = this.getServiceCommon();
		
		Candidat candidat = serviceCommon.findCandidatById(1);
		
		Candidature candidature = candidat.getCandidatures().get(1);
		
		try {
			candidature = serviceCandidats.annuleCandidature(candidat, null);
		} catch (BadParameterException e) {
			
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("BadParameterException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		
		assertEquals("valide", serviceCommon.findCandidatureById(2).getEtat());
		
	}
	
	@Test
	public void testAnnuleCandidatureNonValide(){
		
		IServiceCandidats serviceCandidats = this.getServiceCandidat();
		IServiceCommon serviceCommon = this.getServiceCommon();
		
		Candidat candidat = serviceCommon.findCandidatById(1);
		
		Candidature candidature = candidat.getCandidatures().get(0);
		
		try {
			candidature = serviceCandidats.annuleCandidature(candidat, candidature);
		} catch (BadStateException e) {
			
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("BadStateException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		
		assertEquals("valide", serviceCommon.findCandidatureById(1).getEtat());
		
	}
	
	@Test
	public void testAnnuleCandidatureValide(){
		
		IServiceCandidats serviceCandidats = this.getServiceCandidat();
		IServiceCommon serviceCommon = this.getServiceCommon();
		
		Candidat candidat = serviceCommon.findCandidatById(1);
		
		Candidature candidature = candidat.getCandidatures().get(1);
		
		try {
			candidature = serviceCandidats.annuleCandidature(candidat, candidature);
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("No exception should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		
		assertEquals("annule", serviceCommon.findCandidatureById(2).getEtat());
		
	}

}
