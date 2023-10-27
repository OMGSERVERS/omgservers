package com.omgservers.model.index;

import com.omgservers.exception.ServerSideBadRequestException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    public static void validateServerModel(IndexServerModel server) {
        if (server == null) {
            throw new ServerSideBadRequestException("server is null");
        }
        validateUri(server.getUri());
        validateShards(server.getShards());
    }

    public static void validateUri(URI uri) {
        if (uri == null) {
            throw new ServerSideBadRequestException("uri field is null");
        }
    }

    public static void validateShards(List<Integer> shards) {
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

    @NotNull
    URI uri;

    @NotNull
    @Size(max = 32767)
    List<Integer> shards;
}
