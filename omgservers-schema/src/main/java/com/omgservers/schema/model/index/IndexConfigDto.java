package com.omgservers.schema.model.index;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.omgservers.schema.configuration.ValidationConfiguration;
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
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndexConfigDto {

    static public IndexConfigDto create(final List<URI> shards, final int slotsCount) {
        final var indexConfig = new IndexConfigDto();
        indexConfig.setVersion(IndexConfigVersionEnum.V1);
        indexConfig.setTotalSlotsCount(slotsCount);

        final var shardsCount = shards.size();
        final var indexShards = new ArrayList<IndexShardDto>();
        for (int shardIndex = 0; shardIndex < shardsCount; shardIndex++) {
            final var shardUri = shards.get(shardIndex);
            final var shardSlots = new ArrayList<Integer>();

            int slot = shardIndex;
            while (slot < slotsCount) {
                shardSlots.add(slot);
                slot += shardsCount;
            }

            final var indexShard = IndexShardDto.create(shardUri, shardSlots);
            indexShards.add(indexShard);
        }

        indexConfig.setShards(indexShards);
        indexConfig.setLockedSlots(List.of());

        return indexConfig;
    }

    @NotNull
    IndexConfigVersionEnum version;

    @NotNull
    @Min(1)
    @Max(ValidationConfiguration.MAX_SLOTS)
    Integer totalSlotsCount;

    @NotNull
    @NotEmpty
    @Size(max = ValidationConfiguration.MAX_SHARDS)
    List<IndexShardDto> shards;

    @NotNull
    @Size(max = ValidationConfiguration.MAX_SLOTS)
    List<Integer> lockedSlots;

    @JsonIgnore
    public Optional<URI> getShardUri(final Integer slot) {
        return shards.stream()
                .filter(shard -> shard.getSlots().contains(slot))
                .map(IndexShardDto::getUri)
                .findFirst();
    }
}
