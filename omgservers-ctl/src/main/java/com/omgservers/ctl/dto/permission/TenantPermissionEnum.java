package com.omgservers.ctl.dto.permission;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum TenantPermissionEnum {

    PROJECT_MANAGER("project-manager"),
    TENANT_VIEWER("tenant-viewer");

    final String permission;

    @JsonValue
    public String toJson() {
        return permission;
    }

    @JsonCreator
    public static TenantPermissionEnum fromString(final String permission) {
        return Arrays.stream(TenantPermissionEnum.values())
                .filter(value -> value.permission.equals(permission))
                .findFirst()
                .orElseThrow();
    }

    public TenantPermissionQualifierEnum toQualifier() {
        final var thisName = name();
        return Arrays.stream(TenantPermissionQualifierEnum.values())
                .filter(value -> value.name().equals(thisName))
                .findFirst()
                .orElseThrow();
    }
}
