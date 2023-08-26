package com.omgservers.application.module.userModule.impl.service.tokenInternalService.impl;

import com.omgservers.application.module.userModule.impl.operation.decodeTokenOperation.DecodeTokenOperation;
import com.omgservers.application.module.userModule.impl.operation.getUserServiceApiClientOperation.GetUserServiceApiClientOperation;
import com.omgservers.application.module.userModule.impl.operation.getUserServiceApiClientOperation.UserServiceApiClient;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.TokenInternalService;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.impl.method.createTokenMethod.CreateTokenMethod;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.impl.method.introspectTokenMethod.IntrospectTokenMethod;
import com.omgservers.operation.calculateShard.CalculateShardOperation;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.userModule.CreateTokenShardRequest;
import com.omgservers.dto.userModule.CreateTokenInternalResponse;
import com.omgservers.dto.userModule.IntrospectTokenInternalRequest;
import com.omgservers.dto.userModule.IntrospectTokenInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class TokenInternalServiceImpl implements TokenInternalService {

    final IntrospectTokenMethod introspectTokenMethod;
    final CreateTokenMethod createTokenMethod;

    final GetUserServiceApiClientOperation getUserServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;
    final DecodeTokenOperation decodeTokenOperation;

    @Override
    public Uni<CreateTokenInternalResponse> createToken(final CreateTokenShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                CreateTokenShardRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApiClient::createToken,
                createTokenMethod::createToken);
    }

    @Override
    public Uni<IntrospectTokenInternalResponse> introspectToken(final IntrospectTokenInternalRequest request) {
        IntrospectTokenInternalRequest.validate(request);

        final var rawToken = request.getRawToken();
        return Uni.createFrom().item(rawToken)
                .map(decodeTokenOperation::decodeToken)
                .flatMap(tokenObject -> {
                    final var user = tokenObject.getUserId();
                    final var shardKey = user.toString();
                    return calculateShardOperation.calculateShard(shardKey)
                            .flatMap(shard -> {
                                if (shard.foreign()) {
                                    log.info("Request will be routed, request={}, shard={}", request, shard);
                                    return getUserServiceApiClientOperation.getClient(shard.serverUri())
                                            .introspectToken(request);
                                } else {
                                    return introspectTokenMethod.introspectToken(request);
                                }
                            });
                });
    }
}