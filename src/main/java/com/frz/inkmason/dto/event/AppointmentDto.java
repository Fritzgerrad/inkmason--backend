package com.frz.inkmason.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {
    private String description;
    private String title;
    private Date createdDate;
    private Date startDate;
    private String time;
    private String location;
    private String amount;
    private boolean homeCall;
}
