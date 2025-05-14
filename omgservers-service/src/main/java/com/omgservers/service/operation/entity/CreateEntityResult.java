package com.omgservers.service.operation.entity;

import com.omgservers.schema.model.entity.EntityModel;

public record CreateEntityResult(EntityModel entity, Boolean created) {
}
