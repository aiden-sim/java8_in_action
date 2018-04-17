package chapter12;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.*;

import static java.time.temporal.TemporalAdjusters.*;

/**
 * Created by simjunbo on 2018-04-17.
 */
public class TemporalAdjustersTest {
    public static void main(String[] args) {
        // TemporalAdjusters 사용하기
        LocalDate date1 = LocalDate.of(2014, 3, 18);
        LocalDate date2 = date1.with(nextOrSame(DayOfWeek.SUNDAY));
        LocalDate date3 = date2.with(lastDayOfMonth());

        // 커스텀 TemporalAdjuster 구현하기
        // TemporalAdjuster 인터페이스를 구현하는 NextWorkingDay 클래스를 구현하시오.
        // 이 클래스는 날짜를 하루씩 다음날로 바꾸는데 이때 토요일과 일요일은 건너뛴다.
        // date = date.with(new NextWorkingDay());
        // 만약 날짜가 평일이 아니라면, 즉 토요일이나 월요일이라면 월요일로 이동한다.


        LocalDate d1 = LocalDate.now().with(new NextWorkingDay());

        // 람다 표현식
        LocalDate d2 = LocalDate.now().with(temporal -> {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dow == DayOfWeek.FRIDAY) {  // 금요일이면 3일 추가
                dayToAdd = 3;
            } else if (dow == DayOfWeek.SATURDAY) { // 토요일이면 2일 추가
                dayToAdd = 2;
            }
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        });

        // TemporalAdjuster 람다 표현식
        TemporalAdjuster nextWorkingDay = TemporalAdjusters.ofDateAdjuster(
                temporal -> {
                    DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
                    int dayToAdd = 1;
                    if (dow == DayOfWeek.FRIDAY) {  // 금요일이면 3일 추가
                        dayToAdd = 3;
                    } else if (dow == DayOfWeek.SATURDAY) { // 토요일이면 2일 추가
                        dayToAdd = 2;
                    }
                    return temporal.plus(dayToAdd, ChronoUnit.DAYS);
                });
        LocalDate d3 = LocalDate.now().with(nextWorkingDay);
    }
}

class NextWorkingDay implements TemporalAdjuster {

    @Override
    public Temporal adjustInto(Temporal temporal) {
        DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK)); // 현재 날짜 읽기
        int dayToAdd = 1;

        if (dow == DayOfWeek.FRIDAY) {  // 금요일이면 3일 추가
            dayToAdd = 3;
        } else if (dow == DayOfWeek.SATURDAY) { // 토요일이면 2일 추가
            dayToAdd = 2;
        }

        return temporal.plus(dayToAdd, ChronoUnit.DAYS); // 적정한 날 수만큼 추가된 날짜를 반환
    }
}
