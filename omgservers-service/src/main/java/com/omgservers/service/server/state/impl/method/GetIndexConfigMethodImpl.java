package com.omgservers.service.server.state.impl.method;

import com.omgservers.service.server.state.dto.GetIndexConfigRequest;
import com.omgservers.service.server.state.dto.GetIndexConfigResponse;
import com.omgservers.service.server.state.impl.operation.ExecuteStateOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetIndexConfigMethodImpl implements GetIndexConfigMethod {

    final ExecuteStateOperation executeStateOperation;

    @Override
    public GetIndexConfigResponse execute(final GetIndexConfigRequest request) {
        final var indexConfig = executeStateOperation.getIndexConfig();
        return new GetIndexConfigResponse(indexConfig);
    }
}
