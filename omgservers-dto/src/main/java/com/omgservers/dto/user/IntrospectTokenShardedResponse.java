package com.omgservers.dto.user;

import com.omgservers.model.user.UserTokenModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntrospectTokenShardedResponse {

    UserTokenModel tokenObject;
    long lifetime;
}
