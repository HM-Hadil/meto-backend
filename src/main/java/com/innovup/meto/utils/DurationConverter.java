package com.innovup.meto.utils;

import com.innovup.meto.request.SurgeryDuration;

public class DurationConverter {

    public static Integer toSeconds(SurgeryDuration d) {
        return 86400 * d.getDays() + 3600 * d.getHours() + 60 * d.getMinutes() + d.getSeconds();
    }

    public static Integer convertToMinutes(SurgeryDuration d) {
        return toSeconds(d) / 60;
    }
}
