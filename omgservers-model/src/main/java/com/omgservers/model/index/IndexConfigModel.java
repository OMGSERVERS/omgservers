package com.omgservers.model.index;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
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
        return create(addresses, addresses.size());
    }

    static public IndexConfigModel create(final List<URI> addresses, final int shardCount) {
        final var config = new IndexConfigModel();
        config.setTotalShardCount(shardCount);

        final var serverCount = addresses.size();
        final var servers = new ArrayList<IndexServerModel>();
        for (int serverIndex = 0; serverIndex < serverCount; serverIndex++) {
            final var serverUri = addresses.get(serverIndex);
            final var serverShards = new ArrayList<Integer>();

            int shard = serverIndex;
            while (shard < shardCount) {
                serverShards.add(shard);
                shard += serverCount;
            }

            servers.add(IndexServerModel.create(serverUri, serverShards));
        }

        config.setServers(servers);
        config.setLockedShards(new ArrayList<>());

        return config;
    }

    @NotNull
    @Min(1)
    @Max(32767)
    Integer totalShardCount;

    @NotNull
    @NotEmpty
    @Size(max = 1024)
    List<IndexServerModel> servers;

    @NotNull
    @Size(max = 32767)
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
