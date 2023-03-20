package com.dado.quanlytailieu.config;

import static java.time.format.DateTimeFormatter.ofPattern;

import com.dado.quanlytailieu.util.TrimmingStringDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;
import lombok.experimental.UtilityClass;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@UtilityClass
public class Default {

    public static final TimeZone TIMEZONE = TimeZone.getTimeZone("Asia/Tokyo");
    public static final Locale LOCALE = Locale.JAPANESE;

    public static final String DATE_PATTERN = "yyyy/M/d";
    public static final String TIME_PATTERN = "H:mm:ss";
    public static final String DATE_TIME_PATTERN = DATE_PATTERN + " " + TIME_PATTERN;

    public static final DateTimeFormatter DATE_TIME_FORMATTER = ofPattern(DATE_TIME_PATTERN);
    public static final DateTimeFormatter DATE_FORMATTER = ofPattern(DATE_PATTERN);
    public static final DateTimeFormatter TIME_FORMATTER = ofPattern(TIME_PATTERN);

    public static final String UTF8_BOM =
            new String(new byte[] {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF}, StandardCharsets.UTF_8);

    public static void setDefault(ObjectMapper mapper) {
        mapper.setTimeZone(Default.TIMEZONE);
        mapper.setLocale(Default.LOCALE);
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDate.class, new LocalDateSerializer(Default.DATE_FORMATTER));
        module.addSerializer(
                LocalDateTime.class, new LocalDateTimeSerializer(Default.DATE_TIME_FORMATTER));
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(Default.DATE_FORMATTER));
        module.addDeserializer(
                LocalDateTime.class, new LocalDateTimeDeserializer(Default.DATE_TIME_FORMATTER));
        module.addDeserializer(String.class, new TrimmingStringDeserializer());
        mapper.registerModule(module);
    }

    public static void setDefault(Jackson2ObjectMapperBuilder builder) {
        builder.timeZone(Default.TIMEZONE);
        builder.locale(Default.LOCALE);
        builder.serializers(new LocalDateSerializer(Default.DATE_FORMATTER));
        builder.serializers(new LocalDateTimeSerializer(Default.DATE_TIME_FORMATTER));
        builder.deserializers(new LocalDateDeserializer(Default.DATE_FORMATTER));
        builder.deserializers(new LocalDateTimeDeserializer(Default.DATE_TIME_FORMATTER));
    }

    public static void setDefault(FormatterRegistry registry) {
        var dateTimeRegistrar = new DateTimeFormatterRegistrar();
        dateTimeRegistrar.setDateTimeFormatter(Default.DATE_TIME_FORMATTER);
        dateTimeRegistrar.setDateFormatter(Default.DATE_FORMATTER);
        dateTimeRegistrar.setTimeFormatter(Default.TIME_FORMATTER);
        dateTimeRegistrar.registerFormatters(registry);
    }
}

