package com.omgservers.dto.internal;

import com.omgservers.model.serviceAccount.ServiceAccountModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncServiceAccountOverServersRequest {

    static public void validateSyncServiceAccountOverServersInternalRequest(SyncServiceAccountOverServersRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    ServiceAccountModel serviceAccount;
    List<URI> servers;
}
