package com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request;

import com.omgservers.application.module.internalModule.model.serviceAccount.ServiceAccountModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncServiceAccountHelpRequest {

    static public void validate(SyncServiceAccountHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    ServiceAccountModel serviceAccount;
}
