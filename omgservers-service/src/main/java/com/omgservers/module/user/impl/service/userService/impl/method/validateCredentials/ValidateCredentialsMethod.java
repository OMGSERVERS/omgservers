package com.omgservers.module.user.impl.service.userService.impl.method.validateCredentials;

import com.omgservers.dto.user.ValidateCredentialsResponse;
import com.omgservers.dto.user.ValidateCredentialsRequest;
import io.smallrye.mutiny.Uni;

public interface ValidateCredentialsMethod {
    Uni<ValidateCredentialsResponse> validateCredentials(ValidateCredentialsRequest request);
}
