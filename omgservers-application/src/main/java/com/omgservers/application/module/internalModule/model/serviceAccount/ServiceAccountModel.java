package com.omgservers.application.module.internalModule.model.serviceAccount;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceAccountModel {

    static public ServiceAccountModel create(String username, String passwordsHash) {
        return create(UUID.randomUUID(), username, passwordsHash);
    }

    static public ServiceAccountModel create(UUID uuid, String username, String passwordsHash) {
        Instant now = Instant.now();
        ServiceAccountModel serviceAccountModel = new ServiceAccountModel();
        serviceAccountModel.setUuid(uuid);
        serviceAccountModel.setCreated(now);
        serviceAccountModel.setModified(now);
        serviceAccountModel.setUsername(username);
        serviceAccountModel.setPasswordHash(passwordsHash);
        return serviceAccountModel;
    }

    static public void validateServiceAccountModel(ServiceAccountModel serviceAccount) {
        if (serviceAccount == null) {
            throw new ServerSideBadRequestException("serviceAccount is null");
        }
    }

    UUID uuid;
    @ToString.Exclude
    Instant created;
    @ToString.Exclude
    Instant modified;
    String username;
    @ToString.Exclude
    String passwordHash;
}
