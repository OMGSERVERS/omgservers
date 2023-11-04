package com.omgservers.model.match;

import com.omgservers.model.request.RequestModel;
import com.omgservers.model.version.VersionGroupModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchGroupModel {

    static public MatchGroupModel create(final VersionGroupModel config) {
        return create(config, new ArrayList<>());
    }

    static public MatchGroupModel create(final VersionGroupModel config, final List<RequestModel> requests) {
        final var group = new MatchGroupModel();
        group.setConfig(config);
        group.setRequests(requests);
        return group;
    }

    @NotNull
    VersionGroupModel config;

    @NotNull
    List<RequestModel> requests;
}
