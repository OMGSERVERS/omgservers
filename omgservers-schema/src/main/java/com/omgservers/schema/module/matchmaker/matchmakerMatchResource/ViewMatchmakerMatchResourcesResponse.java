package com.omgservers.schema.module.matchmaker.matchmakerMatchResource;

import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewMatchmakerMatchResourcesResponse {

    List<MatchmakerMatchResourceModel> matchmakerMatchResources;
}
