package com.omgservers.model.dto.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenPlayerResponse {

    @ToString.Exclude
    String rawToken;
}
