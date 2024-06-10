package com.frz.inkmason.dto.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistDto {
    private String gender;
    private String image;
    private String location;
    private String nickname;
    private long userId;
    private String token;
}
