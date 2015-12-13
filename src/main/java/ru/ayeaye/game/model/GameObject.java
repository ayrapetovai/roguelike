package ru.ayeaye.game.model;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.newdawn.slick.Image;

public class GameObject {

	/** A cell where this object is located */
	private FieldCell locationCell;

	/** 2d bitmap image - representation of the game object that should be rendered on screen */
	private Image image;
	private String description;
	
	private final Set<Tag> tags = new TreeSet<>();
	private final Map<Attribute, Object> attributes = new TreeMap<>();
	
	public FieldCell getLocationCell() {
		return locationCell;
	}

	protected void setLocationCell(FieldCell locationCell) {
		this.locationCell = locationCell;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public Map<Attribute, Object> getAttributes() {
		return attributes;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
