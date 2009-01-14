package com.glenmaddern.fjyaml;

import static com.glenmaddern.fjyaml.Casts.DOUBLE;
import static com.glenmaddern.fjyaml.Casts.INT;
import static com.glenmaddern.fjyaml.Casts.STRING;
import static com.glenmaddern.fjyaml.YamlTypedTranslator.namedEntries;
import static com.glenmaddern.fjyaml.YamlTypedTranslator.yamlToList;
import static com.glenmaddern.fjyaml.YamlTypedTranslator.yamlToMap;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.internal.expect.state.matcher.AssertException;
import com.googlecode.instinct.internal.expect.state.matcher.AssertExceptionImpl;
import com.googlecode.instinct.marker.annotate.Specification;
import static fj.P.p;
import fj.P1;
import fj.P2;
import fj.P3;
import org.junit.runner.RunWith;
import org.jvyaml.YAML;

import static java.util.Arrays.asList;
import java.util.List;

@RunWith(InstinctRunner.class)
public final class YamlTypedTranslatorContext {
    private final AssertException assertException = new AssertExceptionImpl();
    
    @Specification
    public void shouldConvertASimpleString() {
        final Object o = YAML.load("---\nkey: value");
        final P1<String> converted = namedEntries(p("key", STRING)).f(o);
        expect.that(converted._1()).isEqualTo("value");
    }

    @Specification
    public void shouldConvertASimpleInt() {
        final Object o = YAML.load("---\nkey: 5");
        final P1<Integer> converted = namedEntries(p("key", INT)).f(o);
        expect.that(converted._1()).isEqualTo(5);
    }

    @Specification
    public void shouldConvertASimpleDouble() {
        final Object o = YAML.load("---\nkey: 5.5");
        final P1<Double> converted = namedEntries(p("key", DOUBLE)).f(o);
        expect.that(converted._1()).isEqualTo(5.5);
    }

    @Specification
    public void shouldConvertYamlWithTwoKeysAndValues() {
        final Object o = YAML.load("---\n" +
                "name: some name\n" +
                "number: 55");
        final P2<String, Integer> converted = namedEntries(p("name", STRING), p("number", INT)).f(o);
        expect.that(converted._1()).isEqualTo("some name");
        expect.that(converted._2()).isEqualTo(55);
    }

    @Specification
    public void shouldConvertYamlWithThreeNamedEntries() {
        final Object o = YAML.load("---\n" +
                "name: some name\n" +
                "number: 55\n" +
                "factor: 1.8");
        final P3<String, Integer, Double> converted = namedEntries(p("name", STRING), p("number", INT), p("factor", DOUBLE)).f(o);
        expect.that(converted._1()).isEqualTo("some name");
        expect.that(converted._2()).isEqualTo(55);
        expect.that(converted._3()).isEqualTo(1.8);
    }

    @Specification
    public void shouldConvertYamlWithAList() {
        final Object o = YAML.load("---\n" +
                "- name: name1\n" +
                "  number: 55\n" +
                "- name: name2\n" +
                "  number: 56\n");
        final List<P2<String, Integer>> convertedList = yamlToList(namedEntries(p("name", STRING), p("number", INT))).f(o);
        expect.that(convertedList).isOfSize(2);
        expect.that(convertedList.get(0)).isEqualTo(p("name1", 55));
        expect.that(convertedList.get(1)).isEqualTo(p("name2", 56));
    }

    @Specification
    public void shouldConvertYamlWithNesting() {
        final Object o = YAML.load("---\n" +
                "entries:\n" +
                "  - name: A\n" +
                "    values: [10, 20, 30]\n" +
                "    factor: 2.0\n" +
                "  - name: B\n" +
                "    values: [100, 200, 300]\n" +
                "    factor: 1.5\n");
        final List<P3<String, List<Integer>, Double>> convertedList =
                namedEntries(p("entries", yamlToList(namedEntries(p("name", STRING), p("values", yamlToList(INT)), p("factor", DOUBLE))))).f(o)._1();
        expect.that(convertedList).isOfSize(2);
        expect.that(convertedList.get(0)).isEqualTo(p("A", asList(10, 20, 30), 2.0));
        expect.that(convertedList.get(1)).isEqualTo(p("B", asList(100, 200, 300), 1.5));
    }


    @Specification
    public void shouldFailGivenBadTypes() {
        assertException.assertThrows(ClassCastException.class, "java.lang.String cannot be cast to java.lang.Double", new Runnable() {
            public void run() {
                final Object o = YAML.load("---\n" +
                        "H: 1.0\n" +
                        "M: foo\n" +
                        "L: 1.8");
                yamlToMap(STRING, DOUBLE).f(o);
            }
        });
    }

    @Specification
    public void shouldFailIfKeyDoesntExist() {
        assertException.assertThrows(IllegalArgumentException.class, "No named entry found with key 'foo'!", new Runnable() {
            public void run() {
                final Object o = YAML.load("---\nkey: value");
                namedEntries(p("foo", STRING)).f(o);
            }
        });
    }
}
