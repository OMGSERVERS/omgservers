package com.omgservers.application.module.userModule.impl.service.userInternalService.impl.method.validateCredentials;

import com.omgservers.application.module.userModule.impl.service.userInternalService.request.ValidateCredentialsInternalRequest;
import com.omgservers.application.module.userModule.impl.service.userInternalService.response.ValidateCredentialsInternalResponse;
import io.smallrye.mutiny.Uni;

public interface ValidateCredentialsMethod {
    Uni<ValidateCredentialsInternalResponse> validateCredentials(ValidateCredentialsInternalRequest request);
}
