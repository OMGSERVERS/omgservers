package com.omgservers.dto.adminModule;

import com.omgservers.model.serviceAccount.ServiceAccountModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncServiceAccountAdminRequest {

    static public void validate(SyncServiceAccountAdminRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    ServiceAccountModel serviceAccount;
}
