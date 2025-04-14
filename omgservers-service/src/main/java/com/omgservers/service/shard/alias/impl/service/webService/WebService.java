package com.omgservers.service.shard.alias.impl.service.webService;

import com.omgservers.schema.shard.alias.DeleteAliasRequest;
import com.omgservers.schema.shard.alias.DeleteAliasResponse;
import com.omgservers.schema.shard.alias.FindAliasRequest;
import com.omgservers.schema.shard.alias.FindAliasResponse;
import com.omgservers.schema.shard.alias.GetAliasRequest;
import com.omgservers.schema.shard.alias.GetAliasResponse;
import com.omgservers.schema.shard.alias.SyncAliasRequest;
import com.omgservers.schema.shard.alias.SyncAliasResponse;
import com.omgservers.schema.shard.alias.ViewAliasesRequest;
import com.omgservers.schema.shard.alias.ViewAliasesResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<GetAliasResponse> execute(GetAliasRequest request);

    Uni<FindAliasResponse> execute(FindAliasRequest request);

    Uni<ViewAliasesResponse> execute(ViewAliasesRequest request);

    Uni<SyncAliasResponse> execute(SyncAliasRequest request);

    Uni<DeleteAliasResponse> execute(DeleteAliasRequest request);
}
