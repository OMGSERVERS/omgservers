package com.omgservers.module.user.impl.service.userService;

import com.omgservers.dto.user.RespondClientRequest;
import com.omgservers.dto.user.SyncUserRequest;
import com.omgservers.dto.user.SyncUserResponse;
import com.omgservers.dto.user.ValidateCredentialsRequest;
import com.omgservers.dto.user.ValidateCredentialsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

import java.time.Duration;

public interface UserService {

    Uni<SyncUserResponse> syncUser(@Valid SyncUserRequest request);

    Uni<ValidateCredentialsResponse> validateCredentials(@Valid ValidateCredentialsRequest request);

    Uni<Void> respondClient(@Valid RespondClientRequest request);
}
