package com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.impl.method.getServiceAccountMethod;

import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.GetServiceAccountHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.response.GetServiceAccountHelpResponse;
import io.smallrye.mutiny.Uni;

public interface GetServiceAccountMethod {
    Uni<GetServiceAccountHelpResponse> getServiceAccount(GetServiceAccountHelpRequest request);
}
