package com.omgservers.ctl.dto.permission;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum StagePermissionEnum {

    DEPLOYMENT_MANAGER("deployment-manager"),
    STAGE_VIEWER("stage-viewer");

    final String permission;

    @JsonValue
    public String toJson() {
        return permission;
    }

    @JsonCreator
    public static StagePermissionEnum fromString(final String permission) {
        return Arrays.stream(StagePermissionEnum.values())
                .filter(value -> value.permission.equals(permission))
                .findFirst()
                .orElseThrow();
    }

    public TenantStagePermissionQualifierEnum toQualifier() {
        final var thisName = name();
        return Arrays.stream(TenantStagePermissionQualifierEnum.values())
                .filter(value -> value.name().equals(thisName))
                .findFirst()
                .orElseThrow();
    }
}
