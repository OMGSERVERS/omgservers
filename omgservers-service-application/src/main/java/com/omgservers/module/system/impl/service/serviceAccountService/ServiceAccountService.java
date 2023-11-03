package com.omgservers.module.system.impl.service.serviceAccountService;

import com.omgservers.model.dto.system.ValidateCredentialsResponse;
import com.omgservers.model.dto.system.ValidateCredentialsRequest;
import com.omgservers.model.dto.system.DeleteServiceAccountRequest;
import com.omgservers.model.dto.system.GetServiceAccountRequest;
import com.omgservers.model.dto.system.SyncServiceAccountRequest;
import com.omgservers.model.dto.system.GetServiceAccountResponse;
import io.smallrye.mutiny.Uni;

public interface ServiceAccountService {

    Uni<GetServiceAccountResponse> getServiceAccount(GetServiceAccountRequest request);

    Uni<Void> syncServiceAccount(SyncServiceAccountRequest request);

    Uni<Void> deleteServiceAccount(DeleteServiceAccountRequest request);

    Uni<ValidateCredentialsResponse> validateCredentials(ValidateCredentialsRequest request);
}
