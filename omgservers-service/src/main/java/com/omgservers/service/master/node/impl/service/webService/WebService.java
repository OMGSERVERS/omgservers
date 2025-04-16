package com.omgservers.service.master.node.impl.service.webService;

import com.omgservers.schema.master.node.AcquireNodeRequest;
import com.omgservers.schema.master.node.AcquireNodeResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<AcquireNodeResponse> execute(AcquireNodeRequest request);
}
