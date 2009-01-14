package com.glenmaddern.fjyaml;

import fj.F;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public final class Functions {
    private Functions() {
    }

    public static <A,B> List<B> transform(final List<A> list, final F<A, B> function) {
        final fj.data.List<A> fjList = fj.data.List.iterableList(list);
        return fj.data.Java.<B>List_ArrayList().f(fjList.map(function));
    }

    public static <K, V> Map<K,V> newHashMap() {
        return new HashMap<K,V>();
    }
}
