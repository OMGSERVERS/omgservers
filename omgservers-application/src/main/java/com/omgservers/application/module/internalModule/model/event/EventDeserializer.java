package com.omgservers.application.module.internalModule.model.event;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;

public class EventDeserializer extends StdDeserializer<EventModel> {

    public EventDeserializer() {
        this(null);
    }

    public EventDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public EventModel deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JacksonException {
        try {
            return deserialize(parser);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    EventModel deserialize(JsonParser parser) throws IOException {
        final var mapper = (ObjectMapper) parser.getCodec();
        final var root = (JsonNode) mapper.readTree(parser);

        final var eventModel = new EventModel();

        final var idNode = root.get("id");
        if (idNode != null) {
            eventModel.setId(Long.valueOf(idNode.asText()));
        }

        final var createdNode = root.get("created");
        if (createdNode != null) {
            eventModel.setCreated(Instant.parse(createdNode.asText()));
        }

        final var modifiedNode = root.get("modified");
        if (modifiedNode != null) {
            eventModel.setModified(Instant.parse(modifiedNode.asText()));
        }

        final var groupIdNode = root.get("groupId");
        if (groupIdNode != null) {
            eventModel.setGroupId(Long.valueOf(groupIdNode.asText()));
        }

        final var qualifierNode = root.get("qualifier");
        if (qualifierNode != null) {
            final var qualifier = EventQualifierEnum.valueOf(qualifierNode.asText());
            eventModel.setQualifier(qualifier);

            final var bodyNode = root.get("body");
            if (bodyNode != null) {
                final var body = mapper.treeToValue(bodyNode, qualifier.getBodyClass());
                eventModel.setBody(body);
            }
        }

        final var statusNode = root.get("status");
        if (statusNode != null) {
            final var status = EventStatusEnum.valueOf(statusNode.asText());
            eventModel.setStatus(status);
        }

        return eventModel;
    }
}
