package com.omgservers.model.index;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndexConfigModel {

    static public IndexConfigModel create(Integer totalShardCount) {
        if (totalShardCount == null) {
            throw new IllegalArgumentException("totalShardCount is null");
        }

        IndexConfigModel config = new IndexConfigModel();
        config.setTotalShardCount(totalShardCount);
        config.setServers(new ArrayList<>());
        config.setLockedShards(new ArrayList<>());

        return config;
    }

    static public IndexConfigModel create(List<URI> addresses) {
        IndexConfigModel config = new IndexConfigModel();
        config.setTotalShardCount(addresses.size());

        List<IndexServerModel> servers = new ArrayList<>();
        for (int serverIndex = 0; serverIndex < addresses.size(); serverIndex++) {
            servers.add(IndexServerModel.create(addresses.get(serverIndex), Collections.singletonList(serverIndex)));
        }
        config.setServers(servers);
        config.setLockedShards(new ArrayList<>());

        return config;
    }

    public static void validateConfig(IndexConfigModel config) {
        if (config == null) {
            throw new ServerSideBadRequestException("config is null");
        }
        validateTotalShardCount(config.getTotalShardCount());
        validateServers(config.getServers());
        validateLockedShards(config.getLockedShards());
    }

    public static void validateTotalShardCount(Integer totalShardCount) {
        if (totalShardCount == null) {
            throw new ServerSideBadRequestException("totalShardCount is null");
        }
        if (totalShardCount < 0 || totalShardCount > 32768) {
            throw new ServerSideBadRequestException("totalShardCount is wrong, value=" + totalShardCount);
        }
    }

    public static void validateServers(List<IndexServerModel> indexServerModels) {
        if (indexServerModels == null) {
            throw new ServerSideBadRequestException("servers field is null");
        }
        if (indexServerModels.size() > 1024) {
            throw new ServerSideBadRequestException("servers array is too long");
        }
        indexServerModels.stream().forEach(IndexServerModel::validateServerModel);
    }

    public static void validateLockedShards(List<Integer> lockedShards) {
        if (lockedShards == null) {
            throw new ServerSideBadRequestException("lockedShards field is null");
        }
        if (lockedShards.size() > 1024) {
            throw new ServerSideBadRequestException("lockedShards array is too long");
        }
        lockedShards.stream().forEach(shard -> {
            if (shard < 0 || shard > 32768) {
                throw new ServerSideBadRequestException("shard is wrong, value=" + shard);
            }
        });
    }

    Integer totalShardCount;
    List<IndexServerModel> servers;
    List<Integer> lockedShards;

    @JsonIgnore
    public URI getServerUri(final Integer shard) {
        if (shard == null) {
            throw new IllegalArgumentException("shard is null");
        }

        return servers.stream()
                .filter(server -> server.getShards().contains(shard))
                .map(IndexServerModel::getUri)
                .findFirst()
                .get();
    }
}
