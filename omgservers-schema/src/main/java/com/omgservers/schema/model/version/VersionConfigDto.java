package com.omgservers.schema.model.version;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionConfigDto {

    static public VersionConfigDto create() {
        final var config = new VersionConfigDto();
        config.setModes(new ArrayList<>());
        return config;
    }

    @NotNull
    List<VersionModeDto> modes;

    Object userData;
}
