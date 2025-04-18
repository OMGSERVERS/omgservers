package com.omgservers.schema.model.index;

import com.omgservers.schema.configuration.ValidationConfiguration;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndexShardDto {

    static public IndexShardDto create(final URI uri, final List<Integer> slots) {
        final var indexShard = new IndexShardDto();
        indexShard.setUri(uri);
        indexShard.setSlots(slots.stream().toList());
        return indexShard;
    }

    @NotNull
    URI uri;

    @NotNull
    @Size(max = ValidationConfiguration.MAX_SLOTS)
    List<Integer> slots;
}
