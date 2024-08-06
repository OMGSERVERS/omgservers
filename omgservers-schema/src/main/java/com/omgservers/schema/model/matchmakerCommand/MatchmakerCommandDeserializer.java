package com.omgservers.schema.model.matchmakerCommand;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;

public class MatchmakerCommandDeserializer extends StdDeserializer<MatchmakerCommandModel> {

    public MatchmakerCommandDeserializer() {
        this(null);
    }

    public MatchmakerCommandDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public MatchmakerCommandModel deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JacksonException {
        try {
            return deserialize(parser);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    MatchmakerCommandModel deserialize(JsonParser parser) throws IOException {
        final var mapper = (ObjectMapper) parser.getCodec();
        final var root = (JsonNode) mapper.readTree(parser);

        final var matchmakerCommandModel = new MatchmakerCommandModel();

        final var idNode = root.get("id");
        if (idNode != null) {
            matchmakerCommandModel.setId(Long.valueOf(idNode.asText()));
        }

        final var idempotencyKeyNode = root.get("idempotency_key");
        if (idempotencyKeyNode != null) {
            matchmakerCommandModel.setIdempotencyKey(idempotencyKeyNode.asText());
        }

        final var matchmakerIdNode = root.get("matchmaker_id");
        if (matchmakerIdNode != null) {
            matchmakerCommandModel.setMatchmakerId(Long.valueOf(matchmakerIdNode.asText()));
        }

        final var createdNode = root.get("created");
        if (createdNode != null) {
            matchmakerCommandModel.setCreated(Instant.parse(createdNode.asText()));
        }

        final var modifiedNode = root.get("modified");
        if (modifiedNode != null) {
            matchmakerCommandModel.setModified(Instant.parse(modifiedNode.asText()));
        }

        final var qualifierNode = root.get("qualifier");
        if (qualifierNode != null) {
            final var qualifier = MatchmakerCommandQualifierEnum.valueOf(qualifierNode.asText());
            matchmakerCommandModel.setQualifier(qualifier);

            final var bodyNode = root.get("body");
            if (bodyNode != null) {
                final var body = mapper.treeToValue(bodyNode, qualifier.getBodyClass());
                matchmakerCommandModel.setBody(body);
            }
        }

        final var deletedNode = root.get("deleted");
        if (deletedNode != null) {
            matchmakerCommandModel.setDeleted(Boolean.valueOf(deletedNode.asText()));
        }

        return matchmakerCommandModel;
    }
}
