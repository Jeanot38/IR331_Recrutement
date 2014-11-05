import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Test;

import eu.telecom_bretagne.recrutement.service.IServiceCommon;


public class TestServiceEmployes {
	
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
	
	@Test
	public void 
}
