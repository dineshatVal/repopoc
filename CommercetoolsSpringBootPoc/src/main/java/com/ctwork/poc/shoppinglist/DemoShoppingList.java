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
import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CartAddLineItemAction;
import com.commercetools.api.models.cart.CartAddLineItemActionBuilder;
import com.commercetools.api.models.cart.CartDraft;
import com.commercetools.api.models.cart.CartDraftBuilder;
import com.commercetools.api.models.cart.CartUpdate;
import com.commercetools.api.models.cart.CartUpdateBuilder;
import com.commercetools.api.models.cart.LineItemDraft;
import com.commercetools.api.models.cart.LineItemDraftBuilder;
import com.commercetools.api.models.common.LocalizedStringBuilder;
import com.commercetools.api.models.shopping_list.ShoppingList;
import com.commercetools.api.models.shopping_list.ShoppingListBuilder;
import com.commercetools.api.models.shopping_list.ShoppingListDraft;
import com.commercetools.api.models.shopping_list.ShoppingListDraftBuilder;

import io.vrap.rmf.base.client.ApiHttpResponse;



//import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/")
@PropertySource("classpath:ctp.properties")
public class DemoShoppingList {

	
	  @Autowired 
	  private ApiRoot ApiClient;
	 
    @Autowired
    private Environment env;

    @GetMapping("/shoppingList")
    public String getCli() throws InterruptedException, ExecutionException {	
		  
    	ShoppingListDraft shoppingListDraft = ShoppingListDraftBuilder.of()
    			.name(LocalizedStringBuilder.of().addValue("en", "My-List").build())
    			.key("Sample-Shopping-List")
    			.build();
    	
    	CompletableFuture<ApiHttpResponse<ShoppingList>> shoppingList = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
    			.shoppingLists()
    			.post(shoppingListDraft)
    			.execute();
    			

		  
        return shoppingList.get().getBody().getId();
    }
}
