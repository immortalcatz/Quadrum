package me.dmillerw.quadrum.feature.trait.impl.block;

import me.dmillerw.quadrum.feature.trait.impl.util.Vector;
import me.dmillerw.quadrum.feature.trait.util.Trait;
import me.dmillerw.quadrum.helper.LogHelper;
import net.minecraft.util.EnumParticleTypes;

/**
 * @author dmillerw
 */
public class Particle extends Trait<Particle> {

    public EnumParticleTypes type;
    public Vector position = new Vector();
    public Vector speed = new Vector();

    @Override
    public boolean isValid() {
        if (type == null) {
            LogHelper.warn("Failed to parse Particle trait: Particle type either invalid or empty");
            return false;
        }

        return true;
    }
}
