package com.ctwork.poc.extension;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercetools.api.client.ApiRoot;
import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CartDraft;
import com.commercetools.api.models.cart.CartDraftBuilder;
import com.commercetools.api.models.cart.LineItemDraft;
import com.commercetools.api.models.cart.LineItemDraftBuilder;



//import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/")
@PropertySource("classpath:ctp.properties")
public class DemoExtensionsTest {

	@Autowired 
	private ApiRoot ApiClient;
	
    @Autowired
    private Environment env;

    @GetMapping("/extensiontest")
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
		 * CartDraft cartDraft = CartDraftBuilder.of() .currency("EUR") .build();
		 * 
		 * Cart cartAdd = apiRoot.withProjectKey(env.getProperty("ctp.projectkey"))
		 * .carts() .post(cartDraft) .executeBlocking() .getBody();
		 */
	   
		
		  LineItemDraft lineItemDraft = LineItemDraftBuilder.of()
		  .productId("d94e7f47-cb2d-4bab-8b57-50be8daad716").quantity((long) 9).build();	 
	    	    
	    CartDraft cdraft = CartDraftBuilder.of().lineItems(lineItemDraft).currency("EUR").build();
	    
	    Cart cartAdd2 = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
				   .carts()
				   .post(cdraft)
				   .executeBlocking()
				   .getBody();
		 
	    /*List<LineItemDraft> cartAddItems = CartDraft.of(cartDraft)
		   .getLineItems();*/
		   
		/*
		 * CartAddLineItemAction cALIA = CartAddLineItemActionBuilder.of()
		 * .productId("d94e7f47-cb2d-4bab-8b57-50be8daad716") .quantity((long) 6)
		 * .build();
		 * 
		 * CartUpdate cartUpdate = CartUpdateBuilder.of() .version((long) 1)
		 * .actions(cALIA).build();
		 * 
		 * Cart cartPull = apiRoot.withProjectKey(env.getProperty("ctp.projectkey"))
		 * .carts() .withId(cartAdd.getId()) .post(cartUpdate) .executeBlocking()
		 * .getBody();
		 */
//  CartUpdateAction cartUpdateAction = CartUpdateAction.
 
	
		  
        return cartAdd2.getId();
    }
}
