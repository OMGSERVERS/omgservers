package com.omgservers.dto.internalModule;

import com.omgservers.model.serviceAccount.ServiceAccountModel;
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
