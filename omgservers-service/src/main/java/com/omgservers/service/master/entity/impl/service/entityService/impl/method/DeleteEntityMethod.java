package com.omgservers.service.master.entity.impl.service.entityService.impl.method;

import com.omgservers.schema.master.entity.DeleteEntityRequest;
import com.omgservers.schema.master.entity.DeleteEntityResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteEntityMethod {
    Uni<DeleteEntityResponse> execute(DeleteEntityRequest request);
}
