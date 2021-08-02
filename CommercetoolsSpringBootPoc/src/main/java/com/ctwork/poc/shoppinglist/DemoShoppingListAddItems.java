package com.ctwork.poc.shoppinglist;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercetools.api.client.ApiRoot;
import com.commercetools.api.models.product.Product;
import com.commercetools.api.models.shopping_list.ShoppingListAddLineItemAction;
import com.commercetools.api.models.shopping_list.ShoppingListAddLineItemActionBuilder;
import com.commercetools.api.models.shopping_list.ShoppingListUpdate;
import com.commercetools.api.models.shopping_list.ShoppingListUpdateBuilder;

import io.vrap.rmf.base.client.ApiHttpResponse;



//import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/")
@PropertySource("classpath:ctp.properties")
public class DemoShoppingListAddItems {

	
	  @Autowired 
	  private ApiRoot ApiClient;
	 
    @Autowired
    private Environment env;

    @GetMapping("/shoppingListAddItems")
    public String getCli() throws InterruptedException, ExecutionException {	
		  
    	CompletableFuture<ApiHttpResponse<Product>> productToAdd = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
    	.products()
    	.withKey("ProductToShop")
    	.get()
    	.execute();
    	
		/*
		 * LineItemDraft lineItemDraft = LineItemDraftBuilder.of()
		 * .productId(productToAdd.get().getBody().getId()) .build();
		 */
    			
    	ShoppingListAddLineItemAction shopLALIA =  ShoppingListAddLineItemActionBuilder.of()
    			.productId(productToAdd.get().getBody().getId())
    			.build();
    	
    	Long version = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
    			.shoppingLists()
    			.withKey("Sample-Shopping-List")
    			.get()
    			.execute()
    			.get()
    			.getBody()
    			.getVersion();
    	
    	ShoppingListUpdate shopListUpdate = ShoppingListUpdateBuilder.of()
    			.version(version++)
    			.actions(shopLALIA)
    			.build();
    	
    	ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
		.shoppingLists()
		.withKey("Sample-Shopping-List")
		.post(shopListUpdate)
		.execute();
		
		  
        return "Shopping List updated succcessfully";
    }
}
