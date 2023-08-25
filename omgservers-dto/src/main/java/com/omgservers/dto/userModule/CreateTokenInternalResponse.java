package com.omgservers.dto.userModule;

import com.omgservers.model.user.UserTokenModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenInternalResponse {

    UserTokenModel tokenObject;
    @ToString.Exclude
    String rawToken;
    long lifetime;
}
