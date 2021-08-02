package com.ctwork.poc.customer;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercetools.api.client.ApiRoot;
import com.commercetools.api.defaultconfig.ApiFactory;
import com.commercetools.api.defaultconfig.ServiceRegion;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerCreateEmailToken;
import com.commercetools.api.models.customer.CustomerCreateEmailTokenBuilder;
import com.commercetools.api.models.customer.CustomerDraft;
import com.commercetools.api.models.customer.CustomerDraftBuilder;
import com.commercetools.api.models.customer.CustomerToken;
import com.commercetools.api.models.customer.CustomerTokenBuilder;

import io.vrap.rmf.base.client.oauth2.ClientCredentials;


//import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/")
@PropertySource("classpath:ctp.properties")
public class DemoCustomer {

	
	@Autowired 
	private ApiRoot ApiClient;
	 
    @Autowired
    private Environment env;

    @GetMapping("/cust")
    public String getCli() throws ExecutionException, InterruptedException {

	
		/*
		 * ApiRoot apiRoot = ApiFactory.create( ClientCredentials.of()
		 * .withClientId(env.getProperty("ctp.clientId"))
		 * .withClientSecret(env.getProperty("ctp.clientSecret"))
		 * .withScopes(env.getProperty("ctp.scopes")) .build(),
		 * ServiceRegion.GCP_AUSTRALIA_SOUTHEAST1.getOAuthTokenUrl(),
		 * ServiceRegion.GCP_AUSTRALIA_SOUTHEAST1.getApiUrl());
		 */
	 
		  
	   CustomerDraft custDraft = CustomerDraftBuilder.of()
			   .email("melena00@yahoo.com")
			   .firstName("Melany")
			   .lastName("Jenif")
			   .password("whoami")
			   .build();
		
	   
	   Customer customer = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
			   .customers()
			   .post(custDraft)
			   .executeBlocking()
			   .getBody()
			   .getCustomer();	  
 	
		/*
		 * CustomerCreateEmailToken cEmailToken = CustomerCreateEmailTokenBuilder.of()
		 * .id(customer.getId()) .ttlMinutes((long) 30) .build();
		 * 
		 * 
		 * 
		 * CustomerToken custToken =
		 * apiRoot.withProjectKey(env.getProperty("ctp.projectkey")) .customers()
		 * .emailToken() .post(cEmailToken) .executeBlocking() .getBody();
		 */
	   
	   CustomerToken custToken = new DemoCustomer().getCustToken(ApiClient, customer, env);
		  
        return custToken.getValue();
    }
    
    public CustomerToken getCustToken(ApiRoot apiRoot, Customer customer, Environment env) {
    	
    	CustomerCreateEmailToken cEmailToken = CustomerCreateEmailTokenBuilder.of()
 			   .id(customer.getId())
 			   .ttlMinutes((long) 30)
 			   .build();
 	   
 	   
 	   
 	   CustomerToken custToken = apiRoot.withProjectKey(env.getProperty("ctp.projectkey"))
 			   .customers()
 			   .emailToken()
 			   .post(cEmailToken)
 			   .executeBlocking()
 			   .getBody();
 	   
 	   return custToken;
    }
}
