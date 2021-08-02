package com.ctwork.poc.subscription;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercetools.api.client.ApiRoot;
import com.commercetools.api.models.subscription.MessageSubscription;
import com.commercetools.api.models.subscription.MessageSubscriptionBuilder;
import com.commercetools.api.models.subscription.SnsDestination;
import com.commercetools.api.models.subscription.SnsDestinationBuilder;
import com.commercetools.api.models.subscription.Subscription;
import com.commercetools.api.models.subscription.SubscriptionDraft;
import com.commercetools.api.models.subscription.SubscriptionDraftBuilder;
import com.commercetools.api.models.subscription.SubscriptionSetMessagesAction;
import com.commercetools.api.models.subscription.SubscriptionSetMessagesActionBuilder;


//import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/")
@PropertySource("classpath:ctp.properties")
public class DemoSubscriptionSNS {

	@Autowired 
	private ApiRoot ApiClient;
	
    @Autowired
    private Environment env;

    @GetMapping("/subscription")
    public String getCli() throws ExecutionException, InterruptedException {

	
		/*
		 * ApiRoot apiRoot = ApiFactory.create( ClientCredentials.of()
		 * .withClientId(env.getProperty("ctp.clientId"))
		 * .withClientSecret(env.getProperty("ctp.clientSecret"))
		 * .withScopes(env.getProperty("ctp.scopes")) .build(),
		 * ServiceRegion.GCP_AUSTRALIA_SOUTHEAST1.getOAuthTokenUrl(),
		 * ServiceRegion.GCP_AUSTRALIA_SOUTHEAST1.getApiUrl());
		 */
	 
		/*
		 * GoogleCloudPubSubDestination gcpPubSub =
		 * GoogleCloudPubSubDestinationBuilder.of() .projectId("formal-envelope-319904")
		 * .topic("MyFirstTopic111") //.topic(getCli()) .build();
		 */
	  	
	  	SnsDestination snsDest = SnsDestinationBuilder.of()
	  			.topicArn(env.getProperty("ctp.topicarn"))
	  			.accessKey(env.getProperty("ctp.accesskey"))
	  			.accessSecret(env.getProperty("ctp.accesssecret"))
	  			.build();
	  	
		  MessageSubscription messageSub = MessageSubscriptionBuilder.of()
				  .resourceTypeId("customer")
				  .types("CustomerCreated")
				  .build();
		
		  SubscriptionDraft subDraft = SubscriptionDraftBuilder.of()
				  .destination(snsDest)
				  .messages(messageSub)
				  .build();
		  
		  Subscription subscr = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
				  .subscriptions()
				  .post(subDraft)
				  .executeBlocking()
				  .getBody();
				  		  

        return subscr.getId();
    }
}
