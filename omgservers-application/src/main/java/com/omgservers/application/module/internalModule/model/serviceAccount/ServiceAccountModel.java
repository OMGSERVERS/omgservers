package com.omgservers.application.module.internalModule.model.serviceAccount;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceAccountModel {

    static public void validate(ServiceAccountModel serviceAccount) {
        if (serviceAccount == null) {
            throw new ServerSideBadRequestException("serviceAccount is null");
        }
    }

    Long id;
    @ToString.Exclude
    Instant created;
    @ToString.Exclude
    Instant modified;
    String username;
    @ToString.Exclude
    String passwordHash;
}
