package com.omgservers.base.module.internal.impl.operation.validateIndex;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.index.IndexModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ApplicationScoped
class ValidateIndexOperationImpl implements ValidateIndexOperation {

    @Override
    public IndexModel validateIndex(IndexModel indexModel) {
        if (indexModel == null) {
            throw new IllegalArgumentException("index is null");
        }

        var config = indexModel.getConfig();

        Map<String, Boolean> results = new HashMap<>();

        // TODO: validate index

        var valid = results.values().stream().allMatch(Boolean.TRUE::equals);
        if (valid) {
            log.info("Index is valid, name={}, version={}", indexModel.getName(), indexModel.getVersion());
            return indexModel;
        } else {
            throw new ServerSideBadRequestException(String.format("bad index, index=%s", indexModel));
        }
    }
}
