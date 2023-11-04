package com.omgservers.model.dto.system;

import com.omgservers.model.serviceAccount.ServiceAccountModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncServiceAccountOverServersRequest {

    @NotNull
    ServiceAccountModel serviceAccount;

    @NotNull
    List<URI> servers;
}
