package com.omgservers.application.module.userModule.impl.service.userInternalService;

import com.omgservers.dto.userModule.SyncUserShardRequest;
import com.omgservers.dto.userModule.SyncUserInternalResponse;
import com.omgservers.dto.userModule.ValidateCredentialsShardRequest;
import com.omgservers.dto.userModule.ValidateCredentialsInternalResponse;
import io.smallrye.mutiny.Uni;

public interface UserInternalService {

    Uni<SyncUserInternalResponse> syncUser(SyncUserShardRequest request);

    Uni<ValidateCredentialsInternalResponse> validateCredentials(ValidateCredentialsShardRequest request);
}
