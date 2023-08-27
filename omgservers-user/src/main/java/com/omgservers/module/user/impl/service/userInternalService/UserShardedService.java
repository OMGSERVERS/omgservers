package com.omgservers.module.user.impl.service.userInternalService;

import com.omgservers.dto.user.SyncUserShardedResponse;
import com.omgservers.dto.user.SyncUserShardedRequest;
import com.omgservers.dto.user.ValidateCredentialsShardedResponse;
import com.omgservers.dto.user.ValidateCredentialsShardedRequest;
import io.smallrye.mutiny.Uni;

public interface UserShardedService {

    Uni<SyncUserShardedResponse> syncUser(SyncUserShardedRequest request);

    Uni<ValidateCredentialsShardedResponse> validateCredentials(ValidateCredentialsShardedRequest request);
}
