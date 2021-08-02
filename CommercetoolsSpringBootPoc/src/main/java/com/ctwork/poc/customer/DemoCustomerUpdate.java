package com.ctwork.poc.customer;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercetools.api.client.ApiRoot;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerCreateEmailToken;
import com.commercetools.api.models.customer.CustomerCreateEmailTokenBuilder;
import com.commercetools.api.models.customer.CustomerSetCustomFieldAction;
import com.commercetools.api.models.customer.CustomerSetCustomFieldActionBuilder;
import com.commercetools.api.models.customer.CustomerSetMiddleNameAction;
import com.commercetools.api.models.customer.CustomerSetMiddleNameActionBuilder;
import com.commercetools.api.models.customer.CustomerUpdate;
import com.commercetools.api.models.customer.CustomerUpdateBuilder;


//import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/")
@PropertySource("classpath:ctp.properties")
public class DemoCustomerUpdate {

	
	@Autowired 
	private ApiRoot ApiClient;
	 
    @Autowired
    private Environment env;

    @GetMapping("/custUpdateCustom")
    public String getCli() throws ExecutionException, InterruptedException {

    			
	   
	   CustomerSetMiddleNameAction cuSMNA = CustomerSetMiddleNameActionBuilder.of()
			   .middleName("Wenny")
			   .build();
	   
	   CustomerUpdate custUpdate = CustomerUpdateBuilder.of()
			   .version((long) 3)
			   .actions(cuSMNA)
			   .build();
			   
		
	   CustomerCreateEmailToken custEmailToken = CustomerCreateEmailTokenBuilder.of()
			   .ttlMinutes((long) 30)
			   .version((long) 3)
			   .build();
	   
	   
	   Customer custU = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
				.customers()				
				.withId("405ab1b3-b3ed-46c4-9dfd-1e918f98f130")
				.post(custUpdate)
				.executeBlocking()
				.getBody();
				
		Customer custU1 = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
				.customers()
				.withId(custU.getId())
				.get()
				.executeBlocking()
				.getBody();		
		  
        return custU.getId();
    }
}
