package com.omgservers.service.module.system.impl.service.entityService;

import com.omgservers.model.dto.system.DeleteEntityRequest;
import com.omgservers.model.dto.system.DeleteEntityResponse;
import com.omgservers.model.dto.system.FindEntityRequest;
import com.omgservers.model.dto.system.FindEntityResponse;
import com.omgservers.model.dto.system.SyncEntityRequest;
import com.omgservers.model.dto.system.SyncEntityResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface EntityService {

    Uni<FindEntityResponse> findEntity(@Valid FindEntityRequest request);

    Uni<SyncEntityResponse> syncEntity(@Valid SyncEntityRequest request);

    Uni<DeleteEntityResponse> deleteEntity(@Valid DeleteEntityRequest request);


}
