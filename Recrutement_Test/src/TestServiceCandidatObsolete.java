import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
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

import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;


public class TestServiceCandidatObsolete extends DBTestCase {
 
    /** Driver JDBC. */
    private static final String JDBC_DRIVER = "org.postgresql.Driver";
 
    /** Base de données HSQLDB nommée "database" qui fonctionne en mode mémoire. */
    private static final String DATABASE = "jdbc:postgresql://localhost:54321/recrutement";
 
    /** Utilisateur qui se connecte à la base de données. */
    private static final String USER = "postgres";
 
    /** GetDataSet mot de passe pour se connecter à la base de données. */
    private static final String PASSWORD = "postgres";
	
	public TestServiceCandidatObsolete() {
        super();
        System.setProperty(
                PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,
                JDBC_DRIVER);
        System.setProperty(
                PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
                DATABASE);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME,
                USER);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD,
                PASSWORD);
    }
	
	protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSet(new FileInputStream(System.getProperty("user.dir")+"/src/datas.xml"));
    }
	
	protected DatabaseOperation getSetUpOperation() throws Exception
    {
        return DatabaseOperation.REFRESH;
    }
	
	protected DatabaseOperation getTearDownOperation() throws Exception
    {
        return DatabaseOperation.TRUNCATE_TABLE;
    }
	
	protected void setUpDatabaseConfig(DatabaseConfig config) {
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());

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
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	/*@Test
	public void testCreerCandidatureCandidatNull(){
		
		IServiceCandidats serviceCandidats = this.getServiceCandidat();
		IServiceCommon serviceCommon = this.getServiceCommon();
		
		int nombreCandidature = serviceCommon.getListCandidatures().size();
		Candidat candidat = serviceCommon.findCandidatById(1);
		
		try {
			serviceCandidats.creerCandidature(null, "Voici mon CV", "Voici ma lettre de motivation");
		} catch (InvalidUserException e) {
			
		} catch (Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
		
		Candidature candidature = candidatures.get(1);
		
		Entretien entretien =  candidature.getEntretiens().get(0);
		
		try {
			entretien = serviceCandidats.valideEntretien(null, candidature.getEntretiens().get(0));
		} catch (InvalidUserException e) {
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("InvalidUserException should be catch, "+e.getClass()+" is the real.");
		}
		
		assertEquals("",entretien.getEtat());
		
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
			e.printStackTrace();
			fail("BadParameterException should be catch, "+e.getClass()+" is the real.");
		}
		
		assertEquals("", entretien.getEtat());
		
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
			e.printStackTrace();
			fail("InvalidUserException should be catch, "+e.getClass()+" is the real.");
		}
		
		assertEquals("", entretien.getEtat());
		
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
			e.printStackTrace();
			fail("BadParameterException should be catch, "+e.getClass()+" is the real.");
		}
		
		assertEquals("", entretien.getEtat());
		
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
			e.printStackTrace();
			fail("No exception should be catch, "+e.getClass()+" is the real.");
		}
		
		assertEquals("valide_candidat", entretien.getEtat());
		
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
			e.printStackTrace();
			fail("InvalidUserException should be catch, "+e.getClass()+" is the real.");
		}
		
		assertEquals("valide", candidature.getEtat());
		
	}
	
	@Test
	public void testAnnuleCandidatureNull(){
		
		IServiceCandidats serviceCandidats = this.getServiceCandidat();
		IServiceCommon serviceCommon = this.getServiceCommon();
		
		Candidat candidat = serviceCommon.findCandidatById(1);
		
		Candidature candidature = candidat.getCandidatures().get(1);
		
		System.out.println("Voir candidature : "+candidature.getEtat());
		
		try {
			candidature = serviceCandidats.annuleCandidature(candidat, null);
		} catch (BadParameterException e) {
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("BadParameterException should be catch, "+e.getClass()+" is the real.");
		}
		
		assertEquals("valide", candidature.getEtat());
		
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
			e.printStackTrace();
			fail("BadStateException should be catch, "+e.getClass()+" is the real.");
		}
		
		assertEquals("", candidature.getEtat());
		
	}
	*/
	@Test
	public void testAnnuleCandidatureValide(){
		
		IServiceCandidats serviceCandidats = this.getServiceCandidat();
		IServiceCommon serviceCommon = this.getServiceCommon();
		
		Candidat candidat = serviceCommon.findCandidatById(1);
		
		Candidature candidature = candidat.getCandidatures().get(1);
		
		System.out.println("Voila l'avant : "+candidature.getEtat());
		
		try {
			candidature = serviceCandidats.annuleCandidature(candidat, candidature);
		} catch (Exception e) {
			e.printStackTrace();
			fail("No exception should be catch, "+e.getClass()+" is the real.");
		}
		
		System.out.println("Voila le résultat : "+candidature.getEtat());
		
		assertEquals("annule", candidature.getEtat());
		
	}

}
