package me.dmillerw.quadrum.feature.property.handler;

import me.dmillerw.quadrum.feature.data.QuadrumData;

/**
 * @author dmillerw
 */
public abstract class PropertyHandler<T> {

    public QuadrumData parent;
    public T data;

    public abstract boolean hasSubtypes();
    public abstract String[] getSubtypes();
}
