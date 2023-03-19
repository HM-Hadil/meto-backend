package com.innovup.meto.utils;

import com.innovup.meto.entity.SurgeryDuration;
import com.innovup.meto.request.SurgeryDurationRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DurationConverter {

    public static Long toSeconds(SurgeryDuration d) {
        return 86400L * d.getDays() + 3600L * d.getHours() + 60L * d.getMinutes() + d.getSeconds();
    }

    public static Long toSeconds(SurgeryDurationRequest d) {
        return 86400L * d.getDays() + 3600L * d.getHours() + 60L * d.getMinutes() + d.getSeconds();
    }

    public static Long tooMinutes(SurgeryDuration d) {
        return toSeconds(d) / 60;
    }
}
