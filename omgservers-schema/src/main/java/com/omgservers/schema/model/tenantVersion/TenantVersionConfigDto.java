package com.omgservers.schema.model.tenantVersion;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantVersionConfigDto {

    static public TenantVersionConfigDto create() {
        final var config = new TenantVersionConfigDto();
        config.setModes(new ArrayList<>());
        return config;
    }

    @NotNull
    List<TenantVersionModeDto> modes;

    Object userData;
}