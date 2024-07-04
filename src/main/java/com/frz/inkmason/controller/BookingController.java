package com.frz.inkmason.controller;

import com.frz.inkmason.dto.event.BookingDto;
import com.frz.inkmason.response.Response;
import com.frz.inkmason.service.BookingService;
import com.frz.inkmason.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor

@RestController
@RequestMapping("auth/booking")
public class BookingController {
    private final BookingService bookingService;
    private final ResponseMaker responseMaker;
    private final CustomerService customerService;

    @PostMapping("/new")
    public ResponseEntity<Response> createBooking(
            @RequestBody BookingDto bookingDto,
            @RequestHeader("Authorization") String token
    ){
        System.out.println(bookingDto.toString());
        return responseMaker.getResponse(bookingService.createBooking(bookingDto,token));
    }

    @GetMapping("/unassigned")
    public ResponseEntity<Response> getUnassignedBookings(){
        return responseMaker.getResponse(bookingService.getUnassignedBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getBooking(@PathVariable Long id){
        return responseMaker.getResponse(bookingService.getBookingById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getCompletedBookings(@RequestHeader("Authorization") String token){
        return responseMaker.getResponse(customerService.getCompletedBookings(token));
    }

    @GetMapping("/pending")
    public ResponseEntity<Response> getOngoingBookings(@RequestHeader("Authorization") String token){
        return responseMaker.getResponse(customerService.getOngoingBookings(token));
    }

}
