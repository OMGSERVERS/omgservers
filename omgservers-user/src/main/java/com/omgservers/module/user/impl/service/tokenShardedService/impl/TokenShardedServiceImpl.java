package com.omgservers.module.user.impl.service.tokenShardedService.impl;

import com.omgservers.dto.user.CreateTokenShardedRequest;
import com.omgservers.dto.user.CreateTokenShardedResponse;
import com.omgservers.dto.user.IntrospectTokenShardRequest;
import com.omgservers.dto.user.IntrospectTokenShardedResponse;
import com.omgservers.module.user.impl.operation.decodeToken.DecodeTokenOperation;
import com.omgservers.module.user.impl.operation.getUserModuleClient.GetUserModuleClientOperation;
import com.omgservers.module.user.impl.operation.getUserModuleClient.UserModuleClient;
import com.omgservers.module.user.impl.service.tokenShardedService.TokenShardedService;
import com.omgservers.module.user.impl.service.tokenShardedService.impl.method.createToken.CreateTokenMethod;
import com.omgservers.module.user.impl.service.tokenShardedService.impl.method.introspectToken.IntrospectTokenMethod;
import com.omgservers.operation.calculateShard.CalculateShardOperation;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class TokenShardedServiceImpl implements TokenShardedService {

    final IntrospectTokenMethod introspectTokenMethod;
    final CreateTokenMethod createTokenMethod;

    final GetUserModuleClientOperation getUserModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;
    final DecodeTokenOperation decodeTokenOperation;

    @Override
    public Uni<CreateTokenShardedResponse> createToken(final CreateTokenShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                CreateTokenShardedRequest::validate,
                getUserModuleClientOperation::getClient,
                UserModuleClient::createToken,
                createTokenMethod::createToken);
    }

    @Override
    public Uni<IntrospectTokenShardedResponse> introspectToken(final IntrospectTokenShardRequest request) {
        IntrospectTokenShardRequest.validate(request);

        final var rawToken = request.getRawToken();
        return Uni.createFrom().item(rawToken)
                .map(decodeTokenOperation::decodeToken)
                .flatMap(tokenObject -> {
                    final var userId = tokenObject.getUserId();
                    final var shardKey = userId.toString();
                    return calculateShardOperation.calculateShard(shardKey)
                            .flatMap(shard -> {
                                if (shard.foreign()) {
                                    log.info("Request will be routed, request={}, shard={}", request, shard);
                                    return getUserModuleClientOperation.getClient(shard.serverUri())
                                            .introspectToken(request);
                                } else {
                                    return introspectTokenMethod.introspectToken(request);
                                }
                            });
                });
    }
}