package com.glenmaddern.fjyaml;

import static com.glenmaddern.fjyaml.Casts.INT;
import static com.glenmaddern.fjyaml.YamlTypedTranslator.yamlToMap;
import static com.glenmaddern.fjyaml.YamlTypedTranslatorWithEnum.Weight.H;
import static com.glenmaddern.fjyaml.YamlTypedTranslatorWithEnum.Weight.L;
import static com.glenmaddern.fjyaml.YamlTypedTranslatorWithEnum.Weight.M;
import static com.glenmaddern.fjyaml.YamlTypedTranslatorWithEnum.Weight.valueOf;
import com.google.common.collect.ImmutableMap;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.Specification;
import fj.F;
import org.junit.runner.RunWith;
import org.jvyaml.YAML;

import java.util.Map;

@RunWith(InstinctRunner.class)
public final class YamlTypedTranslatorWithEnum {
    private static final F<Object, Weight> WEIGHT = new F<Object, Weight>() {
        public Weight f(final Object o) {
            return valueOf((String) o);
        }
    };

    enum Weight {
        H, M, L
    }

    @Specification
    public void shouldConvertToAMap() {
        final Object o = YAML.load("---\n" +
                "H: 1\n" +
                "M: 3\n" +
                "L: 18");
        final Map<Weight, Integer> map = yamlToMap(WEIGHT, INT).f(o);
        expect.that(map).isEqualTo(ImmutableMap.of(H, 1, M, 3, L, 18));
    }
}