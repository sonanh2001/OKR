package org.aibles.okr3.utils;

import static org.aibles.okr3.constants.CommonConstants.TWELVE_AM;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateUtil {
  private static final DateTimeFormatter FORMATTER_TO_EPOCH =
      DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
  private static final DateTimeFormatter FORMATTER_TO_STRING =
      DateTimeFormatter.ofPattern("dd/MM/yyyy");

  public static Long convertStringToEpoch(String date) {
    LocalDateTime localDateTime = LocalDateTime.parse(date + " " + TWELVE_AM, FORMATTER_TO_EPOCH);
    return localDateTime.toEpochSecond(ZoneOffset.UTC);
  }

  public static String convertEpochToString(Long epochSeconds) {
    LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(epochSeconds, 0, ZoneOffset.UTC);
    return localDateTime.format(FORMATTER_TO_STRING);
  }
}
