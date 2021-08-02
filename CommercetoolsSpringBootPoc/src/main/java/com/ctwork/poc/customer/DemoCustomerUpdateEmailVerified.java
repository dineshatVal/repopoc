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
import com.commercetools.api.models.customer.CustomerEmailVerify;
import com.commercetools.api.models.customer.CustomerEmailVerifyBuilder;
import com.commercetools.api.models.customer.CustomerSetMiddleNameAction;
import com.commercetools.api.models.customer.CustomerSetMiddleNameActionBuilder;
import com.commercetools.api.models.customer.CustomerUpdate;
import com.commercetools.api.models.customer.CustomerUpdateBuilder;
import com.commercetools.api.models.message.CustomerEmailVerifiedMessage;
import com.commercetools.api.models.message.CustomerEmailVerifiedMessageBuilder;
import com.fasterxml.jackson.databind.JsonNode;

import io.vrap.rmf.base.client.oauth2.ClientCredentials;


//import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/")
@PropertySource("classpath:ctp.properties")
public class DemoCustomerUpdateEmailVerified {

	
	@Autowired 
	private ApiRoot ApiClient;
	 
    @Autowired
    private Environment env;

    @GetMapping("/verifyEmail")
    public JsonNode getCli() throws ExecutionException, InterruptedException {

	
		/*
		 * ApiRoot apiRoot = ApiFactory.create( ClientCredentials.of()
		 * .withClientId(env.getProperty("ctp.clientId"))
		 * .withClientSecret(env.getProperty("ctp.clientSecret"))
		 * .withScopes(env.getProperty("ctp.scopes")) .build(),
		 * ServiceRegion.GCP_AUSTRALIA_SOUTHEAST1.getOAuthTokenUrl(),
		 * ServiceRegion.GCP_AUSTRALIA_SOUTHEAST1.getApiUrl());
		 */
		 
	  CustomerEmailVerify custEmailVerify = CustomerEmailVerifyBuilder.of()
			  .tokenValue("QmlwE_7LytK6WyQPVKhEa5TLrQGf0YQBk7SuItWk")
			  .build();
	  
	  
	  
	  
	  return ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
			  .customers()
			  .emailConfirm()
			  .post(custEmailVerify)
			  .executeBlocking()
			  .getBody();
		
	   
	   
		  
       // return custU.getId();
    }
}
