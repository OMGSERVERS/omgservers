package com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.impl.method.interchangeMessagesMethod;

import com.omgservers.schema.entrypoint.runtime.InterchangeMessagesRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.InterchangeMessagesRuntimeResponse;
import com.omgservers.schema.module.runtime.runtimeMessage.InterchangeMessagesRequest;
import com.omgservers.schema.module.runtime.runtimeMessage.InterchangeMessagesResponse;
import com.omgservers.service.security.SecurityAttributesEnum;
import com.omgservers.service.shard.runtime.RuntimeShard;
import com.omgservers.service.shard.user.UserShard;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class InterchangeMessagesMethodImpl implements InterchangeMessagesMethod {

    final RuntimeShard runtimeShard;
    final UserShard userShard;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<InterchangeMessagesRuntimeResponse> execute(final InterchangeMessagesRuntimeRequest request) {
        log.debug("Requested, {}", request);

        final var runtimeId = securityIdentity
                .<Long>getAttribute(SecurityAttributesEnum.RUNTIME_ID.getAttributeName());

        final var outgoingMessages = request.getOutgoingMessages();
        final var consumedMessages = request.getConsumedMessages();

        final var interchangeMessagesRequest = new InterchangeMessagesRequest(runtimeId,
                outgoingMessages,
                consumedMessages);
        return runtimeShard.getService().execute(interchangeMessagesRequest)
                .map(InterchangeMessagesResponse::getIncomingMessages)
                .map(InterchangeMessagesRuntimeResponse::new);
    }
}
