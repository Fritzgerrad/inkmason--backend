package com.frz.inkmason.response;

import com.frz.inkmason.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ArtistResponseBody {
    private String nickname;
    private String gender;
    private double rating;
    private String image;
    private String location;

}
