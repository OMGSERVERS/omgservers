package com.omgservers.module.internal.impl.service.serviceAccountService;

import com.omgservers.dto.internal.ValidateCredentialsResponse;
import com.omgservers.dto.internal.ValidateCredentialsRequest;
import com.omgservers.dto.internal.DeleteServiceAccountRequest;
import com.omgservers.dto.internal.GetServiceAccountRequest;
import com.omgservers.dto.internal.SyncServiceAccountRequest;
import com.omgservers.dto.internal.GetServiceAccountResponse;
import io.smallrye.mutiny.Uni;

public interface ServiceAccountService {

    Uni<GetServiceAccountResponse> getServiceAccount(GetServiceAccountRequest request);

    Uni<Void> syncServiceAccount(SyncServiceAccountRequest request);

    Uni<Void> deleteServiceAccount(DeleteServiceAccountRequest request);

    Uni<ValidateCredentialsResponse> validateCredentials(ValidateCredentialsRequest request);
}
