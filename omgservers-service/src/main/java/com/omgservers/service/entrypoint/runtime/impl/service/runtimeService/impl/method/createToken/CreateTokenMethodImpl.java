package com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.impl.method.createToken;

import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.module.runtime.runtime.GetRuntimeRequest;
import com.omgservers.schema.module.runtime.runtime.GetRuntimeResponse;
import com.omgservers.schema.module.user.CreateTokenRequest;
import com.omgservers.schema.module.user.CreateTokenResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.runtime.CreateOpenRuntimeCommandOperation;
import com.omgservers.service.operation.security.IssueJwtTokenOperation;
import com.omgservers.service.shard.runtime.RuntimeShard;
import com.omgservers.service.shard.user.UserShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateTokenMethodImpl implements CreateTokenMethod {

    final RuntimeShard runtimeShard;
    final UserShard userShard;

    final CreateOpenRuntimeCommandOperation createOpenRuntimeCommandOperation;
    final IssueJwtTokenOperation issueJwtTokenOperation;

    @Override
    public Uni<CreateTokenRuntimeResponse> execute(final CreateTokenRuntimeRequest request) {
        log.trace("{}", request);

        final var runtimeId = request.getRuntimeId();
        final var password = request.getPassword();

        return getRuntime(runtimeId)
                .flatMap(runtime -> {
                    if (runtime.getDeleted()) {
                        throw new ServerSideNotFoundException(ExceptionQualifierEnum.RUNTIME_NOT_FOUND,
                                "runtime already deleted, runtimeId=" + runtimeId);
                    }

                    final var userId = runtime.getUserId();
                    return createUserToken(userId, password)
                            .flatMap(userToken -> createOpenRuntimeCommandOperation.execute(runtime)
                                    .map(created -> {
                                        final var apiToken = issueJwtTokenOperation.issueRuntimeJwtToken(runtimeId);
                                        final var wsToken = issueJwtTokenOperation.issueWsJwtToken(userId,
                                                runtimeId,
                                                UserRoleEnum.RUNTIME);

                                        log.info("Token issued to use by runtime \"{}\"", runtimeId);
                                        return new CreateTokenRuntimeResponse(apiToken, wsToken);
                                    }));
                });
    }

    Uni<String> createUserToken(final Long userId, final String password) {
        final var createTokenRequest = new CreateTokenRequest(userId, password);
        return userShard.getService().execute(createTokenRequest)
                .map(CreateTokenResponse::getRawToken);
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id);
        return runtimeShard.getService().execute(request)
                .map(GetRuntimeResponse::getRuntime);
    }
}
