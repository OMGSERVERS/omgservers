package com.omgservers.schema.model.poolCommand;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;

public class PoolCommandDeserializer extends StdDeserializer<PoolCommandModel> {

    public PoolCommandDeserializer() {
        this(null);
    }

    public PoolCommandDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public PoolCommandModel deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JacksonException {
        try {
            return deserialize(parser);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    PoolCommandModel deserialize(JsonParser parser) throws IOException {
        final var mapper = (ObjectMapper) parser.getCodec();
        final var root = (JsonNode) mapper.readTree(parser);

        final var poolCommandModel = new PoolCommandModel();

        final var idNode = root.get("id");
        if (idNode != null) {
            poolCommandModel.setId(Long.valueOf(idNode.asText()));
        }

        final var idempotencyKeyNode = root.get("idempotency_key");
        if (idempotencyKeyNode != null) {
            poolCommandModel.setIdempotencyKey(idempotencyKeyNode.asText());
        }

        final var poolIdNode = root.get("pool_id");
        if (poolIdNode != null) {
            poolCommandModel.setPoolId(Long.valueOf(poolIdNode.asText()));
        }

        final var createdNode = root.get("created");
        if (createdNode != null) {
            poolCommandModel.setCreated(Instant.parse(createdNode.asText()));
        }

        final var modifiedNode = root.get("modified");
        if (modifiedNode != null) {
            poolCommandModel.setModified(Instant.parse(modifiedNode.asText()));
        }

        final var qualifierNode = root.get("qualifier");
        if (qualifierNode != null) {
            final var qualifier = PoolCommandQualifierEnum.valueOf(qualifierNode.asText());
            poolCommandModel.setQualifier(qualifier);

            final var bodyNode = root.get("body");
            if (bodyNode != null) {
                final var body = mapper.treeToValue(bodyNode, qualifier.getBodyClass());
                poolCommandModel.setBody(body);
            }
        }

        final var deletedNode = root.get("deleted");
        if (deletedNode != null) {
            poolCommandModel.setDeleted(Boolean.valueOf(deletedNode.asText()));
        }

        return poolCommandModel;
    }
}
