package com.example.stripeexample.controller;

import com.example.stripeexample.entity.MyCustomer;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerSearchResult;
import com.stripe.param.CustomerSearchParams;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class Controller {


    private final String  stripeKey ="sk_test_51M0N5GFtmfxzG6wMv5rF80PkxBXT7WTOC9fudwDsAdWwws4buKuvv7XlyNTrtIFlV7RkVOesXFLQ2hUUnmticZCd0016lhtl2c";

    @PostMapping
    public String createCustomer(@RequestBody MyCustomer myCustomer) throws StripeException {

        Stripe.apiKey= stripeKey;
        Map<String, Object> params = new HashMap<>();
        params.put("description",myCustomer.description());
        params.put("email", myCustomer.email());
        params.put("name", myCustomer.name());
        params.put("phone", myCustomer.phoneNumber());
        params.put("balance", myCustomer.balance());

        return Customer.create(params).toJson();
    }

    @GetMapping("/{id}")
    public String getCustomer(@PathVariable("id") String id) throws StripeException {
        Stripe.apiKey=stripeKey;

      return  Customer.retrieve(id).toJson();
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable("id") String id) throws StripeException {
        Stripe.apiKey=stripeKey;
        Customer customer = Customer.retrieve(id);
        return customer.delete().toJson();
    }

    @PutMapping("/{id}")
    public String updateCustomer(@PathVariable("id") String id,@RequestBody MyCustomer myCustomer) throws StripeException {
        Stripe.apiKey=stripeKey;
        Customer customer=Customer.retrieve(id);
        Map<String, Object> params = new HashMap<>();
        params.put("description",myCustomer.description());
        params.put("email", myCustomer.email());
        params.put("name", myCustomer.name());
        params.put("phone", myCustomer.phoneNumber());
        params.put("balance", myCustomer.balance());
        return customer.update(params).toJson();
    }

    @GetMapping()
    public String getAllCustomers(@RequestParam("limit") int limit) throws StripeException {
        Stripe.apiKey=stripeKey;
        Map<String, Object> params = new HashMap<>();
        params.put("limit",limit);
        return Customer.list(params).toJson();
    }

    @GetMapping("/search")
    public String searchCustomers(@RequestParam("name") String name) throws StripeException {
        Stripe.apiKey=stripeKey;
        CustomerSearchParams params =
                CustomerSearchParams
                        .builder()
                        //need custom string to be build "name:'fakename' AND metadata['foo']:'bar'
                        .setQuery("name:'"+name+"'")
                        .build();

        CustomerSearchResult result = Customer.search(params);

        return result.toJson();
    }

}




