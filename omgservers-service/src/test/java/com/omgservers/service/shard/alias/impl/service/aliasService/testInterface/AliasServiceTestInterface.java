package com.omgservers.service.shard.alias.impl.service.aliasService.testInterface;

import com.omgservers.schema.module.alias.DeleteAliasRequest;
import com.omgservers.schema.module.alias.DeleteAliasResponse;
import com.omgservers.schema.module.alias.FindAliasRequest;
import com.omgservers.schema.module.alias.FindAliasResponse;
import com.omgservers.schema.module.alias.GetAliasRequest;
import com.omgservers.schema.module.alias.GetAliasResponse;
import com.omgservers.schema.module.alias.SyncAliasRequest;
import com.omgservers.schema.module.alias.SyncAliasResponse;
import com.omgservers.schema.module.alias.ViewAliasesRequest;
import com.omgservers.schema.module.alias.ViewAliasesResponse;
import com.omgservers.service.shard.alias.impl.service.aliasService.AliasService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class AliasServiceTestInterface {
    private static final long TIMEOUT = 1L;

    final AliasService aliasService;

    public GetAliasResponse execute(final GetAliasRequest request) {
        return aliasService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindAliasResponse execute(final FindAliasRequest request) {
        return aliasService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewAliasesResponse execute(final ViewAliasesRequest request) {
        return aliasService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncAliasResponse execute(final SyncAliasRequest request) {
        return aliasService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteAliasResponse execute(final DeleteAliasRequest request) {
        return aliasService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
