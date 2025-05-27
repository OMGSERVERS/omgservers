package com.omgservers.schema.model.lobby;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LobbyConfigDto {

    @NotNull
    @JsonSetter(nulls = Nulls.SKIP)
    LobbyConfigVersionEnum version = LobbyConfigVersionEnum.V1;
}
