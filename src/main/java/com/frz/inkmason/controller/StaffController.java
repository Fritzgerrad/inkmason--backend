package com.frz.inkmason.controller;

import com.frz.inkmason.response.Response;
import com.frz.inkmason.service.StaffService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/staff")
public class StaffController {
    private final ResponseMaker responseMaker;
    private StaffService staffService;

    @GetMapping("/pickup/{bookingId}")
    public ResponseEntity<Response> pickupBooking(
            @RequestHeader("Authorization") String token,
            @PathVariable Long bookingId){
        return responseMaker.getResponse(staffService.pickupBooking(bookingId,token));
    }

    @GetMapping("/bookings")
    public ResponseEntity<Response> getStaffBookings(
            @RequestHeader("Authorization") String token){
        return responseMaker.getResponse(staffService.getStaffBookings(token));
    }
}
