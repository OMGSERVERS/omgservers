package com.omgservers.service.master.node.impl.service.nodeService.impl.method;

import com.omgservers.schema.master.node.AcquireNodeRequest;
import com.omgservers.schema.master.node.AcquireNodeResponse;
import io.smallrye.mutiny.Uni;

public interface AcquireNodeMethod {
    Uni<AcquireNodeResponse> execute(AcquireNodeRequest request);
}
