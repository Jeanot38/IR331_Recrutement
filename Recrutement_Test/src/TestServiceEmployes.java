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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eu.telecom_bretagne.recrutement.data.model.Candidat;
import eu.telecom_bretagne.recrutement.data.model.Candidature;
import eu.telecom_bretagne.recrutement.data.model.Utilisateur;
import eu.telecom_bretagne.recrutement.exception.BadParameterException;
import eu.telecom_bretagne.recrutement.exception.BadStateException;
import eu.telecom_bretagne.recrutement.service.IServiceCommon;
import eu.telecom_bretagne.recrutement.service.IServiceRH;


public class TestServiceEmployes {
	
	private Connection connectionBase;
	private Statement sql;
	private ResultSet rs;
	
	public TestServiceEmployes() throws Exception {
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
	
	private IServiceRH getServiceRH() {
		InitialContext ctx;
		IServiceRH serviceRH = null;
		try {
			ctx = new InitialContext();
			serviceRH = (IServiceRH) ctx.lookup(IServiceRH.JNDI_NAME);
		}
		catch (NamingException e)
		{
			// Unable to retrieve the context or the service
			e.printStackTrace();
			System.exit(-1);
		}
		return serviceRH;
	}
	
	@Before
    public void setUp() throws Exception {
		this.loadDatabase();
	}
	
	@After
	public void tearDown() throws Exception {
		//this.checkResultats();
	}
	
	@Test
	public void testEtudierCandidatureNull() {
		
		IServiceCommon serviceCommon = this.getServiceCommon();
		IServiceRH serviceRH = this.getServiceRH();
		
		try {
			serviceRH.etudierCandidature(null, "valide");
		} catch (BadParameterException e) {
			
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("BadParameterException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		
		assertEquals("cree", serviceCommon.findCandidatureById(1).getEtat());
		
	}
	
	@Test
	public void testEtudierCandidatureEtatNull() {
		
		IServiceCommon serviceCommon = this.getServiceCommon();
		IServiceRH serviceRH = this.getServiceRH();
		
		Candidature candidature = serviceCommon.findCandidatureById(1);
		
		try {
			serviceRH.etudierCandidature(candidature, null);
		} catch (BadParameterException e) {
			
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("BadParameterException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		
		assertEquals("cree", serviceCommon.findCandidatureById(1).getEtat());
		
	}
	
	@Test
	public void testEtudierCandidatureWrongState() {
		
		IServiceCommon serviceCommon = this.getServiceCommon();
		IServiceRH serviceRH = this.getServiceRH();
		
		Candidature candidature = serviceCommon.findCandidatureById(1);
		
		try {
			serviceRH.etudierCandidature(candidature, "coucou");
		} catch (BadStateException e) {
			
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("BadStateException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		
		assertEquals("cree", serviceCommon.findCandidatureById(1).getEtat());
		
	}
	
	@Test
	public void testEtudierCandidatureBadFirstState() {
		
		IServiceCommon serviceCommon = this.getServiceCommon();
		IServiceRH serviceRH = this.getServiceRH();
		
		Candidature candidature = serviceCommon.findCandidatureById(2);
		
		try {
			serviceRH.etudierCandidature(candidature, "refuse");
		} catch (BadStateException e) {
			
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("BadStateException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		
		assertEquals("cree", serviceCommon.findCandidatureById(2).getEtat());
		
	}
	
	@Test
	public void testEtudierCandidatureValide() {
		
		IServiceCommon serviceCommon = this.getServiceCommon();
		IServiceRH serviceRH = this.getServiceRH();
		
		Candidature candidature = serviceCommon.findCandidatureById(1);
		
		try {
			serviceRH.etudierCandidature(candidature, "refuse");
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("No exception should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		
		assertEquals("refuse", serviceCommon.findCandidatureById(1).getEtat());
		
	}
	
	@Test
	public void testProposerDateEntretienCandidatureNull() {
		IServiceCommon serviceCommon = this.getServiceCommon();
		IServiceRH serviceRH = this.getServiceRH();
		
		int nombreEntretien = serviceCommon.getListEntretiens().size();
		
		List<Utilisateur> utilisateurs = serviceCommon.getListUtilisateurs();

		for (int i = 0; i<utilisateurs.size();i++) {
			if(utilisateurs.get(i).getCandidat() != null) {
				utilisateurs.remove(i);
			}
		}
		
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date dateEntretien = sdf.parse("25/08/2015 09:00:00");
			
			serviceRH.proposerDateEntretien(null, utilisateurs, dateEntretien);
		} catch (BadParameterException e) {
			
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("BadParameterException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		
		assertEquals(nombreEntretien, serviceCommon.getListEntretiens().size());
		
	}
	
	@Test
	public void testProposerDateEntretienUtilisateurNull() {
		IServiceCommon serviceCommon = this.getServiceCommon();
		IServiceRH serviceRH = this.getServiceRH();
		
		int nombreEntretien = serviceCommon.getListEntretiens().size();
		
		Candidature candidature = serviceCommon.findCandidatureById(2);
		List<Utilisateur> utilisateurs = serviceCommon.getListUtilisateurs();

		for (int i = 0; i<utilisateurs.size();i++) {
			if(utilisateurs.get(i).getCandidat() != null) {
				utilisateurs.remove(i);
			}
		}
		
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date dateEntretien = sdf.parse("25/08/2015 09:00:00");
			
			serviceRH.proposerDateEntretien(candidature, null, dateEntretien);
		} catch (BadParameterException e) {
			
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("BadParameterException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		
		assertEquals(nombreEntretien, serviceCommon.getListEntretiens().size());
		
	}
	
	@Test
	public void testProposerDateEntretienDateNull() {
		IServiceCommon serviceCommon = this.getServiceCommon();
		IServiceRH serviceRH = this.getServiceRH();
		
		int nombreEntretien = serviceCommon.getListEntretiens().size();
		
		Candidature candidature = serviceCommon.findCandidatureById(2);
		List<Utilisateur> utilisateurs = serviceCommon.getListUtilisateurs();

		for (int i = 0; i<utilisateurs.size();i++) {
			if(utilisateurs.get(i).getCandidat() != null) {
				utilisateurs.remove(i);
			}
		}
		
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date dateEntretien = sdf.parse("25/08/2015 09:00:00");
			
			serviceRH.proposerDateEntretien(candidature, utilisateurs, null);
		} catch (BadParameterException e) {
			
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("BadParameterException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		
		assertEquals(nombreEntretien, serviceCommon.getListEntretiens().size());
		
	}
	
	@Test
	public void testProposerDateEntretienCandidatureNonValide() {
		IServiceCommon serviceCommon = this.getServiceCommon();
		IServiceRH serviceRH = this.getServiceRH();
		
		int nombreEntretien = serviceCommon.getListEntretiens().size();
		
		Candidature candidature = serviceCommon.findCandidatureById(1);
		List<Utilisateur> utilisateurs = serviceCommon.getListUtilisateurs();

		for (int i = 0; i<utilisateurs.size();i++) {
			if(utilisateurs.get(i).getCandidat() != null) {
				utilisateurs.remove(i);
			}
		}
		
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date dateEntretien = sdf.parse("25/08/2015 09:00:00");
			
			serviceRH.proposerDateEntretien(candidature, utilisateurs, dateEntretien);
		} catch (BadStateException e) {
			
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("BadStateException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		
		assertEquals(nombreEntretien, serviceCommon.getListEntretiens().size());
		
	}
	
	@Test
	public void testProposerDateEntretienUtilisateurCandidat() {
		IServiceCommon serviceCommon = this.getServiceCommon();
		IServiceRH serviceRH = this.getServiceRH();
		
		int nombreEntretien = serviceCommon.getListEntretiens().size();
		
		Candidature candidature = serviceCommon.findCandidatureById(2);
		List<Utilisateur> utilisateurs = serviceCommon.getListUtilisateurs();

		/*for (int i = 0; i<utilisateurs.size();i++) {
			if(utilisateurs.get(i).getCandidat() != null) {
				utilisateurs.remove(i);
			}
		}*/
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date dateEntretien = sdf.parse("25/08/2015 09:00:00");
			
			serviceRH.proposerDateEntretien(candidature, utilisateurs, dateEntretien);
		} catch (BadParameterException e) {
			
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("BadParameterException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		
		assertEquals(nombreEntretien, serviceCommon.getListEntretiens().size());
		
	}
	
	@Test
	public void testProposerDateEntretienDatePassee() {
		IServiceCommon serviceCommon = this.getServiceCommon();
		IServiceRH serviceRH = this.getServiceRH();
		
		int nombreEntretien = serviceCommon.getListEntretiens().size();
		
		Candidature candidature = serviceCommon.findCandidatureById(2);
		List<Utilisateur> utilisateurs = serviceCommon.getListUtilisateurs();

		for (int i = 0; i<utilisateurs.size();i++) {
			if(utilisateurs.get(i).getCandidat() != null) {
				utilisateurs.remove(i);
			}
		}
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date dateEntretien = sdf.parse("25/08/2014 09:00:00");
			
			serviceRH.proposerDateEntretien(candidature, utilisateurs, dateEntretien);
		} catch (BadParameterException e) {
			
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("BadParameterException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		
		assertEquals(nombreEntretien, serviceCommon.getListEntretiens().size());
		
	}
	
	@Test
	public void testProposerDateEntretienValide() {
		IServiceCommon serviceCommon = this.getServiceCommon();
		IServiceRH serviceRH = this.getServiceRH();
		
		int nombreEntretien = serviceCommon.getListEntretiens().size();
		
		Candidature candidature = serviceCommon.findCandidatureById(2);
		List<Utilisateur> utilisateurs = serviceCommon.getListUtilisateurs();

		for (int i = 0; i<utilisateurs.size();i++) {
			if(utilisateurs.get(i).getCandidat() != null) {
				utilisateurs.remove(i);
			}
		}
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date dateEntretien = sdf.parse("25/08/2015 09:00:00");
			
			serviceRH.proposerDateEntretien(candidature, utilisateurs, dateEntretien);
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("No exception should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		
		assertEquals(nombreEntretien+1, serviceCommon.getListEntretiens().size());
		
	}
	
	//TODO Rajouter tests création comiteEntretien si déjà présent
	
	@Test
	public void testInformerCandidatNull() {
		IServiceCommon serviceCommon = this.getServiceCommon();
		IServiceRH serviceRH = this.getServiceRH();
		
		int nombreMessage = serviceCommon.getListMessages().size();
		
		Candidat candidat = serviceCommon.findCandidatById(1);
		String sujet = "Un sujet de tests";
		String contenu = "Voila le contenu d'un message";
		
		try {
			serviceRH.informerCandidat(null, sujet, contenu);
		} catch (BadParameterException e) {
			
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("BadParameterException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		assertEquals(nombreMessage, serviceCommon.getListMessages().size());
	}
	
	@Test
	public void testInformerCandidatSujetNull() {
		IServiceCommon serviceCommon = this.getServiceCommon();
		IServiceRH serviceRH = this.getServiceRH();
		
		int nombreMessage = serviceCommon.getListMessages().size();
		
		Candidat candidat = serviceCommon.findCandidatById(1);
		String sujet = "Un sujet de tests";
		String contenu = "Voila le contenu d'un message";
		
		try {
			serviceRH.informerCandidat(candidat, null, contenu);
		} catch (BadParameterException e) {
			
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("BadParameterException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		assertEquals(nombreMessage, serviceCommon.getListMessages().size());
	}
	
	@Test
	public void testInformerCandidatSujetVide() {
		IServiceCommon serviceCommon = this.getServiceCommon();
		IServiceRH serviceRH = this.getServiceRH();
		
		int nombreMessage = serviceCommon.getListMessages().size();
		
		Candidat candidat = serviceCommon.findCandidatById(1);
		String sujet = "Un sujet de tests";
		String contenu = "Voila le contenu d'un message";
		
		try {
			serviceRH.informerCandidat(candidat, "", contenu);
		} catch (BadParameterException e) {
			
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("BadParameterException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		assertEquals(nombreMessage, serviceCommon.getListMessages().size());
	}
	
	@Test
	public void testInformerCandidatContenuNull() {
		IServiceCommon serviceCommon = this.getServiceCommon();
		IServiceRH serviceRH = this.getServiceRH();
		
		int nombreMessage = serviceCommon.getListMessages().size();
		
		Candidat candidat = serviceCommon.findCandidatById(1);
		String sujet = "Un sujet de tests";
		String contenu = "Voila le contenu d'un message";
		
		try {
			serviceRH.informerCandidat(candidat, sujet, null);
		} catch (BadParameterException e) {
			
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("BadParameterException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		assertEquals(nombreMessage, serviceCommon.getListMessages().size());
	}
	
	@Test
	public void testInformerCandidatContenuVide() {
		IServiceCommon serviceCommon = this.getServiceCommon();
		IServiceRH serviceRH = this.getServiceRH();
		
		int nombreMessage = serviceCommon.getListMessages().size();
		
		Candidat candidat = serviceCommon.findCandidatById(1);
		String sujet = "Un sujet de tests";
		String contenu = "Voila le contenu d'un message";
		
		try {
			serviceRH.informerCandidat(candidat, sujet, "");
		} catch (BadParameterException e) {
			
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("BadParameterException should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		assertEquals(nombreMessage, serviceCommon.getListMessages().size());
	}
	
	@Test
	public void testInformerCandidatValide() {
		IServiceCommon serviceCommon = this.getServiceCommon();
		IServiceRH serviceRH = this.getServiceRH();
		
		int nombreMessage = serviceCommon.getListMessages().size();
		
		Candidat candidat = serviceCommon.findCandidatById(1);
		String sujet = "Un sujet de tests";
		String contenu = "Voila le contenu d'un message";
		
		try {
			serviceRH.informerCandidat(candidat, sujet, contenu);
		} catch (BadParameterException e) {
			
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter);
			printWriter.flush();
			
			fail("No exception should be catch, "+e.getClass()+" is the real.\n"+writer.toString());
		}
		assertEquals(nombreMessage+1, serviceCommon.getListMessages().size());
	}
	
}
