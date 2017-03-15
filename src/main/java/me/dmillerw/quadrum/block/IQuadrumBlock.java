package me.dmillerw.quadrum.block;

import me.dmillerw.quadrum.feature.data.BlockData;
import me.dmillerw.quadrum.feature.data.IQuadrumObject;

import java.util.Random;

/**
 * @author dmillerw
 */
public interface IQuadrumBlock extends IQuadrumObject<BlockData> {

    // Because I'm horrible
    public Random getForgeRandom();
}
