package com.omgservers.model.match;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.request.RequestModel;
import com.omgservers.model.version.VersionGroupModel;
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
        if (config == null) {
            throw new ServerSideBadRequestException("config is null");
        }
        if (requests == null) {
            throw new ServerSideBadRequestException("requests is null");
        }

        final var group = new MatchGroupModel();
        group.setConfig(config);
        group.setRequests(requests);
        return group;
    }

    public static void validate(MatchGroupModel config) {
        if (config == null) {
            throw new ServerSideBadRequestException("config is null");
        }
    }

    VersionGroupModel config;
    List<RequestModel> requests;
}
