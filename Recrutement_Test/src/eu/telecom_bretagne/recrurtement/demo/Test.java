package eu.telecom_bretagne.recrurtement.demo;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import eu.telecom_bretagne.recrutement.data.model.Candidature;
import eu.telecom_bretagne.recrutement.service.IServiceRH;

public class Test {

	public static void main(String[] args) {
		
		IServiceRH serviceRH = Test.getServiceRH();
		
		System.out.println("Début du lancement de l'application...");
		
		for (Candidature candidature : serviceRH.getCandidatures()) {
			System.out.println("Candidature n°"+candidature.getId());
			System.out.println("  CV : \n"+candidature.getCv());
			System.out.println("  Lettre de motiv : \n"+candidature.getLettreMotivation()+"\n\n");
		}

	}
	
	public static IServiceRH getServiceRH() {
		
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

}
