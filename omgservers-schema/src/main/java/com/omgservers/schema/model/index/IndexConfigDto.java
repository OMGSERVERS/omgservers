package com.omgservers.schema.model.index;

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
public class IndexConfigDto {

    static public IndexConfigDto create(final Integer totalShardCount) {
        final var indexConfig = new IndexConfigDto();
        indexConfig.setVersion(IndexConfigVersionEnum.V1);
        indexConfig.setTotalShardCount(totalShardCount);
        indexConfig.setServers(new ArrayList<>());
        indexConfig.setLockedShards(new ArrayList<>());

        return indexConfig;
    }

    static public IndexConfigDto create(final List<URI> addresses) {
        return create(addresses, addresses.size());
    }

    static public IndexConfigDto create(final List<URI> addresses, final int shardCount) {
        final var indexConfig = new IndexConfigDto();
        indexConfig.setVersion(IndexConfigVersionEnum.V1);
        indexConfig.setTotalShardCount(shardCount);

        final var serverCount = addresses.size();
        final var servers = new ArrayList<IndexServerDto>();
        for (int serverIndex = 0; serverIndex < serverCount; serverIndex++) {
            final var serverUri = addresses.get(serverIndex);
            final var serverShards = new ArrayList<Integer>();

            int shard = serverIndex;
            while (shard < shardCount) {
                serverShards.add(shard);
                shard += serverCount;
            }

            servers.add(IndexServerDto.create(serverUri, serverShards));
        }

        indexConfig.setServers(servers);
        indexConfig.setLockedShards(new ArrayList<>());

        return indexConfig;
    }

    @NotNull
    IndexConfigVersionEnum version;

    @NotNull
    @Min(1)
    @Max(512)
    Integer totalShardCount;

    @NotNull
    @NotEmpty
    @Size(max = 64)
    List<IndexServerDto> servers;

    @NotNull
    @Size(max = 512)
    List<Integer> lockedShards;

    @JsonIgnore
    public URI getServerUri(final Integer shard) {
        return servers.stream()
                .filter(server -> server.getShards().contains(shard))
                .map(IndexServerDto::getUri)
                .findFirst()
                .get();
    }
}
