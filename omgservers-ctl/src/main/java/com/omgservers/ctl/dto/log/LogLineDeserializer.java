package com.omgservers.ctl.dto.log;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class LogLineDeserializer extends StdDeserializer<LogLineDto> {

    public LogLineDeserializer() {
        this(null);
    }

    public LogLineDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LogLineDto deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JacksonException {
        try {
            return deserialize(parser);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    LogLineDto deserialize(JsonParser parser) throws IOException {
        final var mapper = (ObjectMapper) parser.getCodec();
        final var root = (JsonNode) mapper.readTree(parser);

        final var logLine = new LogLineDto();

        final var qualifierNode = root.get("q");
        if (qualifierNode != null) {
            final var qualifier = LogLineQualifierEnum.valueOf(qualifierNode.asText());
            logLine.setQualifier(qualifier);

            final var bodyNode = root.get("b");
            if (bodyNode != null) {
                final var body = mapper.treeToValue(bodyNode, qualifier.getBodyClass());
                logLine.setBody(body);
            }
        }

        return logLine;
    }
}
