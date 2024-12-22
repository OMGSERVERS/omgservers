package com.omgservers.service.module.alias.impl.service.webService;

import com.omgservers.schema.module.alias.DeleteAliasRequest;
import com.omgservers.schema.module.alias.DeleteAliasResponse;
import com.omgservers.schema.module.alias.FindAliasRequest;
import com.omgservers.schema.module.alias.FindAliasResponse;
import com.omgservers.schema.module.alias.GetAliasRequest;
import com.omgservers.schema.module.alias.GetAliasResponse;
import com.omgservers.schema.module.alias.SyncAliasRequest;
import com.omgservers.schema.module.alias.SyncAliasResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<GetAliasResponse> execute(GetAliasRequest request);

    Uni<FindAliasResponse> execute(FindAliasRequest request);

    Uni<SyncAliasResponse> execute(SyncAliasRequest request);

    Uni<DeleteAliasResponse> execute(DeleteAliasRequest request);
}