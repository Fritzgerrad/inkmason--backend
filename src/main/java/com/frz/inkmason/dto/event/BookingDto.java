package com.frz.inkmason.dto.event;

import com.frz.inkmason.enums.BookingMode;
import com.frz.inkmason.model.person.Staff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private String customerUsername;
    private BookingMode mode;
    private Date createdDate;
    private Date bookingDate;
    private String contactInformation;
    private String bookingTime;
    private boolean guest;
}
