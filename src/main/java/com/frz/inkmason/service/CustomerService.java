package com.frz.inkmason.service;

import com.frz.inkmason.dto.RatingDto;
import com.frz.inkmason.dto.person.CustomerDto;
import com.frz.inkmason.enums.StatusCode;
import com.frz.inkmason.model.Rating;
import com.frz.inkmason.model.event.Booking;
import com.frz.inkmason.model.person.Artist;
import com.frz.inkmason.model.person.Customer;
import com.frz.inkmason.model.person.User;
import com.frz.inkmason.repository.*;
import com.frz.inkmason.response.AuthResponseBody;
import com.frz.inkmason.response.BodyResponse;
import com.frz.inkmason.response.LocalResponse;
import com.frz.inkmason.response.Response;
import com.frz.inkmason.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final ArtistRepository artistRepository;
    private final RatingRepository ratingRepository;

    public Response getAllCustomers(){
        return new BodyResponse<>(StatusCode.successful.getCode(), "Successful",customerRepository.findAll());
    }

    public Response getCustomerById(Long id){
        return new BodyResponse<>(StatusCode.successful.getCode(), "Successful",customerRepository.findById(id));
    }

    public Response addCustomer(CustomerDto customerDto){
        Customer customer = Customer.builder()
                .allowsNewsLetter(customerDto.isAllowsNewsLetter())
                .build();
        User user = userService.newUser(customerDto);
        if (user == null) {
            return new LocalResponse(StatusCode.badRequest.getCode(),"User Already Exists");
        }
        customer.setUser(user);
        userService.handleUser2FA(user );
        customerRepository.save(customer);
        String token = jwtUtil.generateToken(user);

        return new BodyResponse<>(StatusCode.successful.getCode(), "Account Created Successfully",
                new AuthResponseBody(token, user.getFirstname(), user.getRole(), false, user.getId()));
    }

    public Response updateCustomer(CustomerDto customerDto,String token){

        BodyResponse<User> response =  jwtUtil.getUserFromToken(token);
        if(response.getStatusCode() == StatusCode.successful.getCode()){
            Customer customer = customerRepository.findByUser(response.getData());
            if(customer == null){
                return new LocalResponse(StatusCode.badRequest.getCode(), "Customer not Found");
            }
            customer.setAllowsNewsLetter(customerDto.isAllowsNewsLetter());
            customerRepository.save(customer);
            return new LocalResponse(StatusCode.successful.getCode(), "Successful");
        }
        else {
            return response;
        }
    }

    public Response deleteCustomer(Long id){

        Customer customer = customerRepository.findById(id).orElse(null);
        if(customer == null){
            return new LocalResponse(StatusCode.badRequest.getCode(), "Customer Not Found");
        }
        User user  = customer.getUser();
        customerRepository.delete(customer);
        userRepository.delete(user);
        return new LocalResponse(StatusCode.successful.getCode(), "Successful");
    }

    public Response getBookings(String token){
        BodyResponse<User> response = jwtUtil.getUserFromToken(token);
        if(response.getStatusCode() == StatusCode.successful.getCode()) {
            User user = response.getData();
            List<Booking> bookings = bookingRepository.findByBookerId(user.getId());
            return new BodyResponse<>(StatusCode.successful.getCode(), "    All bookings fetched successfully", bookings);
        }
        else{
            return response;
        }
    }

   public Response rateArtist(String token, RatingDto ratingDto, Long artistId){
        User user = jwtUtil.getUserFromToken(token).getData();
       Customer customer = customerRepository.findByUser(user);

       Artist artist = artistRepository.findById(artistId).orElse(null);
       if(artist == null){
           return new LocalResponse(StatusCode.badRequest.getCode(), "Artist Not Found");
       }

       Rating rating = Rating.builder()
               .artist(artist)
               .customer(customer)
               .comment(ratingDto.getComment())
               .createdDate(new Date())
               .score(ratingDto.getScore())
               .build();
       ratingRepository.save(rating);

       List<Rating> artistRatings = ratingRepository.findByArtist(artist);

       double sumOfRatings = 0.0;
       for (Rating rating1 : artistRatings) {
           sumOfRatings += rating1.getScore();
       }
       double averageRating =sumOfRatings/artistRatings.size();
       artist.setAverageRating(averageRating);
       artistRepository.save(artist);
       return new LocalResponse(StatusCode.successful.getCode(), "Successful");
   }


}
