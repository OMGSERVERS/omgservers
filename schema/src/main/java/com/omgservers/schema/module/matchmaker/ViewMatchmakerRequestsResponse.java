package com.omgservers.schema.module.matchmaker;

import com.omgservers.schema.model.request.MatchmakerRequestModel;
import com.omgservers.schema.model.request.MatchmakerRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewMatchmakerRequestsResponse {

    List<MatchmakerRequestModel> matchmakerRequests;
}
