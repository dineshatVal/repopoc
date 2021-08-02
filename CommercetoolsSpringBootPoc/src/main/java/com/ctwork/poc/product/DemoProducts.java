package com.ctwork.poc.product;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercetools.api.client.ApiRoot;
import com.commercetools.api.models.common.LocalizedStringBuilder;
import com.commercetools.api.models.product.Product;
import com.commercetools.api.models.product.ProductDraft;
import com.commercetools.api.models.product.ProductDraftBuilder;
import com.commercetools.api.models.product_type.ProductType;
import com.commercetools.api.models.product_type.ProductTypeDraft;
import com.commercetools.api.models.product_type.ProductTypeDraftBuilder;
import com.commercetools.api.models.product_type.ProductTypeResourceIdentifierBuilder;

//import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/")
@PropertySource("classpath:ctp.properties")
public class DemoProducts {

	
	@Autowired 
	private ApiRoot ApiClient;
	 
    @Autowired
    private Environment env;

    @GetMapping("/product")
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
					 * ApiRoot apiRoot = ApiFactory.create( ClientCredentials.of()
					 * .withClientId("7lf62qvQW9L_71daa-6HScdW")
					 * .withClientSecret("4vvXmW-YOReftBS3O6Olfkbvl9XI09jH")
					 * .withScopes("manage_project:commercetools-2021") .build(),
					 * ServiceRegion.GCP_AUSTRALIA_SOUTHEAST1.getOAuthTokenUrl(),
					 * ServiceRegion.GCP_AUSTRALIA_SOUTHEAST1.getApiUrl());
					 */
		  
			/*
			 * CompletableFuture<ApiHttpResponse<ApiClientPagedQueryResponse>> s = apiRoot
			 * .withProjectKey("commercetools-2021") .apiClients() .get() .execute();
			 */
		 
//                .getBodyAsString();

			/*
			 * Category queriedCategory = apiRoot.withProjectKey("commercetools-2021")
			 * .categories() .withId("95918ce0-c036-4b77-88d7-bc70688f99b4") .get()
			 * .executeBlocking() .getBody();
			 */
	      //  System.out.println(queriedCategory.getExternalId());
		  
		//  ApiHttpResponse<com.commercetools.api.models.product.ProductPagedQueryResponse>
		/*
		 * ProductPagedQueryResponse product =
		 * apiRoot.withProjectKey("commercetools-2021") .products() .get()
		 * .executeBlocking() .getBody();
		 */	  
	  
	  		ProductTypeDraft productTypeDraft = ProductTypeDraftBuilder.of()
	  				.name("MyTestProductType-01")
	  				.description("MyFirstProductTypeDesc-01")
	  				.build();
	  		
	  		ProductType productTypeAdd = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
	  				.productTypes()
	  				.post(productTypeDraft)
	  				.executeBlocking()
	  				.getBody();
	  
		  ProductDraft productDraft = ProductDraftBuilder.of()
				  .productType(ProductTypeResourceIdentifierBuilder.of().id(productTypeAdd.getId()).build())				  
				  .name(LocalizedStringBuilder.of().addValue("en", "MyFirstTestProduct").build())
	              .slug(LocalizedStringBuilder.of().addValue("en", "TestSlug"+"-"+productTypeAdd.getId()).build())            		  	            		  
	              .description(LocalizedStringBuilder.of().addValue("en", "TestDescription").build())
	             // .key("Test Key")
	              .build();
		  
		  Product productAdd = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
				  .products()
				  .post(productDraft)
				  .executeBlocking()
				  .getBody();
		  
		  Product productAdded = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
				  .products()
				  .withId(productAdd.getId())
				  .get()
				  .executeBlocking()
				  .getBody();
		  
		  
		  
		  
		  
		  
        return productAdded.getId();
    }
}
