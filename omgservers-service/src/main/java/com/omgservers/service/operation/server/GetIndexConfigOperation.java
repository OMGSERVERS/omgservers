package com.omgservers.service.operation.server;

import com.omgservers.schema.model.index.IndexConfigDto;
import io.smallrye.mutiny.Uni;

public interface GetIndexConfigOperation {

    Uni<IndexConfigDto> execute();
}
