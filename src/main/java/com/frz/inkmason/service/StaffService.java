package com.frz.inkmason.service;

import com.frz.inkmason.enums.StatusCode;
import com.frz.inkmason.model.event.Booking;
import com.frz.inkmason.model.person.Staff;
import com.frz.inkmason.model.person.User;
import com.frz.inkmason.repository.BookingRepository;
import com.frz.inkmason.repository.StaffRepository;
import com.frz.inkmason.repository.UserRepository;
import com.frz.inkmason.response.BodyResponse;
import com.frz.inkmason.response.LocalResponse;
import com.frz.inkmason.response.Response;
import com.frz.inkmason.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StaffService {
    private final StaffRepository staffRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final JwtUtil jwtUtil;

    public Response pickupBooking(Long bookingId, String token){
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking==null){
            return new LocalResponse(StatusCode.badRequest.getCode(), "Booking not found");
        }
        BodyResponse<User> response =  jwtUtil.getUserFromToken(token);
        User user = response.getData();
        Staff staff = staffRepository.findByUser(user);
        booking.setStaff(staff);

        return new LocalResponse(StatusCode.successful.getCode(), "Booking successfully picked");
    }

    public Response getStaffBookings(String token){
        BodyResponse<User> response =  jwtUtil.getUserFromToken(token);
        User user = response.getData();
        Staff staff = staffRepository.findByUser(user);

        List<Booking> bookings = bookingRepository.findByStaff(staff);

        return new BodyResponse<>(StatusCode.successful.getCode(), "Bookings Fetched",bookings);

    }
}
