package com.afj.solution.buyitapp.common;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.extern.slf4j.Slf4j;

import com.afj.solution.buyitapp.exception.BadRequestException;

import static java.util.Objects.isNull;

/**
 * @author Tomash Gombosh
 */
@Slf4j
public class Adapters {
    private static final String ZONED_DATE_TIME_FORMAT = "MM/dd/yyyy";

    /**
     * @author Tomash Gombosh
     */
    public static class ZonedDateTimeAdapter extends TypeAdapter<ZonedDateTime> {
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @Override
        public void write(final JsonWriter jsonWriter, final ZonedDateTime value) throws IOException {
            if (isNull(value)) {
                jsonWriter.nullValue();
            } else {
                final String format = value
                        .withZoneSameInstant(ZoneId.of(ZoneOffset.UTC.getId()))
                        .format(FORMATTER);
                jsonWriter.value(format);
            }
        }

        @Override
        public ZonedDateTime read(final JsonReader jsonReader) {
            final JsonElement jElement = JsonParser.parseReader(jsonReader);
            final String element = jElement.getAsString();
            if (!element.isEmpty()) {
                final ZonedDateTime parsed = ZonedDateTime
                        .parse(element, FORMATTER)
                        .withZoneSameInstant(ZoneId.of(ZoneOffset.UTC.getId()));
                log.debug("ZonedDateTime formatting({}) to the small date {} to the {}", FORMATTER, element, parsed);
                return parsed;

            }
            throw new BadRequestException(String.format("error.invalid.date.%s", element));
        }
    }

    /**
     * @author Tomash Gombosh
     */
    public static class ZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {

        @Override
        public ZonedDateTime deserialize(final com.fasterxml.jackson.core.JsonParser jsonParser,
                                         final DeserializationContext deserializationContext) throws IOException {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ZONED_DATE_TIME_FORMAT);
            final LocalDate date = LocalDate.parse(jsonParser.getText(), formatter);

            return date.atStartOfDay(ZoneId.of(ZoneOffset.UTC.getId()));
        }
    }
}
