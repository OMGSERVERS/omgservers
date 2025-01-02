package com.omgservers.dispatcher.service.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenResponse {

    @ToString.Exclude
    String rawToken;
}
