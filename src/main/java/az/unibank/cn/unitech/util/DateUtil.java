package az.unibank.cn.unitech.util;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

import static java.time.ZoneOffset.UTC;

@Data
public class DateUtil {
    private LocalDateTime localDateTime;

    public static DateUtil now() {
        return new DateUtil().setLocalDateTime(LocalDateTime.now());
    }

    public DateUtil plusMinutes(long minutes) {
        return this.setLocalDateTime(getLocalDateTime().plusMinutes(minutes));
    }

    public DateUtil plusMonths(long months) {
        return this.setLocalDateTime(getLocalDateTime().plusMonths(months));
    }

    public Date toDate() {
        return Date.from(localDateTime.toInstant(UTC));
    }
}
