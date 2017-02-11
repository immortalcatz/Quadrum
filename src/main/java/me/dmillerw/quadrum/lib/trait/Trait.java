package me.dmillerw.quadrum.lib.trait;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.annotations.SerializedName;
import me.dmillerw.quadrum.block.BlockQuadrum;
import me.dmillerw.quadrum.block.data.BlockData;
import me.dmillerw.quadrum.block.data.trait.Physical;
import me.dmillerw.quadrum.item.ItemQuadrum;
import me.dmillerw.quadrum.item.data.ItemData;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author dmillerw
 */
public class Trait {

    public static final Map<String, TypeToken> TRAIT_TYPES = Maps.newHashMap();
    static {
        // Common
        TRAIT_TYPES.put(Trait.LORE, new TypeToken<List<String>>() {});
        TRAIT_TYPES.put(Trait.ORE_DICTIONARY, new TypeToken<List<String>>() {});

        // Block
        TRAIT_TYPES.put(Trait.BLOCK_AABB, new TypeToken<AxisAlignedBB>() {});
        TRAIT_TYPES.put(Trait.BLOCK_PHYSICAL, new TypeToken<Physical>() {});

        // Item
    }

    public static final Map<String, DefaultValue> DEFAULT_VALUES = Maps.newHashMap();
    static {
        // Common
        DEFAULT_VALUES.put(Trait.LORE, () -> new ArrayList<String>());
        DEFAULT_VALUES.put(Trait.ORE_DICTIONARY, () -> new ArrayList<String>());

        // Block
        DEFAULT_VALUES.put(Trait.BLOCK_AABB, () -> new AxisAlignedBB(0, 0, 0, 1, 1, 1));
        DEFAULT_VALUES.put(Trait.BLOCK_PHYSICAL, () -> new Physical());

        // Item
    }

    public static final String LORE = "lore";
    public static final String ORE_DICTIONARY = "ore_dictionary";

    public static final String BLOCK_AABB = "aabb";
    public static final String BLOCK_PHYSICAL = "physical";

    @SerializedName("default")
    protected Object defaultValue;
    protected Map<String, Object> variants = Maps.newHashMap();

    public final <T> T getValueFromBlockState(IBlockState state) {
        BlockData data = ((BlockQuadrum) state.getBlock()).getObject();
        if (data.variants.length > 0) {
            String value = state.getValue(data.getVariantProperty());
            T variant = (T) variants.get(value);

            if (variant != null) {
                return variant;
            } else {
                return (T) defaultValue;
            }
        } else {
            return (T) defaultValue;
        }
    }

    public final <T> T getValueFromItemStack(ItemStack state) {
        if (state.getItem() instanceof ItemBlock) {
            BlockData data = ((BlockQuadrum)((ItemBlock) state.getItem()).block).getObject();
            if (data.variants.length > 0) {
                String value = data.variants[state.getItemDamage()];
                T variant = (T) variants.get(value);

                if (variant != null) {
                    return variant;
                } else {
                    return (T) defaultValue;
                }
            } else {
                return (T) defaultValue;
            }
        } else {
            ItemData data = ((ItemQuadrum)state.getItem()).getObject();
            if (data.variants.length > 0) {
                String value = data.variants[state.getItemDamage()];
                T variant = (T) variants.get(value);

                if (variant != null) {
                    return variant;
                } else {
                    return (T) defaultValue;
                }
            } else {
                return (T) defaultValue;
            }
        }
    }

    public final <T> T getValueFromIndex(Block block, int index) {
        BlockData data = ((BlockQuadrum) block).getObject();
        if (data.variants.length > 0) {
            return getValue(data.variants[index]);
        } else {
            return (T) defaultValue;
        }
    }

    public final <T> T getValueFromIndex(Item item, int index) {
        ItemData data = ((ItemQuadrum) item).getObject();
        if (data.variants.length > 0) {
            return getValue(data.variants[index]);
        } else {
            return (T) defaultValue;
        }
    }

    private final <T> T getValue(String key) {
        T variant = (T) variants.get(key);

        if (variant != null) {
            return variant;
        } else {
            return (T) defaultValue;
        }
    }

    public final <T> void merge() {
        for (String key : variants.keySet()) {
            if (defaultValue instanceof Mergeable) {
                T merged = (T) ((Mergeable) defaultValue).merge(variants.get(key));
                variants.put(key, merged);
            } else if (defaultValue instanceof Collection) {
                List merged = Lists.newArrayList();
                merged.addAll((Collection) defaultValue);
                merged.addAll((Collection) variants.get(key));
                variants.put(key, (T) merged);
            }
        }
    }
}
