package ru.ildar;

import java.util.ArrayList;
import java.util.List;

public class CollectionsUtil
{
    public static <T> List<T> getListFromIterable(Iterable<T> iter)
    {
        List<T> res = new ArrayList<>();
        iter.forEach(res::add);
        return res;
    }
}
