package com.omgservers.base.impl.service.serviceAccountHelpService.impl.method.deleteServiceAccountMethod;

import com.omgservers.dto.internalModule.DeleteServiceAccountHelpRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteServiceAccountMethod {
    Uni<Void> deleteServiceAccount(DeleteServiceAccountHelpRequest request);
}
