package com.omgservers.schema.entrypoint.developer;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteLobbyDeveloperRequest {

    @NotNull
    Long lobbyId;
}
