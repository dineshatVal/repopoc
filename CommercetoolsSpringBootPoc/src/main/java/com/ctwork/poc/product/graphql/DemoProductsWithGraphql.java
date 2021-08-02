package com.ctwork.poc.product.graphql;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercetools.api.client.ApiRoot;
import com.commercetools.api.models.common.LocalizedStringBuilder;
import com.commercetools.api.models.graph_ql.GraphQLRequest;
import com.commercetools.api.models.graph_ql.GraphQLRequestBuilder;
import com.commercetools.api.models.graph_ql.GraphQLResponse;
import com.commercetools.api.models.graph_ql.GraphQLVariablesMap;
import com.commercetools.api.models.graph_ql.GraphQLVariablesMapBuilder;
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
public class DemoProductsWithGraphql {

	
	@Autowired 
	private ApiRoot ApiClient;
	 
    @Autowired
    private Environment env;

    @SuppressWarnings("unchecked")
	@GetMapping("/graphqlproduct")
    public String getCli() throws ExecutionException, InterruptedException {	  
		
		  
    	String query = "query {product(id: \"d94e7f47-cb2d-4bab-8b57-50be8daad716\") {id version}}";
    	String query1 = "query ($productid: String) {product(id: $productid) {id version}}";
    	StringBuilder sb = new StringBuilder();
    	sb.append("query {product(id: ").append("\"d94e7f47-cb2d-4bab-8b57-50be8daad716\")")
    	.append("{id version}}");

    	
    	Map<String, Object> m = new HashMap<String, Object>();
    	m.put("productid", "d94e7f47-cb2d-4bab-8b57-50be8daad716");
    	
    	GraphQLVariablesMap graphMap = GraphQLVariablesMapBuilder.of()
    			.values(m)
    			.build();
    			

    	GraphQLRequest gReq = GraphQLRequestBuilder.of()
    			.query(query1)
    			.variables(graphMap)
    			.build();
    	
    	GraphQLResponse product = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
    	.graphql()
    	.post(gReq)
    	.executeBlocking()
    	.getBody();
		  
		return (product.getData().toString());
		  
		  
        //return "GraphQL Query Worked";
    }
}
