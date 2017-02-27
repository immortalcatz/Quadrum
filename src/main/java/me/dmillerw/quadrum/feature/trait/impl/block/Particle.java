package me.dmillerw.quadrum.feature.trait.impl.block;

import me.dmillerw.quadrum.feature.trait.util.Trait;
import me.dmillerw.quadrum.feature.trait.impl.util.RandomXYZ;
import net.minecraft.util.EnumParticleTypes;

/**
 * @author dmillerw
 */
public class Particle extends Trait<Particle> {

    public EnumParticleTypes type;
    public RandomXYZ position = new RandomXYZ();
    public RandomXYZ speed = new RandomXYZ();
}
