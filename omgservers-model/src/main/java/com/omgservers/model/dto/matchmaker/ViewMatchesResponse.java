package com.omgservers.model.dto.matchmaker;

import com.omgservers.model.match.MatchModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewMatchesResponse {

    List<MatchModel> matches;
}
