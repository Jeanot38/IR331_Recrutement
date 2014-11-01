package eu.telecom_bretagne.recrutement.front;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import eu.telecom_bretagne.recrutement.service.IServiceRH;


public class GestionService {
	
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
