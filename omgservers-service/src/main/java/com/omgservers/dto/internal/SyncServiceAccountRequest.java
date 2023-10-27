package com.omgservers.dto.internal;

import com.omgservers.model.serviceAccount.ServiceAccountModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncServiceAccountRequest {

    @NotNull
    ServiceAccountModel serviceAccount;
}
