package com.omgservers.application.module.versionModule.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionStageConfigModel {

    static public VersionStageConfigModel create() {
        final var config = new VersionStageConfigModel();
        config.setModes(new ArrayList<>());
        return config;
    }

    List<VersionModeModel> modes;
}
