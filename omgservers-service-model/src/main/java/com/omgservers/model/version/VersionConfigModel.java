package com.omgservers.model.version;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionConfigModel {

    static public VersionConfigModel create() {
        final var config = new VersionConfigModel();
        config.setModes(new ArrayList<>());
        return config;
    }

    @NotEmpty
    List<VersionModeModel> modes;
}
