package com.frz.inkmason.controller;

import com.frz.inkmason.dto.RatingDto;
import com.frz.inkmason.dto.person.CustomerDto;
import com.frz.inkmason.model.person.Customer;
import com.frz.inkmason.response.Response;
import com.frz.inkmason.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final ResponseMaker responseMaker;

    @PostMapping("/new")
    public ResponseEntity<Response>  createCustomer(@RequestBody
                                                        CustomerDto customerDto) {
        return responseMaker.getResponse(
                customerService.addCustomer(customerDto)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getOneCustomer(@PathVariable Long id){
            return responseMaker.getResponse(customerService.getCustomerById(id));
    }

    @PutMapping
    public ResponseEntity<Response> updateCustomer(
            @RequestBody CustomerDto customerDto,
            @RequestHeader("Authorization") String token
    ){
        return responseMaker.getResponse(customerService.updateCustomer(customerDto,token));
    }

    @GetMapping("/bookings")
    public ResponseEntity<Response> getBookings(@RequestHeader("Authorization") String token){
        return responseMaker.getResponse(customerService.getBookings(token));
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Response> deleteCustomer(@PathVariable Long id){
        return responseMaker.getResponse(customerService.deleteCustomer(id));
    }

    @PostMapping("rate/{id}")
    public  ResponseEntity<Response> rateArtist(
            @RequestHeader("Authorization") String token,
            @RequestBody RatingDto ratingDto,
            @PathVariable Long id
    ){
        return responseMaker.getResponse(customerService.rateArtist(token,ratingDto,id));
    }

    @PostMapping("/staff/all")
    public ResponseEntity<Response> getAllCustomers(){
        return responseMaker.getResponse(customerService.getAllCustomers());
    }
}

