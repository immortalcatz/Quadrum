package me.dmillerw.quadrum.feature.trait.impl.block;

import me.dmillerw.quadrum.feature.trait.util.Trait;
import net.minecraft.item.ItemStack;

/**
 * @author dmillerw
 */
public class Drop extends Trait<Drop> {

    public ItemStack item = null;
    public Number count = -1;
}
