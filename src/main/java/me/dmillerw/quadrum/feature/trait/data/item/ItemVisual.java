package me.dmillerw.quadrum.feature.trait.data.item;

import me.dmillerw.quadrum.feature.trait.util.Mergeable;

/**
 * @author dmillerw
 */
public class ItemVisual extends Mergeable<ItemVisual> {

    public boolean enchanted = false;

    @Override
    public ItemVisual merge(ItemVisual other) {
        ItemVisual visual = new ItemVisual();

        visual.enchanted = merge(other, "enchanted", this.enchanted, other.enchanted);

        return visual;
    }
}
