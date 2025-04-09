package com.omgservers.schema.model.index;

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
public class IndexServerDto {

    static public IndexServerDto create(final URI uri) {
        return create(uri, new ArrayList<>());
    }

    static public IndexServerDto create(final URI uri, final List<Integer> shards) {
        final var indexServer = new IndexServerDto();
        indexServer.setUri(uri);
        indexServer.setShards(shards);
        return indexServer;
    }

    @NotNull
    URI uri;

    @NotNull
    @Size(max = 512)
    List<Integer> shards;
}
