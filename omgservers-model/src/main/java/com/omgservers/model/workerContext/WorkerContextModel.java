package com.omgservers.model.workerContext;

import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerContextModel {

    List<RuntimeCommandModel> runtimeCommands;
    Map<Long, PlayerModel> players;
}
