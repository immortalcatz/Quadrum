package me.dmillerw.quadrum.feature.trait.data.item;

import com.google.gson.annotations.SerializedName;
import me.dmillerw.quadrum.feature.trait.util.Mergeable;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

/**
 * @author dmillerw
 */
public class Consumable extends Mergeable<Consumable> {

    public EnumAction type = EnumAction.EAT;

    public FoodStats stats;

    public int duration = 32;

    @SerializedName("resulting_item")
    public ItemStack resultingItem = ItemStack.EMPTY;

    @Override
    public Consumable merge(Consumable other) {
        Consumable consumable = new Consumable();

        consumable.type = merge(other, "type", this.type, other.type);
        consumable.duration = merge(other, "duration", this.duration, other.duration);
        consumable.resultingItem = merge(other, "resulting_item", this.resultingItem, other.resultingItem);

        if (this.stats == null) {
            consumable.stats = other.stats;
        } else {
            if (other.stats != null) {
                consumable.stats = this.stats.merge(other.stats);
            } else {
                consumable.stats = this.stats;
            }
        }

        return consumable;
    }

    public static class FoodStats extends Mergeable<FoodStats> {

        public int health;
        public float saturation;
        @SerializedName("can_always_eat")
        public boolean canAlwaysEat;

        @Override
        public FoodStats merge(FoodStats other) {
            FoodStats stats = new FoodStats();

            stats.health = merge(other, "health", this.health, other.health);
            stats.saturation = merge(other, "saturation", this.saturation, other.saturation);
            stats.canAlwaysEat = merge(other, "can_always_eat", this.canAlwaysEat, other.canAlwaysEat);

            return stats;
        }
    }
}
