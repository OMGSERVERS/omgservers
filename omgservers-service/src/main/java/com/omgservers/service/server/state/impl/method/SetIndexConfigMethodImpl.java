package com.omgservers.service.server.state.impl.method;

import com.omgservers.service.server.state.dto.SetIndexConfigRequest;
import com.omgservers.service.server.state.dto.SetIndexConfigResponse;
import com.omgservers.service.server.state.impl.operation.ExecuteStateOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SetIndexConfigMethodImpl implements SetIndexConfigMethod {

    final ExecuteStateOperation executeStateOperation;

    @Override
    public SetIndexConfigResponse execute(final SetIndexConfigRequest request) {
        final var indexConfig = request.getIndexConfig();
        executeStateOperation.setIndexConfig(indexConfig);

        log.info("Index config set");

        return new SetIndexConfigResponse();
    }
}
