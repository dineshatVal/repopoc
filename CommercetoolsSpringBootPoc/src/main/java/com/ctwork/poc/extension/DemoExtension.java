package com.ctwork.poc.extension;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercetools.api.client.ApiRoot;
import com.commercetools.api.models.extension.Extension;
import com.commercetools.api.models.extension.ExtensionAction;
import com.commercetools.api.models.extension.ExtensionAction.ExtensionActionEnum;
import com.commercetools.api.models.extension.ExtensionDraft;
import com.commercetools.api.models.extension.ExtensionDraftBuilder;
import com.commercetools.api.models.extension.ExtensionHttpDestination;
import com.commercetools.api.models.extension.ExtensionHttpDestinationBuilder;
import com.commercetools.api.models.extension.ExtensionResourceTypeId;
import com.commercetools.api.models.extension.ExtensionResourceTypeId.ExtensionResourceTypeIdEnum;
import com.commercetools.api.models.extension.ExtensionTrigger;
import com.commercetools.api.models.extension.ExtensionTriggerBuilder;



//import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/")
@PropertySource("classpath:ctp.properties")
public class DemoExtension {

	 @Autowired 
	 private ApiRoot ApiClient;
	 
    @Autowired
    private Environment env;

    @GetMapping("/extension")
    public String getCli() throws ExecutionException, InterruptedException {

	
		/*
		 * ApiRoot apiRoot = ApiFactory.create( ClientCredentials.of()
		 * .withClientId(env.getProperty("ctp.clientId"))
		 * .withClientSecret(env.getProperty("ctp.clientSecret"))
		 * .withScopes(env.getProperty("ctp.scopes")) .build(),
		 * ServiceRegion.GCP_AUSTRALIA_SOUTHEAST1.getOAuthTokenUrl(),
		 * ServiceRegion.GCP_AUSTRALIA_SOUTHEAST1.getApiUrl());
		 */
		 
	   
	  /*ExtensionHttpDestination extDesti = ExtensionHttpDestinationBuilder.of()
			  .url("https://us-central1-formal-envelope-319904.cloudfunctions.net/validateMaximumOfTenItems")
			  .build();*/
	  //https://us-central1-formal-envelope-319904.cloudfunctions.net/addMandatoryInsurance
		  
		  ExtensionHttpDestination extDesti = ExtensionHttpDestinationBuilder.of()
		  .url("https://us-central1-formal-envelope-319904.cloudfunctions.net/addMandatoryInsurance")
		  .build();
	  
	  ExtensionResourceTypeId extResTypeId = ExtensionResourceTypeIdEnum.CART;
	  List<ExtensionAction> extAction = new ArrayList<ExtensionAction>();
	  extAction.add(ExtensionActionEnum.CREATE);
	  extAction.add(ExtensionActionEnum.UPDATE);
	  
	  ExtensionTrigger extTrigger = ExtensionTriggerBuilder.of()
			  .resourceTypeId(extResTypeId)
			  .actions(extAction)
			  .build();
			  
			  
	  
	  ExtensionDraft extDraft = ExtensionDraftBuilder.of()
			   .destination(extDesti)
			   .triggers(extTrigger)
			   .build();
	  
	  Extension ext = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
			  .extensions()
			  .post(extDraft)
			  .executeBlocking()
			  .getBody();
			
 
	
		  
        return ext.getId();
    }
}
