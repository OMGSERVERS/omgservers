package com.omgservers.application.module.userModule.impl.service.userInternalService;

import com.omgservers.dto.userModule.SyncUserInternalRequest;
import com.omgservers.dto.userModule.SyncUserInternalResponse;
import com.omgservers.dto.userModule.ValidateCredentialsInternalRequest;
import com.omgservers.dto.userModule.ValidateCredentialsInternalResponse;
import io.smallrye.mutiny.Uni;

public interface UserInternalService {

    Uni<SyncUserInternalResponse> syncUser(SyncUserInternalRequest request);

    Uni<ValidateCredentialsInternalResponse> validateCredentials(ValidateCredentialsInternalRequest request);
}
