package com.frz.inkmason.controller;

import com.frz.inkmason.dto.event.BookingDto;
import com.frz.inkmason.response.Response;
import com.frz.inkmason.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor

@RestController
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;
    private final ResponseMaker responseMaker;

    @PostMapping
    public ResponseEntity<Response> createBooking(
            @RequestBody BookingDto bookingDto,
            @RequestHeader("Authorization") String token
    ){
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
}
