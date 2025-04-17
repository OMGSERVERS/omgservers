package com.omgservers.service.server.cache;

import com.omgservers.service.server.cache.dto.GetCachedClientLastActivityRequest;
import com.omgservers.service.server.cache.dto.GetCachedClientLastActivityResponse;
import com.omgservers.service.server.cache.dto.GetCachedClientsLastActivitiesRequest;
import com.omgservers.service.server.cache.dto.GetCachedClientsLastActivitiesResponse;
import com.omgservers.service.server.cache.dto.GetCachedIndexConfigRequest;
import com.omgservers.service.server.cache.dto.GetCachedIndexConfigResponse;
import com.omgservers.service.server.cache.dto.GetCachedRuntimeLastActivityRequest;
import com.omgservers.service.server.cache.dto.GetCachedRuntimeLastActivityResponse;
import com.omgservers.service.server.cache.dto.CacheClientLastActivityRequest;
import com.omgservers.service.server.cache.dto.CacheClientLastActivityResponse;
import com.omgservers.service.server.cache.dto.CacheIndexConfigRequest;
import com.omgservers.service.server.cache.dto.CacheIndexConfigResponse;
import com.omgservers.service.server.cache.dto.CacheRuntimeLastActivityRequest;
import com.omgservers.service.server.cache.dto.CacheRuntimeLastActivityResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface CacheService {

    Uni<GetCachedRuntimeLastActivityResponse> execute(@Valid GetCachedRuntimeLastActivityRequest request);

    Uni<CacheRuntimeLastActivityResponse> execute(@Valid CacheRuntimeLastActivityRequest request);

    Uni<GetCachedClientLastActivityResponse> execute(@Valid GetCachedClientLastActivityRequest request);

    Uni<GetCachedClientsLastActivitiesResponse> execute(@Valid GetCachedClientsLastActivitiesRequest request);

    Uni<CacheClientLastActivityResponse> execute(@Valid CacheClientLastActivityRequest request);

    Uni<GetCachedIndexConfigResponse> execute(@Valid GetCachedIndexConfigRequest request);

    Uni<CacheIndexConfigResponse> execute(@Valid CacheIndexConfigRequest request);
}
