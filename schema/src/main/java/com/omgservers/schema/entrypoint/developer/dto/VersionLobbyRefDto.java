package com.omgservers.schema.entrypoint.developer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionLobbyRefDto {

    Long id;

    Instant created;

    Long lobbyId;

}
