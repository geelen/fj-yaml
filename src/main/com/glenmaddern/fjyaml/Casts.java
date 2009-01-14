package com.glenmaddern.fjyaml;

import fj.F;

import java.util.Map;
import java.util.List;

public final class Casts {
    private Casts() {
    }

    public static final F<Object, String> STRING = new F<Object, String>() {
        public String f(final Object o) {
            Checks.checkNotNull(o);
            return (String) o;
        }
    };
    public static final F<Object, Double> DOUBLE = new F<Object, Double>() {
        public Double f(final Object o) {
            Checks.checkNotNull(o);
            return (Double) o;
        }
    };
    public static final F<Object, Integer> INT = new F<Object, Integer>() {
        public Integer f(final Object o) {
            return ((Long) o).intValue();
        }
    };
    static final F<Object, Map<Object, Object>> UNSAFE_MAP = uncheckedCast();
    static final F<Object, List<Object>> UNTYPED_LIST = uncheckedCast();

    @SuppressWarnings({"unchecked"})
    static <A> F<Object, A> uncheckedCast() {
        return new F<Object, A>() {
            public A f(final Object o) {
                Checks.checkNotNull(o);
                return (A) o;
            }
        };
    }
}
