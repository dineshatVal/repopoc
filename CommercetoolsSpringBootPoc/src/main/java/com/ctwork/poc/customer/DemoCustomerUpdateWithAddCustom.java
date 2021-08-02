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
import com.commercetools.api.models.customer.CustomerSetCustomTypeAction;
import com.commercetools.api.models.customer.CustomerSetCustomTypeActionBuilder;
import com.commercetools.api.models.customer.CustomerUpdate;
import com.commercetools.api.models.customer.CustomerUpdateBuilder;
import com.commercetools.api.models.type.FieldContainerBuilder;
import com.commercetools.api.models.type.TypeResourceIdentifierBuilder;


//import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/")
@PropertySource("classpath:ctp.properties")
public class DemoCustomerUpdateWithAddCustom {

	
	@Autowired 
	private ApiRoot ApiClient;
	 
    @Autowired
    private Environment env;

    @GetMapping("/customCustomerAdd")
    public String getCli() throws ExecutionException, InterruptedException {
	 
        CustomerSetCustomTypeAction cuSCTA = CustomerSetCustomTypeActionBuilder.of()
        		.type(TypeResourceIdentifierBuilder.of().key("Custom-TS-Customer").build())
     		    .fields(FieldContainerBuilder.of().addValue("Tshirt-size", "46").build())
        		.build();
    	
		/*
		 * CustomerSetCustomFieldAction cuSCFA =
		 * CustomerSetCustomFieldActionBuilder.of() .name("Custom-TS-Customer") .v
		 * .build();
		 */
	   
    	Long version = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
			.customers()				
			.withKey("Melena82")
			.get()
			.execute()
			.get().getBody().getVersion();
        
        CustomerUpdate custUpdate = CustomerUpdateBuilder.of()
 			   .version(version++)
 			   .actions(cuSCTA)
 			   .build();
    	
        Customer custU = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
				.customers()				
				.withKey("Melena82")
				.post(custUpdate)
				.executeBlocking()
				.getBody();
        
        
	   //CustomerToken custToken = new DemoCustomerWithAndWithoutCustom().getCustToken(ApiClient, customer, env);
		  
       
		/*
		 * CustomFieldsDraft custFieldsDraft = CustomFieldsDraftBuilder.of()
		 * .type(TypeResourceIdentifierBuilder.of().key("Custom-TS-Customer").build())
		 * .fields(FieldContainerBuilder.of().addValue("Tshirt-size", "40").build())
		 * .build();
		 * 
		 * custDraft = CustomerDraftBuilder.of() .email("melena81@yahoo.com")
		 * .firstName("MelanyAtt") .lastName("JenifAtt") .password("whoami")
		 * .custom(custFieldsDraft) .build();
		 * 
		 * Customer customerWithAttr =
		 * ApiClient.withProjectKey(env.getProperty("ctp.projectkey")) .customers()
		 * .post(custDraft) .executeBlocking() .getBody() .getCustomer();
		 */
	   
	   
	   return custU.getId();
    }
    
    
}
