package org.hrsninja.api.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;

@UtilityClass
public class TimeUtil {
    public static final LocalDate FIXED_DATE = LocalDate.EPOCH;
    public static final LocalDateTime FIXED_DATETIME = LocalDate.EPOCH.atTime(0,0);
}
