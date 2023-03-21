package com.innovup.meto.core.schema;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ComSchemaConstantSize {
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
    public static final int XXS_DESCRIPTION = 16;
    public static final int XS_DESCRIPTION = 32;
    public static final int S_DESCRIPTION = 64;
    public static final int M_DESCRIPTION = 128;
    public static final int L_DESCRIPTION = 256;
    public static final int XL_DESCRIPTION = 512;
    public static final int XXL_DESCRIPTION = 1024;
    public static final int XXXL_DESCRIPTION = 2048;
    public static final int PHONE_NUMBER = 16;
    public static final int REFERENCE = 40;
    public static final int IMPORTANCE_LEVEL = 10;
}