package com.omgservers.service.module.user.impl.service.userService.impl.method.validateCredentials;

import com.omgservers.model.dto.user.ValidateCredentialsResponse;
import com.omgservers.model.dto.user.ValidateCredentialsRequest;
import io.smallrye.mutiny.Uni;

public interface ValidateCredentialsMethod {
    Uni<ValidateCredentialsResponse> validateCredentials(ValidateCredentialsRequest request);
}
