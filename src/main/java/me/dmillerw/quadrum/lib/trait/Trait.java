package me.dmillerw.quadrum.lib.trait;

import com.google.common.collect.Lists;
import me.dmillerw.quadrum.block.BlockQuadrum;
import me.dmillerw.quadrum.block.data.BlockData;
import me.dmillerw.quadrum.item.ItemQuadrum;
import me.dmillerw.quadrum.item.data.ItemData;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author dmillerw
 */
public abstract class Trait<T> {

    public static final String LORE = "lore";
    public static final String ORE_DICTIONARY = "ore_dictionary";

    public static final String BLOCK_AABB = "aabb";
    public static final String BLOCK_PHYSICAL = "physical";

    protected abstract T getDefaultValue();
    protected abstract Map<String, T> getVariants();

    public final T getValueFromBlockState(IBlockState state) {
        BlockData data = ((BlockQuadrum) state.getBlock()).getObject();
        if (data.variants.length > 0) {
            String value = state.getValue(data.getVariantProperty());
            T variant = getVariants().get(value);

            if (variant != null) {
                return variant;
            } else {
                return getDefaultValue();
            }
        } else {
            return getDefaultValue();
        }
    }

    public final T getValueFromItemStack(ItemStack state) {
        if (state.getItem() instanceof ItemBlock) {
            BlockData data = ((BlockQuadrum)((ItemBlock) state.getItem()).block).getObject();
            if (data.variants.length > 0) {
                String value = data.variants[state.getItemDamage()];
                T variant = getVariants().get(value);

                if (variant != null) {
                    return variant;
                } else {
                    return getDefaultValue();
                }
            } else {
                return getDefaultValue();
            }
        } else {
            ItemData data = ((ItemQuadrum)state.getItem()).getObject();
            if (data.variants.length > 0) {
                String value = data.variants[state.getItemDamage()];
                T variant = getVariants().get(value);

                if (variant != null) {
                    return variant;
                } else {
                    return getDefaultValue();
                }
            } else {
                return getDefaultValue();
            }
        }
    }

    public final T getValueFromIndex(Block block, int index) {
        BlockData data = ((BlockQuadrum) block).getObject();
        if (data.variants.length > 0) {
            return getValue(data.variants[index]);
        } else {
            return getDefaultValue();
        }
    }

    public final T getValueFromIndex(Item item, int index) {
        ItemData data = ((ItemQuadrum) item).getObject();
        if (data.variants.length > 0) {
            return getValue(data.variants[index]);
        } else {
            return getDefaultValue();
        }
    }

    private final T getValue(String key) {
        T variant = getVariants().get(key);

        if (variant != null) {
            return variant;
        } else {
            return getDefaultValue();
        }
    }

    public final void merge() {
        for (String key : getVariants().keySet()) {
            if (getDefaultValue() instanceof Mergeable) {
                T merged = (T) ((Mergeable) getDefaultValue()).merge(getVariants().get(key));
                getVariants().put(key, merged);
            } else if (getDefaultValue() instanceof Collection) {
                List merged = Lists.newArrayList();
                merged.addAll((Collection) getDefaultValue());
                merged.addAll((Collection) getVariants().get(key));
                getVariants().put(key, (T) merged);
            }
        }
    }
}
