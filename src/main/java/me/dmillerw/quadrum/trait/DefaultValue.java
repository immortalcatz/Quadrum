package me.dmillerw.quadrum.trait;

/**
 * @author dmillerw
 */
@FunctionalInterface
public interface DefaultValue<T> {

    public T get();
}
