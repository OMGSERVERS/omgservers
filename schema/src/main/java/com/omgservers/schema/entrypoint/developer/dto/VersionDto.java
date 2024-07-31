package com.omgservers.schema.entrypoint.developer.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionDto {

    Long id;

    Instant created;

    @NotNull
    List<VersionImageRefDto> imageRefs;

    @NotNull
    List<VersionLobbyRefDto> lobbyRefs;

    @NotNull
    List<VersionMatchmakerRefDto> matchmakerRefs;
}
