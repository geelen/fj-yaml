package com.glenmaddern.fjyaml;

import static com.glenmaddern.fjyaml.Casts.UNSAFE_MAP;
import static com.glenmaddern.fjyaml.Casts.UNTYPED_LIST;
import static com.glenmaddern.fjyaml.Functions.newHashMap;
import static com.glenmaddern.fjyaml.Functions.transform;
import fj.F;
import static fj.P.p;
import fj.P1;
import fj.P2;
import fj.P3;
import fj.P4;
import fj.P5;

import java.util.List;
import java.util.Map;

public final class YamlTypedTranslator {
    private YamlTypedTranslator() {
    }

    public static <A> F<Object, P1<A>> namedEntries(final P2<String, F<Object, A>> converter) {
        return new F<Object, P1<A>>() {
            public P1<A> f(final Object o) {
                return p(getValue(UNSAFE_MAP.f(o), converter));
            }
        };
    }

    public static <A, B> F<Object, P2<A, B>> namedEntries(final P2<String, F<Object, A>> converter1, final P2<String, F<Object, B>> converter2) {
        return new F<Object, P2<A, B>>() {
            public P2<A, B> f(final Object o) {
                final Map map = UNSAFE_MAP.f(o);
                return p(getValue(map, converter1), getValue(map, converter2));
            }
        };
    }

    public static <A, B, C> F<Object, P3<A, B, C>> namedEntries(final P2<String, F<Object, A>> converter1, final P2<String, F<Object, B>> converter2,
            final P2<String, F<Object, C>> converter3) {
        return new F<Object, P3<A, B, C>>() {
            public P3<A, B, C> f(final Object o) {
                final Map map = UNSAFE_MAP.f(o);
                return p(getValue(map, converter1), getValue(map, converter2), getValue(map, converter3));
            }
        };
    }

    public static <A, B, C, D> F<Object, P4<A, B, C, D>> namedEntries(final P2<String, F<Object, A>> converter1,
            final P2<String, F<Object, B>> converter2, final P2<String, F<Object, C>> converter3, final P2<String, F<Object, D>> converter4) {
        return new F<Object, P4<A, B, C, D>>() {
            public P4<A, B, C, D> f(final Object o) {
                final Map map = UNSAFE_MAP.f(o);
                return p(getValue(map, converter1), getValue(map, converter2), getValue(map, converter3), getValue(map, converter4));
            }
        };
    }

    public static <A, B, C, D, E> F<Object, P5<A, B, C, D, E>> namedEntries(final P2<String, F<Object, A>> converter1,
            final P2<String, F<Object, B>> converter2, final P2<String, F<Object, C>> converter3, final P2<String, F<Object, D>> converter4,
            final P2<String, F<Object, E>> converter5) {
        return new F<Object, P5<A, B, C, D, E>>() {
            public P5<A, B, C, D, E> f(final Object o) {
                final Map map = UNSAFE_MAP.f(o);
                return p(getValue(map, converter1), getValue(map, converter2), getValue(map, converter3), getValue(map, converter4),
                        getValue(map, converter5));
            }
        };
    }

    public static <A> F<Object, List<A>> yamlToList(final F<Object, A> converter) {
        return new F<Object, List<A>>() {
            public List<A> f(final Object o) {
                return transform(UNTYPED_LIST.f(o), converter);
            }
        };
    }

    public static <A, B> F<Object, Map<A, B>> yamlToMap(final F<Object, A> keyConverter, final F<Object, B> valueConverter) {
        return new F<Object, Map<A, B>>() {
            public Map<A, B> f(final Object o) {
                final Map<A, B> results = newHashMap();
                for (final Map.Entry<Object, Object> entry : UNSAFE_MAP.f(o).entrySet()) {
                    results.put(keyConverter.f(entry.getKey()), valueConverter.f(entry.getValue()));
                }
                return results;
            }
        };
    }

    private static <A> A getValue(final Map map, final P2<String, F<Object, A>> converter) {
        final String key = converter._1();
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException("No named entry found with key '" + key + "'!");
        }
        return converter._2().f(map.get(key));
    }
}
