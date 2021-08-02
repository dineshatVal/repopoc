package com.ctwork.poc.cart;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercetools.api.client.ApiRoot;
import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CartAddLineItemAction;
import com.commercetools.api.models.cart.CartAddLineItemActionBuilder;
import com.commercetools.api.models.cart.CartDraft;
import com.commercetools.api.models.cart.CartDraftBuilder;
import com.commercetools.api.models.cart.CartUpdate;
import com.commercetools.api.models.cart.CartUpdateBuilder;
import com.commercetools.api.models.cart.LineItemDraft;
import com.commercetools.api.models.cart.LineItemDraftBuilder;



//import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/")
@PropertySource("classpath:ctp.properties")
public class DemoCart {

	
	  @Autowired 
	  private ApiRoot ApiClient;
	 
    @Autowired
    private Environment env;

    @GetMapping("/cart")
    public String getCli() throws ExecutionException, InterruptedException {	
		  
	   CartDraft cartDraft = CartDraftBuilder.of()
			   .currency("EUR")			   
			   .build();
		
	   Cart cartAdd = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
			   .carts()
			   .post(cartDraft)
			   .executeBlocking()
			   .getBody();
	   
		  LineItemDraft lineItemDraft = LineItemDraftBuilder.of()
		  .productId("d94e7f47-cb2d-4bab-8b57-50be8daad716").quantity((long) 2).build();	 
	   
	    	    
	    CartDraft cdraft = CartDraftBuilder.of().lineItems(lineItemDraft).currency("EUR").build();
	    
	    Cart cartAdd2 = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
				   .carts()
				   .post(cdraft)
				   .executeBlocking()
				   .getBody();
		 
	    /*List<LineItemDraft> cartAddItems = CartDraft.of(cartDraft)
		   .getLineItems();*/
		   
		 CartAddLineItemAction cALIA = CartAddLineItemActionBuilder.of()
		 		.productId("d94e7f47-cb2d-4bab-8b57-50be8daad716")		 		
		 		.build();
		 
		 CartUpdate cartUpdate = CartUpdateBuilder.of()
				 .version((long) 1)
				 .actions(cALIA).build();
		 
		 Cart cartPull = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
				  .carts()
				  .withId(cartAdd.getId())
				  .post(cartUpdate)
				  .executeBlocking()
				  .getBody();
 

		  
        return cartAdd2.getId();
    }
}
