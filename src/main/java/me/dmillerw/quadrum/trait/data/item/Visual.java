package me.dmillerw.quadrum.trait.data.item;

import me.dmillerw.quadrum.trait.Mergeable;

/**
 * @author dmillerw
 */
public class Visual extends Mergeable<Visual> {

    public boolean enchanted = false;

    @Override
    public Visual merge(Visual other) {
        Visual visual = new Visual();

        visual.enchanted = merge(other, "enchanted", this.enchanted, other.enchanted);

        return visual;
    }
}
