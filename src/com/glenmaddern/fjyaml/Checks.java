package com.glenmaddern.fjyaml;

public final class Checks {
    private Checks() {
    }


    public static void checkNotNull(final Object parameter) {
        if (parameter == null) throw new IllegalArgumentException("Parameter cannot be null");
    }
}
