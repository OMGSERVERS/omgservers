package com.omgservers.model.dto.admin;

import com.omgservers.model.serviceAccount.ServiceAccountModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncServiceAccountAdminRequest {

    @NotNull
    ServiceAccountModel serviceAccount;
}
