package me.dmillerw.quadrum.feature.trait;

import com.google.common.reflect.TypeToken;
import me.dmillerw.quadrum.feature.trait.impl.block.*;
import me.dmillerw.quadrum.feature.trait.impl.item.Consumable;
import me.dmillerw.quadrum.feature.trait.impl.item.ItemVisual;

import java.util.List;

/**
 * @author dmillerw
 */
public enum Traits {

    COMMON_LORE(Type.COMMON, "lore", new TypeToken<List<String>>() {}),
    COMMON_ORE_DICTIONARY(Type.COMMON, "ore_dictionary", new TypeToken<List<String>>() {}),

    BLOCK_BOUNDING_BOX(Type.BLOCK, "bounding_box", new TypeToken<BoundingBox>() {}),
    BLOCK_PHYSICAL(Type.BLOCK, "physical", new TypeToken<Physical>() {}),
    BLOCK_PARTICLE(Type.BLOCK, "particle", new TypeToken<Particle[]>() {}),
    BLOCK_REDSTONE(Type.BLOCK, "redstone", new TypeToken<Redstone>() {}),
    BLOCK_VISUAL(Type.BLOCK, "visual", new TypeToken<BlockVisual>() {}),
    BLOCK_DROP(Type.BLOCK, "drop", new TypeToken<Drop[]>() {}),

    ITEM_CONSUMABLE(Type.ITEM, "consumable", new TypeToken<Consumable>() {}),
    ITEM_VISUAL(Type.ITEM, "visual", new TypeToken<ItemVisual>() {});

    private Type type;
    private String key;
    public TypeToken<?> typeToken;

    private Traits(Type type, String key, TypeToken<?> typeToken) {
        this.type = type;
        this.key = key;
        this.typeToken = typeToken;
    }

    public static Traits get(Type type, String key) {
        for (Traits trait : Traits.values()) {
            if (trait.type == type && trait.key.equalsIgnoreCase(key))
                return trait;
        }
        return null;
    }

    public static enum Type {

        COMMON, BLOCK, ITEM;
    }
}
