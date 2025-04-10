package com.omgservers.schema.model.runtimeAssignment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RuntimeAssignmentConfigVersionEnum {
    V1(1);

    final int version;
}
