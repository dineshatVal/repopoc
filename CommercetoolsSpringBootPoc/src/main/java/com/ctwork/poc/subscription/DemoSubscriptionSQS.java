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
import com.commercetools.api.models.subscription.SqsDestination;
import com.commercetools.api.models.subscription.SqsDestinationBuilder;
import com.commercetools.api.models.subscription.Subscription;
import com.commercetools.api.models.subscription.SubscriptionDraft;
import com.commercetools.api.models.subscription.SubscriptionDraftBuilder;


//import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/")
@PropertySource("classpath:ctp.properties")
public class DemoSubscriptionSQS {

	@Autowired 
	private ApiRoot ApiClient;
	
    @Autowired
    private Environment env;

    @GetMapping("/subscriptionSQS")
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
	  	
	  	SqsDestination sqsDest = SqsDestinationBuilder.of()
	  			.queueUrl("https://sqs.us-east-1.amazonaws.com/601373112635/MyQueue-01")
	  			.accessKey(env.getProperty("ctp.accesskey"))
	  			.accessSecret(env.getProperty("ctp.accesssecret"))
	  			.region("us-east-1")
	  			.build();
	  	
		  MessageSubscription messageSub = MessageSubscriptionBuilder.of()
				  .resourceTypeId("customer")
				  .types("CustomerCreated")
				  .build();
		
		  SubscriptionDraft subDraft = SubscriptionDraftBuilder.of()
				  .destination(sqsDest)
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
