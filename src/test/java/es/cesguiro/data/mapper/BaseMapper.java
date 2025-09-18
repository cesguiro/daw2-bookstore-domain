package es.cesguiro.data.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BaseMapper {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    protected static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    protected static Integer parseInt(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return Integer.parseInt(value);
    }
}
