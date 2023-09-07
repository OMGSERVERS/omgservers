package com.omgservers.module.system.impl.service.serviceAccountService.impl.method.validateCredentials;

import com.omgservers.dto.internal.ValidateCredentialsRequest;
import com.omgservers.dto.internal.ValidateCredentialsResponse;
import io.smallrye.mutiny.Uni;

public interface ValidateCredentialsMethod {
    Uni<ValidateCredentialsResponse> validateCredentials(ValidateCredentialsRequest request);
}
