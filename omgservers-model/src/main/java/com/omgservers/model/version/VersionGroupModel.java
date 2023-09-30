package com.omgservers.model.version;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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

    @NotEmpty
    @Size(max = 64)
    String name;

    @NotNull
    @Positive
    Integer minPlayers;

    @NotNull
    @Positive
    Integer maxPlayers;
}
