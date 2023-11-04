package com.omgservers.model.dto.developer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenDeveloperResponse {

    @ToString.Exclude
    String rawToken;
}
