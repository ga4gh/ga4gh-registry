package org.ga4gh.registry.util.serialize.serializers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DateSerializer extends VariableDepthSerializer<Date> {

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final long serialVersionUID = 1L;

    public DateSerializer() {
        super(Date.class);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public DateSerializer(String[] serializedRelationalAttributes) {
        super(Date.class, serializedRelationalAttributes);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public void serialize (Date value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException, JsonProcessingException {
        gen.writeString(formatter.format(value));
    }

    public SimpleDateFormat getFormatter() {
        return formatter;
    }
}