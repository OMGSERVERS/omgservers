package com.omgservers.module.user.impl.service.userInternalService.impl.method.validateCredentials;

import com.omgservers.dto.user.ValidateCredentialsShardedResponse;
import com.omgservers.dto.user.ValidateCredentialsShardedRequest;
import io.smallrye.mutiny.Uni;

public interface ValidateCredentialsMethod {
    Uni<ValidateCredentialsShardedResponse> validateCredentials(ValidateCredentialsShardedRequest request);
}
