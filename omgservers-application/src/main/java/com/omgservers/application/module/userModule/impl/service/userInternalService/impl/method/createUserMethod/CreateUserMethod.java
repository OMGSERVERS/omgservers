package com.omgservers.application.module.userModule.impl.service.userInternalService.impl.method.createUserMethod;

import com.omgservers.application.module.userModule.impl.service.userInternalService.request.CreateUserInternalRequest;
import io.smallrye.mutiny.Uni;

public interface CreateUserMethod {
    Uni<Void> createUser(CreateUserInternalRequest request);
}
