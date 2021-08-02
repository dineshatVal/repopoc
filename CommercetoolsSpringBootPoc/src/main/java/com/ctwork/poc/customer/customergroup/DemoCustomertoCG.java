package com.ctwork.poc.customer.customergroup;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercetools.api.client.ApiRoot;
import com.commercetools.api.models.cart.CartSetCustomerGroupActionBuilder;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerSetCustomerGroupAction;
import com.commercetools.api.models.customer.CustomerSetCustomerGroupActionBuilder;
import com.commercetools.api.models.customer.CustomerUpdate;
import com.commercetools.api.models.customer.CustomerUpdateBuilder;
import com.commercetools.api.models.customer_group.CustomerGroup;
import com.commercetools.api.models.customer_group.CustomerGroupDraft;
import com.commercetools.api.models.customer_group.CustomerGroupDraftBuilder;
import com.commercetools.api.models.customer_group.CustomerGroupResourceIdentifier;
import com.commercetools.api.models.customer_group.CustomerGroupResourceIdentifierBuilder;



@RestController
@RequestMapping("/")
@PropertySource("classpath:ctp.properties")
public class DemoCustomertoCG {

	
	@Autowired 
	private ApiRoot ApiClient;
	 
    @Autowired
    private Environment env;

    @GetMapping("/cust2CGmapping")
    public String getCli() throws ExecutionException, InterruptedException {

	
	   
    	
    	CustomerSetCustomerGroupAction customerGroupAction = CustomerSetCustomerGroupActionBuilder.of()
	   .customerGroup(CustomerGroupResourceIdentifierBuilder.of().key("silver").build())
	   .build();
	   
	   Long version = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
				.customers()				
				.withKey("Melena82")
				.get()
				.execute()
				.get().getBody().getVersion();
	   
	   CustomerUpdate custUpdate = CustomerUpdateBuilder.of()
			   .version(version)
			   .actions(customerGroupAction)
			   .build();
	   
	   Customer custU = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
				.customers()				
				.withKey("Melena82")
				.post(custUpdate)
				.executeBlocking()
				.getBody();
		  
        return custU.getCustomerGroup().toString();
    }        
}
