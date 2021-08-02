package com.ctwork.poc.customer;

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
import com.commercetools.api.defaultconfig.ApiFactory;
import com.commercetools.api.defaultconfig.ServiceRegion;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerCreateEmailToken;
import com.commercetools.api.models.customer.CustomerCreateEmailTokenBuilder;
import com.commercetools.api.models.customer.CustomerDraft;
import com.commercetools.api.models.customer.CustomerDraftBuilder;
import com.commercetools.api.models.customer.CustomerSetCustomFieldAction;
import com.commercetools.api.models.customer.CustomerSetCustomFieldActionBuilder;
import com.commercetools.api.models.customer.CustomerSetCustomTypeAction;
import com.commercetools.api.models.customer.CustomerSetCustomTypeActionBuilder;
import com.commercetools.api.models.customer.CustomerToken;
import com.commercetools.api.models.customer.CustomerTokenBuilder;
import com.commercetools.api.models.customer.CustomerUpdate;
import com.commercetools.api.models.customer.CustomerUpdateBuilder;
import com.commercetools.api.models.type.CustomFieldsDraft;
import com.commercetools.api.models.type.CustomFieldsDraftBuilder;
import com.commercetools.api.models.type.FieldContainerBuilder;
import com.commercetools.api.models.type.TypeResourceIdentifierBuilder;

import io.vrap.rmf.base.client.oauth2.ClientCredentials;


//import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/")
@PropertySource("classpath:ctp.properties")
public class DemoCustomerUpdateWithUpdateCustom {

	
	@Autowired 
	private ApiRoot ApiClient;
	 
    @Autowired
    private Environment env;

    @SuppressWarnings("unchecked")
	@GetMapping("/customCustomerUpdate")
    public String getCli() throws ExecutionException, InterruptedException {
	 
		/*
		 * CustomerSetCustomTypeAction cuSCTA = CustomerSetCustomTypeActionBuilder.of()
		 * .type(TypeResourceIdentifierBuilder.of().key("Custom-TS-Customer").build())
		 * .fields(FieldContainerBuilder.of().addValue("Tshirt-size", "44").build())
		 * .build();
		 */
    		
    	Map m = new HashMap();
    	m.put("Tshirt-size", "43");
		
		 
    	//NEED TO REWORK ON THIS
    	CustomerSetCustomFieldAction cuSCFA = CustomerSetCustomFieldActionBuilder.of() 
				  .value(FieldContainerBuilder.of().values(m).build())
				  .build();
				  

	   
		  CustomerUpdate custUpdate = CustomerUpdateBuilder.of()
 			   .version((long) 1) 			   
 			   .actions(cuSCFA)
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
