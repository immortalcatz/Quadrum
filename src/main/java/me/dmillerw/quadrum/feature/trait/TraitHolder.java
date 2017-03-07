package me.dmillerw.quadrum.feature.trait;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.dmillerw.quadrum.block.BlockQuadrum;
import me.dmillerw.quadrum.feature.data.BlockData;
import me.dmillerw.quadrum.feature.data.ItemData;
import me.dmillerw.quadrum.feature.trait.util.Trait;
import me.dmillerw.quadrum.item.IQuadrumItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author dmillerw
 */
public class TraitHolder<T> {

    public Class type;

    public T defaultValue;
    public Map<String, T> variants = Maps.newHashMap();

    public final T getValueFromBlockState(IBlockState state) {
        BlockData data = ((BlockQuadrum) state.getBlock()).getObject();
        if (data.variants.length > 0) {
            String value = state.getValue(data.getVariantProperty());
            T variant = variants.get(value);

            if (variant != null) {
                return variant;
            } else {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public final T getValueFromItemStack(ItemStack state) {
        if (state.isEmpty())
            return defaultValue;

        String[] variants = new String[0];
        if (state.getItem() instanceof ItemBlock) {
            BlockData data = ((BlockQuadrum)((ItemBlock) state.getItem()).block).getObject();
            variants = data.variants;
        } else {
            ItemData data = ((IQuadrumItem)state.getItem()).getObject();
            variants = data.variants;
        }

        if (variants.length > 0) {
            String value = variants[state.getItemDamage()];
            T variant = this.variants.get(value);

            if (variant != null) {
                return variant;
            } else {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public final void merge() {
        if (variants == null || variants.isEmpty())
            return;

        for (String key : variants.keySet()) {
            if (Trait.class.isAssignableFrom(type)) {
                T merged = (T) Trait.merge(type, (Trait)defaultValue, (Trait)variants.get(key));
                variants.put(key, merged);
            } else if (Collection.class.isAssignableFrom(type)) {
                List merged = Lists.newArrayList();
                merged.addAll((Collection) defaultValue);
                merged.addAll((Collection) variants.get(key));
                variants.put(key, (T) merged);
            } else if (type.isArray()) {
                List<T> merged = Lists.newArrayList();
                for (Object v : (Object[]) defaultValue) merged.add((T) v);
                for (Object v : (Object[]) variants.get(key)) merged.add((T) v);

                T[] array = (T[]) Array.newInstance(type.getComponentType(), merged.size());
                for (int i=0; i<merged.size(); i++) array[i] = merged.get(i);

                variants.put(key, (T) array);
            }
        }
    }
}
