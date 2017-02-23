package me.dmillerw.quadrum.feature.trait.impl.block;

import me.dmillerw.quadrum.feature.trait.util.Mergeable;
import me.dmillerw.quadrum.feature.trait.impl.util.RandomXYZ;
import net.minecraft.util.EnumParticleTypes;

/**
 * @author dmillerw
 */
public class Particle extends Mergeable<Particle> {

    public EnumParticleTypes type;
    public RandomXYZ position = new RandomXYZ();
    public RandomXYZ speed = new RandomXYZ();

    @Override
    public Particle merge(Particle other) {
        Particle particle = new Particle();

        particle.type = merge(other, "type", this.type, other.type);
        particle.position = merge(other, "position", this.position, other.position);
        particle.speed = merge(other, "speed", this.speed, other.speed);

        return particle;
    }
}
