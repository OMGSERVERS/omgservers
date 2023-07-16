package com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.impl.method.validateCredentialsMethod;

import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.ValidateCredentialsHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.response.ValidateCredentialsHelpResponse;
import io.smallrye.mutiny.Uni;

public interface ValidateCredentialsMethod {
    Uni<ValidateCredentialsHelpResponse> validateCredentials(ValidateCredentialsHelpRequest request);
}
