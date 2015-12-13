package ru.ayeaye.game.gson;

import java.lang.reflect.Type;

import org.newdawn.slick.Image;

import ru.ayeaye.game.display.ImageConstants;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class ImageDeserializer implements JsonDeserializer<Image> {

	@Override
	public Image deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		if (json.getAsString().endsWith("demon.png"))
			return ImageConstants.getInstance().demon;
		else
			return null;
	}

}
