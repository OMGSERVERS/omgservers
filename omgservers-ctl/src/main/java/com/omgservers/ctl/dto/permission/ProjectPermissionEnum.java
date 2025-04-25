package com.omgservers.ctl.dto.permission;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ProjectPermissionEnum {

    STAGE_MANAGER("stage-manager"),
    VERSION_MANAGER("version-manager"),
    PROJECT_VIEWER("project-viewer");

    final String permission;

    @JsonValue
    public String toJson() {
        return permission;
    }

    @JsonCreator
    public static ProjectPermissionEnum fromString(final String permission) {
        return Arrays.stream(ProjectPermissionEnum.values())
                .filter(value -> value.permission.equals(permission))
                .findFirst()
                .orElseThrow();
    }

    public TenantProjectPermissionQualifierEnum toQualifier() {
        final var thisName = name();
        return Arrays.stream(TenantProjectPermissionQualifierEnum.values())
                .filter(value -> value.name().equals(thisName))
                .findFirst()
                .orElseThrow();
    }
}
