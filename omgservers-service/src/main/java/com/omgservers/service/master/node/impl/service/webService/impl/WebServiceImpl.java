package com.omgservers.service.master.node.impl.service.webService.impl;

import com.omgservers.schema.master.node.AcquireNodeRequest;
import com.omgservers.schema.master.node.AcquireNodeResponse;
import com.omgservers.service.master.node.impl.service.nodeService.NodeService;
import com.omgservers.service.master.node.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final NodeService nodeService;

    @Override
    public Uni<AcquireNodeResponse> execute(final AcquireNodeRequest request) {
        return nodeService.execute(request);
    }
}
