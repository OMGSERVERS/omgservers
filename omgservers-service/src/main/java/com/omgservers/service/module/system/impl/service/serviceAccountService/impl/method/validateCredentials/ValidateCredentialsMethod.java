package com.omgservers.service.module.system.impl.service.serviceAccountService.impl.method.validateCredentials;

import com.omgservers.model.dto.system.ValidateCredentialsRequest;
import com.omgservers.model.dto.system.ValidateCredentialsResponse;
import io.smallrye.mutiny.Uni;

public interface ValidateCredentialsMethod {
    Uni<ValidateCredentialsResponse> validateCredentials(ValidateCredentialsRequest request);
}
