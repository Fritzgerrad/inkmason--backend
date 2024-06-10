package com.frz.inkmason.service;

import com.frz.inkmason.dto.auth.EmailDetailsDto;
import com.frz.inkmason.dto.event.BookingDto;
import com.frz.inkmason.enums.Role;
import com.frz.inkmason.enums.StatusCode;
import com.frz.inkmason.model.event.Booking;
import com.frz.inkmason.model.person.Guest;
import com.frz.inkmason.model.person.User;
import com.frz.inkmason.repository.*;
import com.frz.inkmason.response.BodyResponse;
import com.frz.inkmason.response.Response;
import com.frz.inkmason.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final StaffRepository staffRepository;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final GuestRepository guestRepository;

    public Response createBooking(BookingDto bookingDto, String token){
        Booking booking = Booking.builder()
                .bookingDate(bookingDto.getBookingDate())
                .createdDate(new Date())
                .mode(bookingDto.getMode())
                .contactInformation(bookingDto.getContactInformation())
                .bookingTime(bookingDto.getBookingTime())
                .build();
        EmailDetailsDto emailDetailsDto = new EmailDetailsDto();

        if(token == null || !token.startsWith("Bearer")){
            booking.setBookerRole(Role.guest);
            Guest guest = Guest.builder()
                    .name(bookingDto.getBookerName())
                    .email(bookingDto.getEmail())
                    .logInTime(bookingDto.getLoginTime())
                    .build();
                    Guest savedGuest = guestRepository.save(guest);
                    booking.setBookerId(savedGuest.getId());
            emailDetailsDto.setRecipient(bookingDto.getEmail());
        }
        else{
            BodyResponse<User> response =  jwtUtil.getUserFromToken(token);
            User user = response.getData();
            booking.setBookerRole(Role.customer);
            booking.setBookerName(user.getFirstname() + " " + user.getLastname());
            booking.setBookerId(user.getId());
            emailDetailsDto.setRecipient(user.getEmail());
        }
        bookingRepository.save(booking);
        String body = "Your Booking has been created!";
        String subject = "Booking Confirmation";
        emailDetailsDto.setMessageBody(body);
        emailDetailsDto.setSubject(subject);
        emailService.sendEmail(emailDetailsDto);
        return new BodyResponse<>(StatusCode.successful.getCode(), "Booking created successfully",booking);
    }

    public Response getUnassignedBookings(){
        List<Booking> unassignedBookings = bookingRepository.findByStaffIsNull();
        return new BodyResponse<>(StatusCode.successful.getCode(),"Unassigned bookings",unassignedBookings);
    }

    public Response getBookingById(Long id){
        Booking booking = bookingRepository.findById(id).orElse(null);
        return new BodyResponse<>(StatusCode.successful.getCode(), "Successful",booking);
    }


}
