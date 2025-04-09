package com.omgservers.service.server.cache.impl.service.redis;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.server.cache.CacheService;
import com.omgservers.service.server.cache.dto.*;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CacheServiceRedisImpl implements CacheService {

    @Override
    public Uni<GetRuntimeLastActivityResponse> execute(@Valid final GetRuntimeLastActivityRequest request) {
        throw new ServerSideConflictException(ExceptionQualifierEnum.UNSUPPORTED_FEATURE, "unsupported feature");
    }

    @Override
    public Uni<SetRuntimeLastActivityResponse> execute(@Valid final SetRuntimeLastActivityRequest request) {
        throw new ServerSideConflictException(ExceptionQualifierEnum.UNSUPPORTED_FEATURE, "unsupported feature");
    }

    @Override
    public Uni<GetClientLastActivityResponse> execute(@Valid final GetClientLastActivityRequest request) {
        throw new ServerSideConflictException(ExceptionQualifierEnum.UNSUPPORTED_FEATURE, "unsupported feature");
    }

    @Override
    public Uni<GetClientsLastActivitiesResponse> execute(@Valid final GetClientsLastActivitiesRequest request) {
        throw new ServerSideConflictException(ExceptionQualifierEnum.UNSUPPORTED_FEATURE, "unsupported feature");
    }

    @Override
    public Uni<SetClientLastActivityResponse> execute(@Valid final SetClientLastActivityRequest request) {
        throw new ServerSideConflictException(ExceptionQualifierEnum.UNSUPPORTED_FEATURE, "unsupported feature");
    }
}
