package com.omgservers.service.module.alias.impl.service.aliasService;

import com.omgservers.schema.module.alias.DeleteAliasRequest;
import com.omgservers.schema.module.alias.DeleteAliasResponse;
import com.omgservers.schema.module.alias.FindAliasRequest;
import com.omgservers.schema.module.alias.FindAliasResponse;
import com.omgservers.schema.module.alias.GetAliasRequest;
import com.omgservers.schema.module.alias.GetAliasResponse;
import com.omgservers.schema.module.alias.SyncAliasRequest;
import com.omgservers.schema.module.alias.SyncAliasResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface AliasService {

    Uni<GetAliasResponse> execute(@Valid GetAliasRequest request);

    Uni<FindAliasResponse> execute(@Valid FindAliasRequest request);

    Uni<SyncAliasResponse> execute(@Valid SyncAliasRequest request);

    Uni<DeleteAliasResponse> execute(@Valid DeleteAliasRequest request);
}
