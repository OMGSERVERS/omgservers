package com.omgservers.base.impl.service.serviceAccountHelpService.impl.method.getServiceAccountMethod;

import com.omgservers.dto.internalModule.GetServiceAccountHelpRequest;
import com.omgservers.dto.internalModule.GetServiceAccountHelpResponse;
import io.smallrye.mutiny.Uni;

public interface GetServiceAccountMethod {
    Uni<GetServiceAccountHelpResponse> getServiceAccount(GetServiceAccountHelpRequest request);
}
