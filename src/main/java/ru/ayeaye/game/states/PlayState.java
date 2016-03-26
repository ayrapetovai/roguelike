package ru.ayeaye.game.states;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.ayeaye.game.display.ImageConstants;
import ru.ayeaye.game.display.controller.TerrainController;
import ru.ayeaye.game.display.layouts.AbstractLayout;
import ru.ayeaye.game.display.layouts.HorizontalLayout;
import ru.ayeaye.game.display.layouts.ParentRelativeLayout;
import ru.ayeaye.game.display.layouts.StackLayout;
import ru.ayeaye.game.display.layouts.VerticalLayout;
import ru.ayeaye.game.display.widgets.ButtonWidget;
import ru.ayeaye.game.display.widgets.ContextWidget;
import ru.ayeaye.game.display.widgets.GameLogWidget;
import ru.ayeaye.game.display.widgets.InventoryWidget;
import ru.ayeaye.game.display.widgets.TerrainWidget;
import ru.ayeaye.game.display.widgets.Widget;
import ru.ayeaye.game.gson.ImageDeserializer;
import ru.ayeaye.game.gson.ImageSerializer;
import ru.ayeaye.game.input.GameInputListener;
import ru.ayeaye.game.logic.GameLogicEngine;
import ru.ayeaye.game.model.Attribute;
import ru.ayeaye.game.model.GameField;
import ru.ayeaye.game.model.GameLogSource;
import ru.ayeaye.game.model.GameModel;
import ru.ayeaye.game.model.GameObject;
import ru.ayeaye.game.model.Tag;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PlayState extends BasicGameState {

	public static Logger log = LoggerFactory.getLogger(PlayState.class);
	
	public static int ID = StateIdGenerator.getNextId();
	
	private Widget mainFrame;
	private Widget topWidget;
	private StackLayout stackLayout;
	private TextField tf;
	private GameModel gm;
	
	private float delay;
	
	public PlayState() {
		int height = 30;
		int width = 30;
		GameField field = new GameField(width, height);
		SpriteSheet grassSheet = ImageConstants.getInstance().grassSheet;
//		GameObject player = saveOrLoadPlayer();
		GameObject player = createPlayer();
		
		GameObject someMonster = new GameObject();
		someMonster.getTags().add(Tag.CREATURE);
		someMonster.getTags().add(Tag.DESTRACTABLE);
		someMonster.getAttributes().put(Attribute.HIT_POINTS_INT, 3);
		someMonster.getAttributes().put(Attribute.DESCRIPTION_STRING, "minor demon");
		someMonster.setImage(ImageConstants.getInstance().minorDemon);
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				GameObject gameObject = new GameObject();
				gameObject.getTags().add(Tag.STANDABLE);
				gameObject.setImage(grassSheet.getSprite((int)(Math.random() * 12), 0).getScaledCopy(ImageConstants.cellWidthInPixels, ImageConstants.cellHeightInPixels));
				field.getCellOrException(i, j).pushGameObject(gameObject );
			}
		}
		
		field.setPlayer(player);
		field.getCellOrException(2, 2).pushGameObject(player);
		
		field.getCellOrException(0, 0).pushGameObject(someMonster);
		
		GameLogSource logSource = new GameLogSource();

		gm = new GameModel();
		gm.setField(field);
		gm.setGameLogSource(logSource);
		
		topWidget = new Widget("menu", 100, 100);
		topWidget.setCentred(true);
		topWidget.setBackgroundColor(Color.yellow);
		topWidget.setVisible(false);
		
//		mainFrame = getSimple();
//		mainFrame = getLogAndTerrains();
//		mainFrame = getNineTerrains(true);
		mainFrame = getLogOnTerrain();
