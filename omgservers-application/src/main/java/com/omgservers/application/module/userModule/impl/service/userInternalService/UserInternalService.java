package com.omgservers.application.module.userModule.impl.service.userInternalService;

import com.omgservers.dto.userModule.SyncUserRoutedRequest;
import com.omgservers.dto.userModule.SyncUserInternalResponse;
import com.omgservers.dto.userModule.ValidateCredentialsRoutedRequest;
import com.omgservers.dto.userModule.ValidateCredentialsInternalResponse;
import io.smallrye.mutiny.Uni;

public interface UserInternalService {

    Uni<SyncUserInternalResponse> syncUser(SyncUserRoutedRequest request);

    Uni<ValidateCredentialsInternalResponse> validateCredentials(ValidateCredentialsRoutedRequest request);
}
