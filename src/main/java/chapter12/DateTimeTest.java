package chapter12;

import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

/**
 * Created by simjunbo on 2018-04-12.
 */
public class DateTimeTest {
    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2014, 3, 18); // 2014-03-18
        int year = date.getYear();                  // 2014
        Month month = date.getMonth();              // MARCH
        int day = date.getDayOfMonth();             // 18
        DayOfWeek dow = date.getDayOfWeek();        // TUESDAY
        int len = date.lengthOfMonth();             // 31    (3월의 일수)
        boolean leap = date.isLeapYear();           // false (윤년이 아님)

        // 현재 날짜 정보
        LocalDate today = LocalDate.now();
        System.out.println(today);


        // TemporalField를 이용해서 LocalDate 값 읽기
        int year2 = date.get(ChronoField.YEAR);
        int month2 = date.get(ChronoField.MONTH_OF_YEAR);
        int day2 = date.get(ChronoField.DAY_OF_MONTH);

        // LocalTime 만들고 값 읽기
        LocalTime time = LocalTime.of(13, 45, 20);
        int hour = time.getHour();      // 13
        int minute = time.getMinute();  // 45
        int second = time.getSecond();  // 20

        // 날짜와 시간 문자열로 인스턴스 만들기
        LocalDate date2 = LocalDate.parse("2014-03-18");
        LocalTime time2 = LocalTime.parse("13:45:20");


        // LocalDateTime (LocalDate + LocalTime)
        LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20);
        LocalDateTime dt2 = LocalDateTime.of(date, time);
        LocalDateTime dt3 = date.atTime(13, 45, 20);
        LocalDateTime dt4 = date.atTime(time);
        LocalDateTime dt5 = time.atDate(date);


/*        LocalDate date1 = dt1.toLocalDate();
        LocalDate time1 = dt1.toLocalTime();*/

        // 기계의 날짜 Instant
        Instant.ofEpochSecond(3);
        Instant.ofEpochSecond(3, 0);
        Instant.ofEpochSecond(2, 1_000_000_000);
        Instant.ofEpochSecond(4, -1_000_000_000);


/*        Duration d1 = Duration.between(time, time2);
        Duration d2 = Duration.between(dateTime1, dateTime2);
        Duration d3 = Duration.between(instant1, instant2);*/


        // Period
        Period tenDays = Period.between(LocalDate.of(2014, 3, 8), LocalDate.of(2014, 3, 18));

        // Duration과 Period 만들기
        Duration threeMinutes = Duration.ofMinutes(3);
        Duration threeMinutes2 = Duration.of(3, ChronoUnit.MINUTES);

        Period tenDays2 = Period.ofDays(10);
        Period threeWeeks = Period.ofWeeks(3);
        Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);

        // 절대적인 방식으로 LocalDate의 속성 바꾸기
        LocalDate abDate1 = LocalDate.of(2014, 3, 18);    // 2014-03-18
        LocalDate abDate2 = abDate1.withYear(2011);       // 2011-03-18
        LocalDate abDate3 = abDate2.withDayOfMonth(25);   // 2011-03-25
        LocalDate abDate4 = abDate3.with(ChronoField.MONTH_OF_YEAR, 9); // 2011-09-25

        // 상대적인 방식으로 LocalDate 속성 바꾸기
        LocalDate reDate1 = LocalDate.of(2014, 3, 18);     // 2014-03-18
        LocalDate reDate2 = reDate1.plusWeeks(1);          // 2014-03-25
        LocalDate reDate3 = reDate2.minusYears(3);         // 2011-03-25
        LocalDate reDate4 = reDate3.plus(6, ChronoUnit.MONTHS); // 2011-09-25

        // DateTimeFormatter ( 날짜 -> 문자열 )
        LocalDate localDate = LocalDate.of(2014, 3, 18);
        String s1 = localDate.format(DateTimeFormatter.BASIC_ISO_DATE); // 20140318
        String s2 = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE); // 2014-03-18

        // DateTimeFormatter ( 문자열 -> 날짜 )
        LocalDate date11 = LocalDate.parse("20140318", DateTimeFormatter.BASIC_ISO_DATE);
        LocalDate date22 = LocalDate.parse("2014-03-18", DateTimeFormatter.ISO_LOCAL_DATE);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate1 = LocalDate.of(2014, 3, 18);
        String formattedDate = localDate1.format(formatter);
        LocalDate localDate2 = LocalDate.parse(formattedDate, formatter);

        // 지역화된 DateTimeFormatter 만들기
        DateTimeFormatter italianFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN);
        LocalDate localDateFormatter1 = LocalDate.of(2014, 3, 18);
        String formattedDate2 = localDateFormatter1.format(italianFormatter);
        LocalDate localDateFormatter2 = LocalDate.parse(formattedDate2, italianFormatter);

        // DateTimeFormatter 만들기
        DateTimeFormatter italianFormatter2 = new DateTimeFormatterBuilder()
                .appendText(ChronoField.DAY_OF_MONTH)
                .appendLiteral(". ")
                .appendText(ChronoField.MONTH_OF_YEAR)
                .appendLiteral(" ")
                .appendText(ChronoField.YEAR)
                .parseCaseInsensitive()
                .toFormatter(Locale.ITALIAN);


        // 특정 시점에 시간대 적용
        ZoneId romeZone = ZoneId.of("Europe/Rome");

        LocalDate zonedDate1 = LocalDate.of(2014, Month.MARCH, 18);
        ZonedDateTime zdt1 = zonedDate1.atStartOfDay(romeZone);

        LocalDateTime zonedDate2 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
        ZonedDateTime zdt2 = zonedDate2.atZone(romeZone);

        Instant instant = Instant.now();
        ZonedDateTime zdt3 = instant.atZone(romeZone);


        // 뉴욕은 런던보다 5시간 느리다 (권장X 서머타임 제대로 처리 못함)
        ZoneOffset newYorkOffset = ZoneOffset.of("-05:00");

        LocalDateTime offsetDateTime = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
        OffsetDateTime dateTimeInNewYokr = OffsetDateTime.of(offsetDateTime, newYorkOffset);

        // ChronoLocalDate
        LocalDate chronoDate = LocalDate.of(2014, Month.MARCH, 18);
        JapaneseDate japaneseDate = JapaneseDate.from(chronoDate);

        Chronology japaneseChronology = Chronology.ofLocale(Locale.JAPAN);
        ChronoLocalDate now = japaneseChronology.dateNow();

    }
}
