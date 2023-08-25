package com.omgservers.dto.userModule;

import com.omgservers.model.user.UserTokenModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntrospectTokenInternalResponse {

    UserTokenModel tokenObject;
    long lifetime;
}
