package com.omgservers.service.master.entity.impl.service.webService;

import com.omgservers.schema.master.entity.DeleteEntityRequest;
import com.omgservers.schema.master.entity.DeleteEntityResponse;
import com.omgservers.schema.master.entity.FindEntityRequest;
import com.omgservers.schema.master.entity.FindEntityResponse;
import com.omgservers.schema.master.entity.GetEntityRequest;
import com.omgservers.schema.master.entity.GetEntityResponse;
import com.omgservers.schema.master.entity.SyncEntityRequest;
import com.omgservers.schema.master.entity.SyncEntityResponse;
import com.omgservers.schema.master.entity.ViewEntitiesRequest;
import com.omgservers.schema.master.entity.ViewEntitiesResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<GetEntityResponse> execute(GetEntityRequest request);

    Uni<FindEntityResponse> execute(FindEntityRequest request);

    Uni<ViewEntitiesResponse> execute(ViewEntitiesRequest request);

    Uni<SyncEntityResponse> execute(SyncEntityRequest request);

    Uni<DeleteEntityResponse> execute(DeleteEntityRequest request);
}
