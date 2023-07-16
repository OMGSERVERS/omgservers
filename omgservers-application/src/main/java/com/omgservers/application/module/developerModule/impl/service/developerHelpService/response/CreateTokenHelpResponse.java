package com.omgservers.application.module.developerModule.impl.service.developerHelpService.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenHelpResponse {

    @ToString.Exclude
    String rawToken;
}
