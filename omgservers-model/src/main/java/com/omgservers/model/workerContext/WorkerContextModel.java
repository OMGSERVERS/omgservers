package com.omgservers.model.workerContext;

import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerContextModel {

    List<RuntimeCommandModel> runtimeCommands;
    List<PlayerModel> players;
}
