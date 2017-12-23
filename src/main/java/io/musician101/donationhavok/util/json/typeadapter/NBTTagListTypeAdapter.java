package io.musician101.donationhavok.util.json.typeadapter;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.stream.StreamSupport;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants.NBT;

import static io.musician101.donationhavok.util.json.JsonUtils.GSON;

public class NBTTagListTypeAdapter implements JsonDeserializer<NBTTagList>, JsonSerializer<NBTTagList> {

    @Override
    public NBTTagList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonArray();
        if (jsonArray.size() == 0) {
            return new NBTTagList();
        }

        JsonElement jsonElement = jsonArray.get(0);
        NBTTagList nbtTagList = new NBTTagList();
        if (jsonElement.isJsonArray()) {
            jsonArray.forEach(array -> nbtTagList.appendTag(deserialize(array, typeOfT, context)));
        }
        else if (jsonElement.isJsonObject()) {
            jsonArray.forEach(object -> nbtTagList.appendTag(GSON.fromJson(object, NBTTagCompound.class)));
        }
        else if (jsonElement.isJsonPrimitive()) {
            JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
            if (jsonPrimitive.isBoolean() || jsonPrimitive.isNumber()) {
                StreamSupport.stream(jsonArray.spliterator(), false).forEach(jp -> nbtTagList.appendTag(GSON.fromJson(jp, NBTPrimitive.class)));
            }
            else {
                jsonArray.forEach(s -> nbtTagList.appendTag(new NBTTagString(s.getAsString())));
            }
        }

        return nbtTagList;
    }

    @Override
    public JsonElement serialize(NBTTagList src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray jsonArray = new JsonArray();
        if (src.getId() == NBT.TAG_LIST) {
            src.forEach(tag -> {
                switch (tag.getId()) {
                    case NBT.TAG_BYTE:
                        jsonArray.add(((NBTPrimitive) tag).getByte());
                        break;
                    case NBT.TAG_BYTE_ARRAY:
                        jsonArray.add(GSON.toJsonTree(tag, NBTTagByteArray.class));
                        break;
                    case NBT.TAG_COMPOUND:
                        jsonArray.add(GSON.toJsonTree(tag, NBTTagCompound.class));
                        break;
                    case NBT.TAG_DOUBLE:
                        jsonArray.add(((NBTPrimitive) tag).getDouble());
                        break;
                    case NBT.TAG_FLOAT:
                        jsonArray.add(((NBTPrimitive) tag).getFloat());
                        break;
                    case NBT.TAG_INT:
                        jsonArray.add(((NBTPrimitive) tag).getInt());
                        break;
                    case NBT.TAG_INT_ARRAY:
                        jsonArray.add(GSON.toJsonTree(tag, NBTTagIntArray.class));
                        break;
                    case NBT.TAG_LIST:
                        jsonArray.add(serialize((NBTTagList) tag, typeOfSrc, context));
                        break;
                    case NBT.TAG_LONG:
                        jsonArray.add(((NBTPrimitive) tag).getLong());
                        break;
                    case NBT.TAG_SHORT:
                        jsonArray.add(((NBTPrimitive) tag).getShort());
                        break;
                    case NBT.TAG_STRING:
                        jsonArray.add(((NBTTagString) tag).getString());
                        break;
                }
            });
        }

        return jsonArray;
    }
}
