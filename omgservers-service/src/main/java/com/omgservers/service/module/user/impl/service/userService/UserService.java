package com.omgservers.service.module.user.impl.service.userService;

import com.omgservers.model.dto.user.DeleteUserRequest;
import com.omgservers.model.dto.user.DeleteUserResponse;
import com.omgservers.model.dto.user.RespondClientRequest;
import com.omgservers.model.dto.user.SyncUserRequest;
import com.omgservers.model.dto.user.SyncUserResponse;
import com.omgservers.model.dto.user.ValidateCredentialsRequest;
import com.omgservers.model.dto.user.ValidateCredentialsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface UserService {

    Uni<SyncUserResponse> syncUser(@Valid SyncUserRequest request);

    Uni<DeleteUserResponse> deleteUser(@Valid DeleteUserRequest request);

    Uni<ValidateCredentialsResponse> validateCredentials(@Valid ValidateCredentialsRequest request);

    Uni<Void> respondClient(@Valid RespondClientRequest request);
}
