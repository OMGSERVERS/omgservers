package com.omgservers.application.module.versionModule.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionGroupModel {

    static public VersionGroupModel create(final String name,
                                           final Integer minPlayers,
                                           final Integer maxPlayers) {
        final var group = new VersionGroupModel();
        group.setName(name);
        group.setMinPlayers(minPlayers);
        group.setMaxPlayers(maxPlayers);
        return group;
    }

    String name;
    Integer minPlayers;
    Integer maxPlayers;
}
