package me.dmillerw.quadrum.feature.trait.impl.item;

import com.google.gson.annotations.SerializedName;
import me.dmillerw.quadrum.feature.trait.util.Trait;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

/**
 * @author dmillerw
 */
public class Consumable extends Trait<Consumable> {

    public EnumAction type = EnumAction.EAT;

    public FoodStats stats;

    public int duration = 32;

    @SerializedName("resulting_item")
    public ItemStack resultingItem = ItemStack.EMPTY;

    public static class FoodStats extends Trait<FoodStats> {

        public int health;
        public float saturation;
        @SerializedName("can_always_eat")
        public boolean canAlwaysEat;
    }
}
