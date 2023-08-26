package com.omgservers.module.internal.impl.service.serviceAccountService;

import com.omgservers.dto.internalModule.ValidateCredentialsResponse;
import com.omgservers.dto.internalModule.ValidateCredentialsRequest;
import com.omgservers.dto.internalModule.DeleteServiceAccountHelpRequest;
import com.omgservers.dto.internalModule.GetServiceAccountHelpRequest;
import com.omgservers.dto.internalModule.SyncServiceAccountRequest;
import com.omgservers.dto.internalModule.GetServiceAccountHelpResponse;
import io.smallrye.mutiny.Uni;

public interface ServiceAccountService {

    Uni<GetServiceAccountHelpResponse> getServiceAccount(GetServiceAccountHelpRequest request);

    Uni<Void> syncServiceAccount(SyncServiceAccountRequest request);

    Uni<Void> deleteServiceAccount(DeleteServiceAccountHelpRequest request);

    Uni<ValidateCredentialsResponse> validateCredentials(ValidateCredentialsRequest request);
}
