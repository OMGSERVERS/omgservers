package com.omgservers.dto.matchmaker;

import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewMatchmakerCommandsResponse {

    List<MatchmakerCommandModel> matchmakerCommands;
}
