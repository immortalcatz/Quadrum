package me.dmillerw.quadrum.lib.trait;

/**
 * @author dmillerw
 */
@FunctionalInterface
public interface DefaultValue<T> {

    public T get();
}
