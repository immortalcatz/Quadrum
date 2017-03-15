package me.dmillerw.quadrum.feature.trait;

import com.google.common.reflect.TypeToken;
import me.dmillerw.quadrum.feature.DataType;
import me.dmillerw.quadrum.feature.trait.impl.block.*;
import me.dmillerw.quadrum.feature.property.data.Consumable;
import me.dmillerw.quadrum.feature.trait.impl.item.ItemVisual;

import java.util.List;

/**
 * @author dmillerw
 */
public enum Traits {

    COMMON_LORE(DataType.COMMON, "lore", new TypeToken<List<String>>() {}),
    COMMON_ORE_DICTIONARY(DataType.COMMON, "ore_dictionary", new TypeToken<List<String>>() {}),

    BLOCK_BOUNDING_BOX(DataType.BLOCK, "bounding_box", new TypeToken<BoundingBox>() {}),
    BLOCK_PHYSICAL(DataType.BLOCK, "physical", new TypeToken<Physical>() {}),
    BLOCK_PARTICLE(DataType.BLOCK, "particle", new TypeToken<Particle[]>() {}),
    BLOCK_REDSTONE(DataType.BLOCK, "redstone", new TypeToken<Redstone>() {}),
    BLOCK_VISUAL(DataType.BLOCK, "visual", new TypeToken<BlockVisual>() {}),
    BLOCK_DROP(DataType.BLOCK, "drop", new TypeToken<Drop[]>() {}),

    ITEM_VISUAL(DataType.ITEM, "visual", new TypeToken<ItemVisual>() {});

    private DataType type;
    private String key;
    public TypeToken<?> typeToken;

    private Traits(DataType type, String key, TypeToken<?> typeToken) {
        this.type = type;
        this.key = key;
        this.typeToken = typeToken;
    }

    public static Traits get(DataType type, String key) {
        for (Traits trait : Traits.values()) {
            if (trait.type == type && trait.key.equalsIgnoreCase(key))
                return trait;
        }
        return null;
    }
}
