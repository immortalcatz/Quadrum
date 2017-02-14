package me.dmillerw.quadrum.lib.gson.deserializer;

import com.google.gson.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Type;

/**
 * @author dmillerw
 */
public class ItemStackDeserializer implements JsonDeserializer<ItemStack> {

    @Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive()) {
            return new ItemStack(getItem(json.getAsString()));
        } else if (json.isJsonArray()) {
            JsonArray array = json.getAsJsonArray();
            if (array.size() <= 0) {
                return null;
            }

            String item = array.get(0).getAsString();
            int metadata = array.size() >= 2 ? array.get(1).getAsInt() : 0;
            int stackSize = array.size() >= 3 ? array.get(2).getAsInt() : 1;

            return new ItemStack(getItem(item), stackSize, metadata);
        } else if (json.isJsonObject()) {
            JsonObject object = json.getAsJsonObject();

            String item = object.get("item").getAsString();
            int metadata = object.has("metadata") ? object.get("metadata").getAsInt() : 0;
            int stackSize = object.has("stack_size") ? object.get("stack_size").getAsInt() : 0;

            return new ItemStack(getItem(item), stackSize, metadata);
        }

        return null;
    }

    private Item getItem(String item) {
        Item i = Item.getByNameOrId(item);
        if (i == null) {
            Block b = Block.getBlockFromName(item);
            if (b == null) {
                return Item.getItemFromBlock(Blocks.AIR);
            } else {
                return Item.getItemFromBlock(b);
            }
        } else {
            return i;
        }
    }
}
