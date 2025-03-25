package com.omgservers.schema.module.matchmaker.matchmakerRequest;

import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestModel;
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
