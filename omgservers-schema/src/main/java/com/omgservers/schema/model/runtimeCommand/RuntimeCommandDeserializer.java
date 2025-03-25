package com.omgservers.schema.model.runtimeCommand;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;

public class RuntimeCommandDeserializer extends StdDeserializer<RuntimeCommandModel> {

    public RuntimeCommandDeserializer() {
        this(null);
    }

    public RuntimeCommandDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public RuntimeCommandModel deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JacksonException {
        try {
            return deserialize(parser);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    RuntimeCommandModel deserialize(JsonParser parser) throws IOException {
        final var mapper = (ObjectMapper) parser.getCodec();
        final var root = (JsonNode) mapper.readTree(parser);

        final var runtimeCommandModel = new RuntimeCommandModel();

        final var idNode = root.get("id");
        if (idNode != null) {
            runtimeCommandModel.setId(Long.valueOf(idNode.asText()));
        }

        final var idempotencyKeyNode = root.get("idempotency_key");
        if (idempotencyKeyNode != null) {
            runtimeCommandModel.setIdempotencyKey(idempotencyKeyNode.asText());
        }

        final var runtimeIdNode = root.get("runtimeId");
        if (runtimeIdNode != null) {
            runtimeCommandModel.setRuntimeId(Long.valueOf(runtimeIdNode.asText()));
        }

        final var createdNode = root.get("created");
        if (createdNode != null) {
            runtimeCommandModel.setCreated(Instant.parse(createdNode.asText()));
        }

        final var modifiedNode = root.get("modified");
        if (modifiedNode != null) {
            runtimeCommandModel.setModified(Instant.parse(modifiedNode.asText()));
        }

        final var qualifierNode = root.get("qualifier");
        if (qualifierNode != null) {
            final var qualifier = RuntimeCommandQualifierEnum.valueOf(qualifierNode.asText());
            runtimeCommandModel.setQualifier(qualifier);

            final var bodyNode = root.get("body");
            if (bodyNode != null) {
                final var body = mapper.treeToValue(bodyNode, qualifier.getBodyClass());
                runtimeCommandModel.setBody(body);
            }
        }

        final var deletedNode = root.get("deleted");
        if (deletedNode != null) {
            runtimeCommandModel.setDeleted(Boolean.valueOf(deletedNode.asText()));
        }

        return runtimeCommandModel;
    }
}
