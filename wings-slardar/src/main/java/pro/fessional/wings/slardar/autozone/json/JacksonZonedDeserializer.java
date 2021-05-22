package pro.fessional.wings.slardar.autozone.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import org.springframework.context.i18n.LocaleContextHolder;
import pro.fessional.mirana.time.DateParser;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;

/**
 * @author trydofor
 * @since 2019-09-01
 */
public class JacksonZonedDeserializer extends InstantDeserializer<ZonedDateTime> {

    private final List<DateTimeFormatter> formats;

    public JacksonZonedDeserializer(DateTimeFormatter formatter, List<DateTimeFormatter> formats) {
        super(ZonedDateTime.class,
                formatter,
                temporal -> DateParser.parseZoned(temporal, LocaleContextHolder.getTimeZone().toZoneId()),
                a -> ZonedDateTime.ofInstant(Instant.ofEpochMilli(a.value), a.zoneId),
                a -> ZonedDateTime.ofInstant(Instant.ofEpochSecond(a.integer, a.fraction), a.zoneId),
                (zonedDateTime, zoneId) -> zonedDateTime,
                false // keep zero offset and Z separate since zones explicitly supported
        );
        this.formats = formats;
    }

    @Override
    public ZonedDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String str = parser.getText().trim();
        TemporalAccessor tma = DateParser.parseTemporal(str, formats, true);
        if (tma == null) {
            return super.deserialize(parser, context);
        }

        final ZoneId zid = LocaleContextHolder.getTimeZone().toZoneId();
        return DateParser.parseZoned(tma, zid);
    }
}
