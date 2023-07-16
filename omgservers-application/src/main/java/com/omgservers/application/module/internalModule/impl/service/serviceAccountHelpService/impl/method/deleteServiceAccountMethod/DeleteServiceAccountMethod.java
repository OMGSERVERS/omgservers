package com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.impl.method.deleteServiceAccountMethod;

import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.DeleteServiceAccountHelpRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteServiceAccountMethod {
    Uni<Void> deleteServiceAccount(DeleteServiceAccountHelpRequest request);
}
