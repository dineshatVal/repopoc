package com.ctwork.poc.customer;

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
import com.commercetools.api.models.customer.CustomerToken;
import com.commercetools.api.models.customer.CustomerTokenBuilder;
import com.commercetools.api.models.type.CustomFieldsDraft;
import com.commercetools.api.models.type.CustomFieldsDraftBuilder;
import com.commercetools.api.models.type.FieldContainerBuilder;
import com.commercetools.api.models.type.TypeResourceIdentifierBuilder;

import io.vrap.rmf.base.client.oauth2.ClientCredentials;


//import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/")
@PropertySource("classpath:ctp.properties")
public class DemoCustomerWithAndWithoutCustom {

	
	@Autowired 
	private ApiRoot ApiClient;
	 
    @Autowired
    private Environment env;

    @GetMapping("/customCustomer")
    public String getCli() throws ExecutionException, InterruptedException {
	 
		  
	   CustomerDraft custDraft = CustomerDraftBuilder.of()
			   .email("melena78@yahoo.com")
			   .firstName("Melany")
			   .lastName("Jenif")
			   .password("whoami")
			   .key("Melena78")
			   .build();
		
	   
	   Customer customerWithoutAttr = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
			   .customers()
			   .post(custDraft)
			   .executeBlocking()
			   .getBody()
			   .getCustomer();	  
 	   
	   //CustomerToken custToken = new DemoCustomerWithAndWithoutCustom().getCustToken(ApiClient, customer, env);
		  
       
	   CustomFieldsDraft custFieldsDraft = CustomFieldsDraftBuilder.of()
    		   .type(TypeResourceIdentifierBuilder.of().key("Custom-TS-Customer").build())
    		   .fields(FieldContainerBuilder.of().addValue("Tshirt-size", "42").build())
    		   .build();
    	
	   custDraft = CustomerDraftBuilder.of()
			   .email("melena82@yahoo.com")
			   .firstName("MelanyAtt")
			   .lastName("JenifAtt")
			   .password("whoami")
			   .key("Melena82")
			   .custom(custFieldsDraft)
			   .build();
	   
	   Customer customerWithAttr = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
			   .customers()
			   .post(custDraft)
			   .executeBlocking()
			   .getBody()
			   .getCustomer();
	   
	   
	   return customerWithoutAttr.getId()+"<----------------->"+customerWithAttr.getId();
    }
    
    public CustomerToken getCustToken(ApiRoot apiRoot, Customer customer, Environment env) {
    	
    	CustomerCreateEmailToken cEmailToken = CustomerCreateEmailTokenBuilder.of()
 			   .id(customer.getId())
 			   .ttlMinutes((long) 30)
 			   .build();
 	   
 	   
 	   
 	   CustomerToken custToken = apiRoot.withProjectKey(env.getProperty("ctp.projectkey"))
 			   .customers()
 			   .emailToken()
 			   .post(cEmailToken)
 			   .executeBlocking()
 			   .getBody();
 	   
 	   return custToken;
    }
}
