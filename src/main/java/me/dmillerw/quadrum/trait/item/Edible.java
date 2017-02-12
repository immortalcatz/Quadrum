package me.dmillerw.quadrum.trait.item;

import com.google.gson.annotations.SerializedName;

/**
 * @author dmillerw
 */
public class Edible {

    public int health;
    public float saturation;
    public int duration;
    @SerializedName("can_always_eat")
    public boolean canAlwaysEat;
}
