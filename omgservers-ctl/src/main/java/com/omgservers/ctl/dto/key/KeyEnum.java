package com.omgservers.ctl.dto.key;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum KeyEnum {
    ID("id"),
    MAP("map"),
    INSTALLATION("installation"),
    API("api"),
    REGISTRY("registry"),
    USER("user"),
    USER_ID("user_id"),
    DEVELOPER("developer"),
    NAME("name"),
    HASH("hash"),
    URI("uri"),
    SLOT("slot"),
    TOKEN("token"),
    RESULT("result"),
    ALIAS("alias"),
    TENANT("tenant"),
    TENANT_ID("tenant_id"),
    PROJECT("project"),
    PROJECT_ID("project_id"),
    STAGE("stage"),
    STAGE_ID("stage_id"),
    VERSION_ID("version_id"),
    DEPLOYMENT_ID("deployment_id"),
    PASSWORD("password");

    final String key;

    @JsonValue
    public String toJson() {
        return key;
    }

    @JsonCreator
    public static KeyEnum fromString(final String key) {
        return Arrays.stream(KeyEnum.values())
                .filter(value -> value.key.equals(key))
                .findFirst()
                .orElseThrow();
    }
}
