package me.dmillerw.quadrum.feature.property.handler.item;

import me.dmillerw.quadrum.feature.data.ItemData;
import me.dmillerw.quadrum.item.sub.ItemQuadrumConsumable;
import net.minecraft.item.Item;

/**
 * @author dmillerw
 */
public class ConsumableHandler extends ItemPropertyHandler {

    @Override
    public Item constructItem(ItemData data) {
        return new ItemQuadrumConsumable(data);
    }

    @Override
    public boolean hasSubtypes() {
        return false;
    }

    @Override
    public String[] getSubtypes() {
        return new String[0];
    }
}
