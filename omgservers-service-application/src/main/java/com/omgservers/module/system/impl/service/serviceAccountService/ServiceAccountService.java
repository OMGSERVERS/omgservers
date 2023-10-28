package com.omgservers.module.system.impl.service.serviceAccountService;

import com.omgservers.model.dto.internal.ValidateCredentialsResponse;
import com.omgservers.model.dto.internal.ValidateCredentialsRequest;
import com.omgservers.model.dto.internal.DeleteServiceAccountRequest;
import com.omgservers.model.dto.internal.GetServiceAccountRequest;
import com.omgservers.model.dto.internal.SyncServiceAccountRequest;
import com.omgservers.model.dto.internal.GetServiceAccountResponse;
import io.smallrye.mutiny.Uni;

public interface ServiceAccountService {

    Uni<GetServiceAccountResponse> getServiceAccount(GetServiceAccountRequest request);

    Uni<Void> syncServiceAccount(SyncServiceAccountRequest request);

    Uni<Void> deleteServiceAccount(DeleteServiceAccountRequest request);

    Uni<ValidateCredentialsResponse> validateCredentials(ValidateCredentialsRequest request);
}
