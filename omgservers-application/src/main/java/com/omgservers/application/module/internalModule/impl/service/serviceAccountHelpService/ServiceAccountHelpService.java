package com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService;

import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.DeleteServiceAccountHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.GetServiceAccountHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.SyncServiceAccountHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.ValidateCredentialsHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.response.GetServiceAccountHelpResponse;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.response.ValidateCredentialsHelpResponse;
import io.smallrye.mutiny.Uni;

public interface ServiceAccountHelpService {

    Uni<GetServiceAccountHelpResponse> getServiceAccount(GetServiceAccountHelpRequest request);

    Uni<Void> syncServiceAccount(SyncServiceAccountHelpRequest request);

    Uni<Void> deleteServiceAccount(DeleteServiceAccountHelpRequest request);

    Uni<ValidateCredentialsHelpResponse> validateCredentials(ValidateCredentialsHelpRequest request);
}