//		mainFrame = getLocalContextButtons();
	}
	
	private GameObject saveOrLoadPlayer() {
		Gson gson = new GsonBuilder().setPrettyPrinting()
				.registerTypeAdapter(Image.class, new ImageSerializer())
				.registerTypeAdapter(Image.class, new ImageDeserializer())
				.create();
		
		GameObject player = null;
		
		File save = new File("./save.json");
		if (!save.exists()) {
			log.debug("Saving player");
			player = createPlayer();
			String json = gson.toJson(player);
			try {
				Writer w = new FileWriter(save);
				w.write(json);
				w.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			log.debug("Loading player");
			try {
				Path path = Paths.get(save.getCanonicalPath());
				byte[] encoded = Files.readAllBytes(path);
				String json = new String(encoded, "UTF-8");
				player = gson.fromJson(json, GameObject.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return player;
	}
	
	private GameObject createPlayer() {
		GameObject player = new GameObject();
		player.setImage(ImageConstants.getInstance().demon);
		player.getTags().add(Tag.CAN_WALK);
		player.getTags().add(Tag.CREATURE);
		player.getTags().add(Tag.CAN_ATTACK);
		player.getTags().add(Tag.DESTRACTABLE);
		player.getAttributes().put(Attribute.HIT_POINTS_INT, 5);
		player.getAttributes().put(Attribute.ATTACK_POINTS_INT, 1);
		player.getAttributes().put(Attribute.MELE_ATTACK_SPEED_FLOAT, 1.5f);
		player.getAttributes().put(Attribute.CONJURE_SPEED_FLOAT, 2f);
		player.getAttributes().put(Attribute.MOVE_SPEED_FLOAT, 1f); 
		player.getAttributes().put(Attribute.DESCRIPTION_STRING, "demon");
		player.getAttributes().put(Attribute.INVENTORY_GAME_OBJECT_LIST, createInventoryItems());
		return player;
	}
	
	private List<GameObject> createInventoryItems() {
		List<GameObject> result = new ArrayList<>();
		GameObject axe = new GameObject();
		axe.setImage(ImageConstants.getInstance().exe);
		axe.getAttributes().put(Attribute.DESCRIPTION_STRING, "axe +5Str, -2Int, -2Stmn");
		result.add(axe);
		result.add(axe);
		result.add(axe);
		return result;
	}

	private Widget getLocalContextButtons() {
		Widget mainFrame = new Widget("Main Frame");
		mainFrame.setBackgroundColor(Color.blue);
		
		AbstractLayout mainLayout = new ParentRelativeLayout();
		
		ContextWidget clw = new ContextWidget("label", 70, 80);
		clw.setVisible(false);
		clw.setFolowMouse(false);
		
		AbstractLayout labelLayout = new VerticalLayout();

//		TerrainWidget terrain = new TerrainWidget("terrain");
//		terrain.setGameField(field);
//		terrain.setBackgroundColor(Color.gray);
//		labelLayout.addWidget(terrain);
		
		ButtonWidget bw1 = new ButtonWidget("buttonOK", 1f, 1f/3);
		bw1.setText("OK");
		bw1.setMouseOverColor(Color.red);
		bw1.setMouseOffColor(Color.green);
		ButtonWidget bw2 = new ButtonWidget("buttonCancel", 1f, 1f/3);
		bw2.setText("Cancel");
		bw2.setMouseOverColor(Color.red);
		bw2.setMouseOffColor(Color.green);
		ButtonWidget bw3 = new ButtonWidget("buttonHelp", 1f, 1f/3);
		bw3.setText("Help");
		bw3.setMouseOverColor(Color.red);
		bw3.setMouseOffColor(Color.green);
		
		labelLayout.addWidget(bw1);
		labelLayout.addWidget(bw2);
		labelLayout.addWidget(bw3);
		
		clw.setLayout(labelLayout);
//		
//		mainLayout.addWidget(clw);
//		mainFrame.setLayout(mainLayout);
		mainFrame.setContextWidget(clw);
		return mainFrame;
	}

	private Widget getSimple() {
		Widget mainFrame = new Widget("Main Frame");
//		AbstractLayout mainLayout = new HorizontalLayout();
		AbstractLayout mainLayout = new VerticalLayout();
		Widget first = new Widget("first", 0, 0);
		first.setWidthProportion(0.5f);
		first.setHeightProportion(0.5f);
		first.setBackgroundColor(Color.red);
		
		Widget second = new Widget("second", 0, 0);
		second.setWidthProportion(0.3f);
		second.setHeightProportion(0.5f);
		second.setBackgroundColor(Color.green);
		
		mainLayout.addWidget(first);
		mainLayout.addWidget(second);
		
		mainFrame.setLayout(mainLayout);

		return mainFrame;
	}

	private Widget getLogOnTerrain() {
		Widget mainFrame = new Widget("Main Frame");
		ParentRelativeLayout prl = new ParentRelativeLayout();
		
		InventoryWidget inventory = new InventoryWidget("inventory", 600, 400);
		inventory.setCentred(true);
		inventory.setVisible(false);
		inventory.setAlowedToDispatchMouse(false);
		inventory.setAlowedToDispatchKey(false);
		inventory.setBackgroundColor(Color.darkGray);
		inventory.setOwner(gm.getField().getPlayer());
		topWidget = inventory;
		
		TerrainWidget terrain = new TerrainWidget("terrain");
		terrain.setGameField(gm.getField());
		terrain.setBackgroundColor(Color.gray);
		terrain.setController(new TerrainController(gm.getField()));
		
		GameLogWidget gameLog = new GameLogWidget("log", 0, 100);
		gameLog.setLogSource(gm.getGameLogSource());
		gameLog.setAlowedToDispatchMouse(false);
		gameLog.setAlowedToDispatchKey(false);
		gameLog.setOffsetX(0);
		gameLog.setOffsetY(0);
		gameLog.setWidthProportion(0.6f);
//		gameLog.setBackgroundColor(Color.lightGray);
		
		prl.addWidget(terrain);
		prl.addWidget(gameLog);
		
		Widget logAndTerrain = new Widget("logAndTerrain");
		logAndTerrain.setLayout(prl);
		StackLayout stack = new StackLayout();
		
		stack.addWidget(logAndTerrain);
		
		GameLogWidget bigGameLog = new GameLogWidget("biglog");
		bigGameLog.setLogSource(gm.getGameLogSource());
		bigGameLog.setVisible(false);
		bigGameLog.setAlowedToDispatchMouse(false);
		bigGameLog.setBackgroundColor(Color.lightGray);
//		topWidget = bigGameLog;
		
		stack.addWidget(bigGameLog);
		stack.addWidget(inventory);
		
		mainFrame.setLayout(stack);
		
//		gm.getTriggers().put(ActionType.WALK, new Trigger() {
//
//			@Override
//			public void preApplay(Map<ActionParameter, Object> context) {}
//
//			@Override
//			public test.GenericAction postApplay(Map<ActionParameter, Object> context) {
//				GameObject goTarget = (GameObject) context.get(ActionParameter.SOURCE_GAME_OBJECT);
//				int i = goTarget.getLocationCell().getI();
//				int j = goTarget.getLocationCell().getJ();
//				
//				if (i + j == 3) {
//					log.debug("Win!");
//					goTarget.getTags().remove(Tag.CAN_WALK);
//				}
//				return null;
//			}
//			
//		});
		
		GameLogicEngine.getInstance().setModel(gm);
		
		return mainFrame;
	}

	private Widget getLogAndTerrains() {
		Widget mainFrame = new Widget("MainFrame");
		
		AbstractLayout mainLayout = new HorizontalLayout();
		
		GameLogWidget gameLog = new GameLogWidget("log", 200, 200);
		gameLog.setLogSource(gm.getGameLogSource());
		gameLog.setBackgroundColor(Color.gray);
		
		ContextWidget cw = new ContextWidget("miniCOntextWidget", 50, 50);
		cw.setBackgroundColor(Color.pink);
		cw.setVisible(false);
//		cw.setFolowMouse(false);
		
		TerrainWidget terrain1 = new TerrainWidget("terrain2x4", 200, 400);
		terrain1.setGameField(gm.getField());
		terrain1.setContextWidget(cw);
		terrain1.setAlowedToDispatchKey(false);
		
		TerrainWidget terrain2 = new TerrainWidget("terrain2x2", 200, 200);
		terrain2.setGameField(gm.getField());
		
		
		Widget vertWidget = new Widget("vertWidget", 200, 400);
		AbstractLayout vertLayout = new VerticalLayout();
		vertLayout.addWidget(gameLog);
		vertLayout.addWidget(terrain2);
		vertWidget.setLayout(vertLayout);
		mainLayout.addWidget(vertWidget);
		mainLayout.addWidget(terrain1);
		
		stackLayout = new StackLayout();
		Widget stackWidget = new Widget("stack", 640, 480);
		stackWidget.setLayout(mainLayout);
		stackLayout.addWidget(stackWidget);
		stackLayout.addWidget(topWidget);
		mainFrame.setLayout(stackLayout);
		return mainFrame;
	}
	
	private Widget getNineTerrains(boolean cubes) {
		Widget mainWidget = new Widget("mainFrame");
		
//		int aW = 640;
//		int aH = 480;
//		
//		int w = aW / 3;
//		int h = aH / 3;
//		
//		w = 0;
//		h = 0;
		float w = 0.33f;
		float h = 1f;

		Widget t1 = null;
		Widget t2 = null;
		Widget t3 = null;
		Widget t4 = null;
		Widget t5 = null;
		Widget t6 = null;
		Widget t7 = null;
		Widget t8 = null;
		Widget t9 = null;
		
		ContextWidget cw = new ContextWidget("miniCOntextWidget", 50, 50);
		cw.setBackgroundColor(Color.pink);
//		cw.setFolowMouse(false);
		cw.setVisible(false);
		
		if (!cubes) {
			t1 = new TerrainWidget("t1", w, h);
			t1.setAlowedToDispatchKey(false);
			((TerrainWidget)t1).setGameField(gm.getField());
			t2 = new TerrainWidget("t2", w, h);
			t2.setAlowedToDispatchKey(false);
			((TerrainWidget)t2).setGameField(gm.getField());
			t3 = new TerrainWidget("t3", w, h);
			t3.setAlowedToDispatchKey(false);
			((TerrainWidget)t3).setGameField(gm.getField());
			t4 = new TerrainWidget("t4", w, h);
			t4.setAlowedToDispatchKey(false);
			((TerrainWidget)t4).setGameField(gm.getField());
			t5 = new TerrainWidget("t5", w, h);
			t5.setAlowedToDispatchKey(false);
			((TerrainWidget)t5).setGameField(gm.getField());
			t6 = new TerrainWidget("t6", w, h);
			t6.setAlowedToDispatchKey(false);
			((TerrainWidget)t6).setGameField(gm.getField());
			t7 = new TerrainWidget("t7", w, h);
			t7.setAlowedToDispatchKey(false);
			((TerrainWidget)t7).setGameField(gm.getField());
			t8 = new TerrainWidget("t8", w, h);
			t8.setAlowedToDispatchKey(false);
			((TerrainWidget)t8).setGameField(gm.getField());
			t9 = new TerrainWidget("t9", w, h);
//			t9.setAlowedToDispatchMouse(false);
//			t9.setAlowedToDispatchKey(false);
			((TerrainWidget)t9).setGameField(gm.getField());
		} else {
			t1 = new Widget("t1", .1f, 0.01f);
			t1.setBackgroundColor(Color.red);
			t2 = new Widget("t2", w, h);
			t2.setBackgroundColor(Color.green);
			
			t3 = new Widget("t3", w, h);
				StackLayout sl = new StackLayout();
				sl.addWidget(topWidget);
			t3.setLayout(sl);
			t3.setBackgroundColor(Color.blue);
			t4 = new Widget("t4", w, h);
			t4.setBackgroundColor(Color.yellow);
			t5 = new Widget("t5", w, h);
			
			t5.setBackgroundColor(Color.orange);
			t6 = new Widget("t6", w, h);
			t6.setBackgroundColor(Color.cyan);
			t7 = new Widget("t7", w, h);
			t7.setBackgroundColor(Color.pink);
			t8 = new Widget("t8", w, h);
			t8.setBackgroundColor(Color.lightGray);
			t9 = new Widget("t9", w, h);
			t9.setBackgroundColor(Color.white);
		}
		
		t9.setContextWidget(cw);
		
//		1 2 3
//		4 5 6
//		7 8 9
		
		HorizontalLayout h1 = new HorizontalLayout();
		h1.addWidget(t1);
		h1.addWidget(t2);
		h1.addWidget(t3);
		
		HorizontalLayout h2 = new HorizontalLayout();
		h2.addWidget(t4);
		h2.addWidget(t5);
		h2.addWidget(t6);
		
		HorizontalLayout h3 = new HorizontalLayout();
		h3.addWidget(t7);
		h3.addWidget(t8);
		h3.addWidget(t9);
		
		Widget horWidget1 = new Widget("horWidget1", 1f, 0.333f);
		horWidget1.setLayout(h1);
		Widget horWidget2 = new Widget("horWidget2", 1f, 0.333f);
		horWidget2.setLayout(h2);
		Widget horWidget3 = new Widget("horWidget3", 1f, 0.333f);
		horWidget3.setLayout(h3);
		
		VerticalLayout vertical = new VerticalLayout();
		vertical.addWidget(horWidget1);
		vertical.addWidget(horWidget2);
		vertical.addWidget(horWidget3);
		
		mainWidget.setLayout(vertical);
		return mainWidget;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		mainFrame.setWidth(container.getWidth());
		mainFrame.setHeight(container.getHeight());
		tf = new TextField(container, container.getDefaultFont(), 100, 100, 50, 50);
		tf.setText("Hello!");
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		try {
			mainFrame.render(g, 0, 0, container.getWidth(), container.getHeight());
//			tf.render(container, g);
		} catch (Exception e) {
			log.error("Failed to render", e);
			throw e;
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if (container.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
//			game.enterState(PlayMenuState.ID);
			container.exit();
		}
		
		if (container.getInput().isKeyPressed(Input.KEY_ENTER)) {
			topWidget.setVisible(!topWidget.isVisible());
			topWidget.setAlowedToDispatchMouse(!topWidget.isAlowedToDispatchMouse());
			topWidget.setAlowedToDispatchKey(!topWidget.isAlowedToDispatchKey());
		}
		
		if (container.getInput().isKeyPressed(Input.KEY_SPACE)) {
			char[] chars = new char[(int)(Math.random() * 100)];
			for (int i = 0; i < chars.length; i++) {
				chars[i] = (char) ('a' + (int)(Math.random() * ('z' - 'a')));
			}
			String s = new String(chars);
			gm.getGameLogSource().addEntry(s);
//			logSource.addEntry(Integer.valueOf((int)(Math.random() * 100)).toString());
		}
		
		int keyCode = GameInputListener.getInstance().getKey();
		if (keyCode != 0) {
			int modifier = GameInputListener.getInstance().getModifier();
//			log.debug("Key pressed {}-{} ({})", Input.getKeyName(modifier), Input.getKeyName(keyCode), keyCode;
			mainFrame.dispatchKeyEvent(keyCode, modifier);
			GameInputListener.getInstance().clearKey();
		}
//		if (GameInputListener.getInstance().getMouseButton() != -1) {
		mainFrame.dispatchMouseEvent(
				GameInputListener.getInstance().getMouseButton(),
				GameInputListener.getInstance().getModifier(),
				GameInputListener.getInstance().getMouseX(), GameInputListener.getInstance().getMouseY(),
				0, 0,
				mainFrame.getWidth(), mainFrame.getHeight()
				);
		GameInputListener.getInstance().clearMouse();
//		}
		
		if (delay > 20 * delta) {
			delay = 0;
			GameLogicEngine.getInstance().go();
		}
		delay += delta;
	}
	
	@Override
	public int getID() {
		return ID;
	}

}
