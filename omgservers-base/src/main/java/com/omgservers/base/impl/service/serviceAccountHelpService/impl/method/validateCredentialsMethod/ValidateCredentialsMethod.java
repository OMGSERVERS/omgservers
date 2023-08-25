package com.omgservers.base.impl.service.serviceAccountHelpService.impl.method.validateCredentialsMethod;

import com.omgservers.base.impl.service.serviceAccountHelpService.request.ValidateCredentialsHelpRequest;
import com.omgservers.base.impl.service.serviceAccountHelpService.response.ValidateCredentialsHelpResponse;
import io.smallrye.mutiny.Uni;

public interface ValidateCredentialsMethod {
    Uni<ValidateCredentialsHelpResponse> validateCredentials(ValidateCredentialsHelpRequest request);
}
