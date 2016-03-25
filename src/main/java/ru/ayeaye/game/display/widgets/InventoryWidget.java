package ru.ayeaye.game.display.widgets;

import java.util.List;

import org.newdawn.slick.Graphics;

import ru.ayeaye.game.display.ImageConstants;
import ru.ayeaye.game.model.Attribute;
import ru.ayeaye.game.model.GameObject;

public class InventoryWidget extends Widget {

	public GameObject owner;
	
	public InventoryWidget(String name) {
		super(name);
	}
	public InventoryWidget(String name, int width, int height) {
		super(name, width, height);
	}
	
	public InventoryWidget(String name, float widthProportion, float heightProportion) {
		super(name, widthProportion, heightProportion);
	}
	
	@Override
	public void customRender(Graphics graphcis, final int absoluteX, final int absoluteY, final int minWidth, final int minHeight) {
		if (owner != null) {
			final int letterHeight = ImageConstants.fontHeightInPixels;
			final int letterWidth = ImageConstants.fontWidthInPixels;
			
			List<GameObject> inventory = (List<GameObject>) owner.getAttributes().get(Attribute.INVENTORY_GAME_OBJECT_LIST);
			if (inventory != null && !inventory.isEmpty()) {
				int offset = 0;
				for (GameObject item: inventory) {
					graphcis.drawImage(item.getImage(), absoluteX, absoluteY + offset);
					graphcis.drawString((String) item.getAttributes().get(Attribute.DESCRIPTION_STRING), absoluteX + item.getImage().getWidth(), absoluteY + item.getImage().getHeight()/2 - letterHeight/2 + offset);
					offset += item.getImage().getHeight();
				}
			} else {
				String msg = "Empty";
				graphcis.drawString(msg, absoluteX + minWidth/2 - letterWidth * msg.length() / 2, absoluteY + minHeight/2 - letterHeight/2);
			}
		}
	}
	
	public void setOwner(GameObject owner) {
		this.owner = owner;
	}
	
}
