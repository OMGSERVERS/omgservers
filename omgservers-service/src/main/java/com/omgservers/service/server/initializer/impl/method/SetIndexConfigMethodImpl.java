package com.omgservers.service.server.initializer.impl.method;

import com.omgservers.service.operation.server.ExecuteStateOperation;
import com.omgservers.service.operation.server.GetIndexConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SetIndexConfigMethodImpl implements SetIndexConfigMethod {

    final GetIndexConfigOperation getIndexConfigOperation;
    final ExecuteStateOperation executeStateOperation;

    @Override
    public Uni<Void> execute() {
        log.info("Set index config");

        return getIndexConfigOperation.execute()
                .invoke(indexConfig -> {
                    executeStateOperation.setIndexConfig(indexConfig);
                    log.info("Index config set");
                })
                .replaceWithVoid();
    }
}
