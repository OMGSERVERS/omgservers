package com.omgservers.model.serviceAccount;

import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceAccountModel {

    public static void validate(ServiceAccountModel serviceAccount) {
        if (serviceAccount == null) {
            throw new ServerSideBadRequestException("serviceAccount is null");
        }
    }

    Long id;
    Instant created;
    Instant modified;
    String username;
    @ToString.Exclude
    String passwordHash;
}
