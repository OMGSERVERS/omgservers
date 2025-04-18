package com.omgservers.schema.model.shard;

import java.net.URI;

public record ShardModel(int slot, URI uri, boolean foreign, boolean locked) {
}
