package com.ctwork.poc.product.graphql;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercetools.api.client.ApiRoot;
import com.commercetools.api.models.graph_ql.GraphQLRequest;
import com.commercetools.api.models.graph_ql.GraphQLRequestBuilder;
import com.commercetools.api.models.graph_ql.GraphQLResponse;
import com.commercetools.api.models.graph_ql.GraphQLVariablesMap;
import com.commercetools.api.models.graph_ql.GraphQLVariablesMapBuilder;

//import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/")
@PropertySource("classpath:ctp.properties")
public class DemoCustomerWithGraphql {

	
	@Autowired 
	private ApiRoot ApiClient;
	 
    @Autowired
    private Environment env;

    @SuppressWarnings("unchecked")
	@GetMapping("/graphqlcustomer")
    public String getCli() {	  
		
		  
    	String query1 = "query ($custKey: String) {customer(key: $custKey) {email firstName lastName}}";
		/*
		 * StringBuilder sb = new StringBuilder();
		 * sb.append("query {product(id: ").append(
		 * "\"d94e7f47-cb2d-4bab-8b57-50be8daad716\")") .append("{id version}}");
		 */

    	
    	Map<String, Object> m = new HashMap<String, Object>();
    	m.put("custKey", "Test-Melan-Key");
    	
    	GraphQLVariablesMap graphMap = GraphQLVariablesMapBuilder.of()
    			.values(m)
    			.build();
    			

    	GraphQLRequest gReq = GraphQLRequestBuilder.of()
    			.query(query1)
    			.variables(graphMap)
    			.build();
    	
    	GraphQLResponse customer = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
    	.graphql()
    	.post(gReq)
    	.executeBlocking()
    	.getBody();
		  
		return (customer.getData().toString());
		  
		  
        //return "GraphQL Query Worked";
    }
}
