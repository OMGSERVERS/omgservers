package com.omgservers.application.module.internalModule.impl.service.syncInternalService.request;

import com.omgservers.application.module.internalModule.model.serviceAccount.ServiceAccountModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncServiceAccountOverServersInternalRequest {

    static public void validateSyncServiceAccountOverServersInternalRequest(SyncServiceAccountOverServersInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    ServiceAccountModel serviceAccount;
    List<URI> servers;
}
