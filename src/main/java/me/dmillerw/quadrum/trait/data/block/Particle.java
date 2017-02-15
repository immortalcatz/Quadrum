package me.dmillerw.quadrum.trait.data.block;

import me.dmillerw.quadrum.trait.Mergeable;
import me.dmillerw.quadrum.trait.data.util.RandomNumber;
import me.dmillerw.quadrum.trait.data.util.RandomXYZ;
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
