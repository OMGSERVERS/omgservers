package com.omgservers.model.runtimeChangeOfContext;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.player.PlayerModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuntimeChangeOfContext {

    @NotNull
    List<Long> handledRuntimeCommandIds;

    @NotNull
    List<DoCommandModel> producedDoCommands;

    @NotNull
    List<PlayerModel> updatedPlayers;
}
