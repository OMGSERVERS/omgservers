package com.omgservers.application.module.internalModule.model.index;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndexServerModel {

    static public IndexServerModel create(URI uri) {
        return create(uri, new ArrayList<>());
    }

    static public IndexServerModel create(URI uri, List<Integer> shards) {
        IndexServerModel indexServerModel = new IndexServerModel();
        indexServerModel.setUri(uri);
        indexServerModel.setShards(shards);
        return indexServerModel;
    }

    static public void validateServerModel(IndexServerModel server) {
        if (server == null) {
            throw new ServerSideBadRequestException("server is null");
        }
        validateUri(server.getUri());
        validateShards(server.getShards());
    }

    static public void validateUri(URI uri) {
        if (uri == null) {
            throw new ServerSideBadRequestException("uri field is null");
        }
    }

    static public void validateShards(List<Integer> shards) {
        if (shards == null) {
            throw new ServerSideBadRequestException("shards field is null");
        }
        if (shards.size() > 1024) {
            throw new ServerSideBadRequestException("shards array is too long");
        }
        shards.stream().forEach(shard -> {
            if (shard < 0 || shard > 32768) {
                throw new ServerSideBadRequestException("shard is wrong, value=" + shard);
            }
        });
    }

    URI uri;
    List<Integer> shards;
}
