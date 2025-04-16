package com.omgservers.service.master.node.impl.service.nodeService.impl.method;

import com.omgservers.schema.master.node.AcquireNodeRequest;
import com.omgservers.schema.master.node.AcquireNodeResponse;
import com.omgservers.service.master.node.impl.operation.AcquireNodeOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class AcquireNodeMethodImpl implements AcquireNodeMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final AcquireNodeOperation acquireNodeOperation;

    final PgPool pgPool;

    @Override
    public Uni<AcquireNodeResponse> execute(final AcquireNodeRequest request) {
        log.trace("{}", request);

        return changeWithContextOperation.changeWithContext(acquireNodeOperation::execute)
                .map(ChangeContext::getResult)
                .map(AcquireNodeResponse::new);
    }
}
