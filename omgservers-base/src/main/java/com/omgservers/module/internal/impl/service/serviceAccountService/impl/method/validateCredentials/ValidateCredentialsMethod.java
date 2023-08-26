package com.omgservers.module.internal.impl.service.serviceAccountService.impl.method.validateCredentials;

import com.omgservers.dto.internalModule.ValidateCredentialsRequest;
import com.omgservers.dto.internalModule.ValidateCredentialsResponse;
import io.smallrye.mutiny.Uni;

public interface ValidateCredentialsMethod {
    Uni<ValidateCredentialsResponse> validateCredentials(ValidateCredentialsRequest request);
}
