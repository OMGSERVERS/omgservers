package com.omgservers.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
public class UserTokenContainerModel {

    UserTokenModel tokenObject;
    @ToString.Exclude
    String rawToken;
    long lifetime;
}
