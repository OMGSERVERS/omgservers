package com.omgservers.model.version;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionModeModel {

    static public VersionModeModel create(final String name,
                                          final Integer minPlayers,
                                          final Integer maxPlayers) {
        return create(name, minPlayers, maxPlayers, new ArrayList<>());
    }

    static public VersionModeModel create(final String name,
                                          final Integer minPlayers,
                                          final Integer maxPlayers,
                                          final List<VersionGroupModel> groups) {
        final var config = new VersionModeModel();
        config.setName(name);
        config.setMinPlayers(minPlayers);
        config.setMaxPlayers(maxPlayers);
        config.setGroups(groups);
        return config;
    }

    @NotBlank
    @Size(max = 64)
    String name;

    @NotNull
    Integer minPlayers;

    @NotNull
    Integer maxPlayers;

    @NotEmpty
    List<VersionGroupModel> groups;
}
