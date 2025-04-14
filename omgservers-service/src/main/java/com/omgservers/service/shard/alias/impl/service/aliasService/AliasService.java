package com.omgservers.service.shard.alias.impl.service.aliasService;

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
import jakarta.validation.Valid;

public interface AliasService {

    Uni<GetAliasResponse> execute(@Valid GetAliasRequest request);

    Uni<FindAliasResponse> execute(@Valid FindAliasRequest request);

    Uni<ViewAliasesResponse> execute(@Valid ViewAliasesRequest request);

    Uni<SyncAliasResponse> execute(@Valid SyncAliasRequest request);

    Uni<DeleteAliasResponse> execute(@Valid DeleteAliasRequest request);
}
