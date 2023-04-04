package com.innovup.meto.util;

import com.innovup.meto.entity.ChirurgieDuration;
import com.innovup.meto.request.ChirurgieDurationRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class DurationConverter {


    public static Long toSeconds(ChirurgieDuration d) {
        return 86400L * d.getDays() + 3600L * d.getHours() + 60L * d.getMinutes() + d.getSeconds();
    }

    public static Long toSeconds(ChirurgieDurationRequest d) {
        return 86400L * d.getDays() + 3600L * d.getHours() + 60L * d.getMinutes() + d.getSeconds();
    }

    public static Long tooMinutes(ChirurgieDuration d) {
        return toSeconds(d) / 60;
    }
}
