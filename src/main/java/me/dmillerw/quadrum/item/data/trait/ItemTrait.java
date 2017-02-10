package me.dmillerw.quadrum.item.data.trait;

import me.dmillerw.quadrum.item.ItemQuadrum;
import me.dmillerw.quadrum.item.data.ItemData;
import me.dmillerw.quadrum.lib.trait.Trait;
import net.minecraft.item.ItemStack;

/**
 * @author dmillerw
 */
public abstract class ItemTrait<T> extends Trait<ItemStack, T> {

    @Override
    public final T getValue(ItemStack state) {
        ItemData data = ((ItemQuadrum)state.getItem()).getObject();
        if (data.variants.length > 0) {
            String value = data.variants[state.getItemDamage()];
            T variant = getVariants().get(value);

            if (variant != null) {
                return variant;
            } else {
                return getDefaultValue();
            }
        } else {
            return getDefaultValue();
        }
    }
}
