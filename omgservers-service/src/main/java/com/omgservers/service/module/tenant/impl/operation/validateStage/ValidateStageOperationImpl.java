package com.omgservers.service.module.tenant.impl.operation.validateStage;

import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.model.stage.StageModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ApplicationScoped
class ValidateStageOperationImpl implements ValidateStageOperation {

    @Override
    public StageModel validateStage(StageModel stage) {
        if (stage == null) {
            throw new IllegalArgumentException("stage is null");
        }

        var config = stage.getConfig();

        Map<String, Boolean> results = new HashMap<>();

        // TODO: validate stage

        var valid = results.values().stream().allMatch(Boolean.TRUE::equals);
        if (valid) {
            return stage;
        } else {
            throw new ServerSideBadRequestException(String.format("bad stage, stage=%s", stage));
        }
    }
}
