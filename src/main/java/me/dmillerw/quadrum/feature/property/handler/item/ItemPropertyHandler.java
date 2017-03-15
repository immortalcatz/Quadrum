package me.dmillerw.quadrum.feature.property.handler.item;

import me.dmillerw.quadrum.feature.data.ItemData;
import me.dmillerw.quadrum.feature.property.handler.PropertyHandler;
import me.dmillerw.quadrum.item.ItemQuadrum;
import net.minecraft.item.Item;

/**
 * @author dmillerw
 */
public abstract class ItemPropertyHandler<T> extends PropertyHandler<T> {

    public Item constructItem(ItemData data) {
        return new ItemQuadrum(data);
    }
}
