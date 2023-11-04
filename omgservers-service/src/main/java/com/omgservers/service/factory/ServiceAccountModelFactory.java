package com.omgservers.service.factory;

import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.model.serviceAccount.ServiceAccountModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ServiceAccountModelFactory {

    final GenerateIdOperation generateIdOperation;

    public ServiceAccountModel create(String username, String passwordsHash) {
        final var id = generateIdOperation.generateId();
        return create(id, username, passwordsHash);
    }

    public ServiceAccountModel create(Long id, String username, String passwordsHash) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        ServiceAccountModel serviceAccountModel = new ServiceAccountModel();
        serviceAccountModel.setId(id);
        serviceAccountModel.setCreated(now);
        serviceAccountModel.setModified(now);
        serviceAccountModel.setUsername(username);
        serviceAccountModel.setPasswordHash(passwordsHash);
        return serviceAccountModel;
    }
}
