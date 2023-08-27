package com.omgservers.module.internal.impl.service.serviceAccountService;

import com.omgservers.dto.internal.ValidateCredentialsResponse;
import com.omgservers.dto.internal.ValidateCredentialsRequest;
import com.omgservers.dto.internal.DeleteServiceAccountHelpRequest;
import com.omgservers.dto.internal.GetServiceAccountHelpRequest;
import com.omgservers.dto.internal.SyncServiceAccountRequest;
import com.omgservers.dto.internal.GetServiceAccountHelpResponse;
import io.smallrye.mutiny.Uni;

public interface ServiceAccountService {

    Uni<GetServiceAccountHelpResponse> getServiceAccount(GetServiceAccountHelpRequest request);

    Uni<Void> syncServiceAccount(SyncServiceAccountRequest request);

    Uni<Void> deleteServiceAccount(DeleteServiceAccountHelpRequest request);

    Uni<ValidateCredentialsResponse> validateCredentials(ValidateCredentialsRequest request);
}
