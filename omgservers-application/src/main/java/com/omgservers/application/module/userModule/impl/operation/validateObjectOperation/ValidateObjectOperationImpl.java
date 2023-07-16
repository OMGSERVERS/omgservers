package com.omgservers.application.module.userModule.impl.operation.validateObjectOperation;

import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.module.userModule.model.object.ObjectModel;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ApplicationScoped
class ValidateObjectOperationImpl implements ValidateObjectOperation {

    @Override
    public ObjectModel validateObject(ObjectModel object) {
        if (object == null) {
            throw new IllegalArgumentException("object is null");
        }

        var body = object.getBody();

        Map<String, Boolean> results = new HashMap<>();

        // TODO: validate object

        var valid = results.values().stream().allMatch(Boolean.TRUE::equals);
        if (valid) {
            log.info("Object is valid, object={}", object);
            return object;
        } else {
            throw new ServerSideBadRequestException(String.format("bad object, object=%s", object));
        }
    }
}
