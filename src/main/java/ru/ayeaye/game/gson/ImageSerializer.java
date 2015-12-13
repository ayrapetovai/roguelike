package ru.ayeaye.game.gson;

import java.lang.reflect.Type;

import org.newdawn.slick.Image;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ImageSerializer implements JsonSerializer<Image> {

	@Override
	public JsonElement serialize(Image src, Type typeOfSrc, JsonSerializationContext context) {
		JsonElement je = new JsonPrimitive(src.getResourceReference());
		return je;
	}
}
