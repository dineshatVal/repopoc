package com.ctwork.poc.customer.customergroup;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercetools.api.client.ApiRoot;
import com.commercetools.api.models.customer_group.CustomerGroup;
import com.commercetools.api.models.customer_group.CustomerGroupDraft;
import com.commercetools.api.models.customer_group.CustomerGroupDraftBuilder;



@RestController
@RequestMapping("/")
@PropertySource("classpath:ctp.properties")
public class DemoCustomerGroup {

	
	@Autowired 
	private ApiRoot ApiClient;
	 
    @Autowired
    private Environment env;

    @GetMapping("/custGroup")
    public String getCli() throws ExecutionException, InterruptedException {

	
	   CustomerGroupDraft custGroupDraft = CustomerGroupDraftBuilder.of()
			   .groupName("Normal")
			   .key("normal")
			   .build();
			   
	   
	   CustomerGroup customerGroup = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
			   .customerGroups()
			   .post(custGroupDraft)
			   .executeBlocking()
			   .getBody();
		  
        return customerGroup.getId();
    }        
}
