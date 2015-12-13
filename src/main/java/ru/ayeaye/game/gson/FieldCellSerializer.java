package ru.ayeaye.game.gson;

import java.lang.reflect.Type;

import ru.ayeaye.game.model.FieldCell;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class FieldCellSerializer implements JsonSerializer<FieldCell> {

	@Override
	public JsonElement serialize(FieldCell src, Type typeOfSrc, JsonSerializationContext context) {
		JsonElement je = new JsonNull();
		if (src != null) {
			JsonObject jo = new JsonObject();
			jo.add("fields", new JsonPrimitive(src.getI() + ":" + src.getJ() + ":" + src.isSelected()));
			return je;
		}
		return je;
	}

}
