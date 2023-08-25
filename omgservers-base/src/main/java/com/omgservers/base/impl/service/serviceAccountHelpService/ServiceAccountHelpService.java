package com.omgservers.base.impl.service.serviceAccountHelpService;

import com.omgservers.base.impl.service.serviceAccountHelpService.response.ValidateCredentialsHelpResponse;
import com.omgservers.dto.internalModule.DeleteServiceAccountHelpRequest;
import com.omgservers.dto.internalModule.GetServiceAccountHelpRequest;
import com.omgservers.dto.internalModule.SyncServiceAccountHelpRequest;
import com.omgservers.base.impl.service.serviceAccountHelpService.request.ValidateCredentialsHelpRequest;
import com.omgservers.dto.internalModule.GetServiceAccountHelpResponse;
import io.smallrye.mutiny.Uni;

public interface ServiceAccountHelpService {

    Uni<GetServiceAccountHelpResponse> getServiceAccount(GetServiceAccountHelpRequest request);

    Uni<Void> syncServiceAccount(SyncServiceAccountHelpRequest request);

    Uni<Void> deleteServiceAccount(DeleteServiceAccountHelpRequest request);

    Uni<ValidateCredentialsHelpResponse> validateCredentials(ValidateCredentialsHelpRequest request);
}
