package eu.telecom_bretagne.recrutement.front;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import eu.telecom_bretagne.recrutement.service.IServiceCandidats;
import eu.telecom_bretagne.recrutement.service.IServiceCommon;
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
	
	public static IServiceCommon getServiceCommon() {
		
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
	
public static IServiceCandidats getServiceCandidats() {
		
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
}
