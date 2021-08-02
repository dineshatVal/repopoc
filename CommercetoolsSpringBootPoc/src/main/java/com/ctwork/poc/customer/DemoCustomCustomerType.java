package com.ctwork.poc.customer;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercetools.api.client.ApiRoot;
import com.commercetools.api.models.common.LocalizedStringBuilder;
import com.commercetools.api.models.type.CustomFieldStringType;
import com.commercetools.api.models.type.CustomFieldStringTypeBuilder;
import com.commercetools.api.models.type.FieldDefinition;
import com.commercetools.api.models.type.FieldDefinitionBuilder;
import com.commercetools.api.models.type.FieldType;
import com.commercetools.api.models.type.ResourceTypeId;
import com.commercetools.api.models.type.Type;
import com.commercetools.api.models.type.TypeDraft;
import com.commercetools.api.models.type.TypeDraftBuilder;

import io.vrap.rmf.base.client.ApiHttpResponse;


//import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/")
@PropertySource("classpath:ctp.properties")
public class DemoCustomCustomerType {

	
	@Autowired 
	private ApiRoot ApiClient;
	 
    @Autowired
    private Environment env;

    @GetMapping("/customCustomerType")
    public String getCli() throws ExecutionException, InterruptedException {
    	
    	FieldType fieldType = CustomFieldStringTypeBuilder.of().build();
    			
    	
    	FieldDefinition fieldDef = FieldDefinitionBuilder.of()
    			.type(fieldType)
    			.name("Tshirt-size")
    			.label(LocalizedStringBuilder.of().addValue("Tshirt", "Tsirt Size").build())
    			.required(false)
    			.build();
    	
    	TypeDraft typeDraft = TypeDraftBuilder.of()
    			.key("Custom-TS-Customer")
    			.name(LocalizedStringBuilder.of().addValue("en", "Tsirt Size Type").build())
    			.resourceTypeIds(ResourceTypeId.CUSTOMER)
    			.fieldDefinitions(fieldDef)
    			.build();
    			
		ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
					.types()
					.post(typeDraft)
					.executeBlocking()
					.getBody();
    	
    	
    	/*
		 * CustomFieldsDraft cusomFDraft = CustomFieldsDraftBuilder.of()
		 * .type(TypeResourceIdentifierBuilder.of().)
		 */
    	
    	
		/*
		 * CustomerDraft custDraft = CustomerDraftBuilder.of()
		 * .email("melena89@yahoo.com") .firstName("Melanie") .lastName("Holzberg")
		 * .password("whoami") .custo .build();
		 * 
		 * 
		 * Customer customer =
		 * ApiClient.withProjectKey(env.getProperty("ctp.projectkey")) .customers()
		 * .post(custDraft) .executeBlocking() .getBody() .getCustomer();
		 */
    	
		
 	   return " ";
    }
}
