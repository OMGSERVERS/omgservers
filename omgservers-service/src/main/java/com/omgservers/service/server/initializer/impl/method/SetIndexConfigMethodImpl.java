package com.omgservers.service.server.initializer.impl.method;

import com.omgservers.schema.model.index.IndexConfigDto;
import com.omgservers.service.operation.server.GetIndexConfigOperation;
import com.omgservers.service.server.state.StateService;
import com.omgservers.service.server.state.dto.SetIndexConfigRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SetIndexConfigMethodImpl implements SetIndexConfigMethod {

    final GetIndexConfigOperation getIndexConfigOperation;

    final StateService stateService;

    @Override
    public Uni<Void> execute() {
        log.debug("Set index config");

        return getIndexConfigOperation.execute()
                .invoke(this::setIndexConfig)
                .replaceWithVoid();
    }

    void setIndexConfig(final IndexConfigDto indexConfig) {
        final var request = new SetIndexConfigRequest(indexConfig);
        stateService.execute(request);

        log.info("Index config set");
    }
}
