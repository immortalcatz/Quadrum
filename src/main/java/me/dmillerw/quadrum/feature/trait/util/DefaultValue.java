package me.dmillerw.quadrum.feature.trait.util;

/**
 * @author dmillerw
 */
@FunctionalInterface
public interface DefaultValue<T> {

    public T get();
}
