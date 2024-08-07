package com.omgservers.schema.model.version;

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
public class VersionModeDto {

    static public VersionModeDto create(final String name,
                                        final Integer minPlayers,
                                        final Integer maxPlayers) {
        return create(name, minPlayers, maxPlayers, new ArrayList<>());
    }

    static public VersionModeDto create(final String name,
                                        final Integer minPlayers,
                                        final Integer maxPlayers,
                                        final List<VersionGroupDto> groups) {
        final var config = new VersionModeDto();
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
    List<VersionGroupDto> groups;
}
