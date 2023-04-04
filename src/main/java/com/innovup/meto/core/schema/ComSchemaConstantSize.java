package com.innovup.meto.core.schema;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ComSchemaConstantSize {
    public static final int CODE = 1;
    public static final int LOGIN = 32;
    public static final int PASSWORD = 128;
    public static final int EMAIL = 320;    //64 + 1(@) + 255 = 320
    public static final int XXS_VALUE = 1;
    public static final int XS_VALUE = 8;
    public static final int S_VALUE = 16;
    public static final int M_VALUE = 32;
    public static final int L_VALUE = 64;
    public static final int XL_VALUE = 128;
    public static final int XXL_VALUE = 256;
    public static final int XL_DESCRIPTION = 512;

}