package com.omgservers.dto.matchmaker;

import com.omgservers.model.matchCommand.MatchCommandModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewMatchCommandsResponse {

    List<MatchCommandModel> matchCommands;
}
