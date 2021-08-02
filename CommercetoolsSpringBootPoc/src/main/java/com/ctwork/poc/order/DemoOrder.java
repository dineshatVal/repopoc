package com.ctwork.poc.order;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.commercetools.api.client.ApiRoot;
import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CartDraft;
import com.commercetools.api.models.cart.CartDraftBuilder;
import com.commercetools.api.models.cart.CartResourceIdentifier;
import com.commercetools.api.models.cart.CartResourceIdentifierBuilder;
import com.commercetools.api.models.cart.LineItemDraft;
import com.commercetools.api.models.cart.LineItemDraftBuilder;
import com.commercetools.api.models.cart.ShippingInfo;
import com.commercetools.api.models.cart.ShippingInfoBuilder;
import com.commercetools.api.models.cart.ShippingMethodState.ShippingMethodStateEnum;
import com.commercetools.api.models.common.Address;
import com.commercetools.api.models.common.AddressBuilder;
import com.commercetools.api.models.common.CentPrecisionMoney;
import com.commercetools.api.models.common.CentPrecisionMoneyBuilder;
import com.commercetools.api.models.common.TypedMoney;
import com.commercetools.api.models.common.TypedMoneyDraft;
import com.commercetools.api.models.order.Order;
import com.commercetools.api.models.order.OrderFromCartDraft;
import com.commercetools.api.models.order.OrderFromCartDraftBuilder;
import com.commercetools.api.models.order.ShipmentState;
import com.commercetools.api.models.order.ShipmentState.ShipmentStateEnum;
import com.commercetools.api.models.shipping_method.CartScoreTier;
import com.commercetools.api.models.shipping_method.CartScoreTierBuilder;
import com.commercetools.api.models.shipping_method.ShippingMethod;
import com.commercetools.api.models.shipping_method.ShippingMethodBuilder;
import com.commercetools.api.models.shipping_method.ShippingRate;
import com.commercetools.api.models.shipping_method.ShippingRateBuilder;
import com.commercetools.api.models.shipping_method.ShippingRatePriceTier;



//import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/")
@PropertySource("classpath:ctp.properties")
public class DemoOrder {

	
	@Autowired 
	private ApiRoot ApiClient;
	 
    @Autowired
    private Environment env;

    @GetMapping("/order")
    public Order getCli() {	
    	
		
		/*
		 * CartDraft cartDraft = CartDraftBuilder.of() .currency("EUR") .build();
		 */
		 
		 LineItemDraft lineItemDraft = LineItemDraftBuilder.of()
				  .productId("d94e7f47-cb2d-4bab-8b57-50be8daad716").quantity((long) 2).build(); 	
		 Address address = AddressBuilder.of()
				 .country("DE")
				 .building("Hermock")
				 .city("Munich")
				 .build();
		 
		  CartDraft cartDraft = CartDraftBuilder.of().lineItems(lineItemDraft)
				  .currency("EUR")
				  .country("DE")
				  .shippingAddress(address)
				  .build();
		  
		  Cart cartAdd = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
				   .carts()
				   .post(cartDraft)
				   .executeBlocking()
				   .getBody();
		  
		  //ShippingRatePriceTier shipRPT = 
			/*
			 * CartScoreTier cartST = CartScoreTierBuilder.of() .score((double) 10)
			 * .build();
			 * 
			 * CentPrecisionMoney centPrecMoney = CentPrecisionMoneyBuilder.of()
			 * .currencyCode("EUR") .centAmount((long) 1000) .fractionDigits(2) .build();
			 * 
			 * 
			 * ShippingRate shipRate = ShippingRateBuilder.of() .price(centPrecMoney)
			 * .tiers(cartST) .build();
			 */
				  
			/*
			 * ShippingMethod shiMethod = ShippingMethodBuilder.of() .
			 */
		  
			/*
			 * ShippingInfo shipInfo = ShippingInfoBuilder.of() .shippingRate(shipRate)
			 * .shippingMethod(null) .price(centPrecMoney)
			 * .shippingMethodState(ShippingMethodStateEnum.MATCHES_CART) .build();
			 */
		  
		 
    	
    	CartResourceIdentifier cartIdentifier = CartResourceIdentifierBuilder.of()
    			.id(cartAdd.getId())
    			.build();
    	
		/*
		 * ShippingInfo shippingInfo = ShippingInfoBuilder.of() .
		 */
    			
    			
    	OrderFromCartDraft orderDraft = OrderFromCartDraftBuilder.of()
    			.version((long) 1)
    			.cart(cartIdentifier)  
    			.build();   			
    			
    	
		/*
		 * Order order = OrderBuilder.of() .build();
		 */
    	
    	Order order = ApiClient.withProjectKey(env.getProperty("ctp.projectkey"))
		    	.orders()
		    	.post(orderDraft)
		    	.executeBlocking()
		    	.getBody();
    	
    	RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String url = "http://localhost:8082/getOrder";
        HttpEntity<Order> request = new HttpEntity<>(order, headers);
       //restTemplate.pos
       //ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
       //ResponseEntity<Order> response = restTemplate.exchange(url, HttpMethod.POST, request, Order.class);
        ResponseEntity<Order> response = restTemplate.postForEntity(url, request, Order.class);
    	
    	return response.getBody();
	   
    }
}
