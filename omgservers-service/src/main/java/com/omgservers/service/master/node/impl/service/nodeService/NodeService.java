package com.omgservers.service.master.node.impl.service.nodeService;

import com.omgservers.schema.master.node.AcquireNodeRequest;
import com.omgservers.schema.master.node.AcquireNodeResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface NodeService {

    Uni<AcquireNodeResponse> execute(@Valid AcquireNodeRequest request);
}
