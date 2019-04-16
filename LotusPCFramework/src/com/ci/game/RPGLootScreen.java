package com.ci.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;

import com.ci.game.GameScreen.GameState;
import com.ci.game.entity.Player;
import com.ci.game.graphics.Animation;
import com.ci.game.graphics.Assets;
import com.ci.game.graphics.Camera;
import com.ci.game.level.Level;
import com.ci.game.level.tile.Tile;
import com.ci.game.loot.lootsystem.LootSystem;
import com.ci.lotusFramework.Screen;
import com.ci.lotusFramework.Input.InputEvent;
import com.ci.lotusFramework.implementation.LotusRenderView;
import com.ci.lotusFramework.implementation.input.Keyboard;
import com.ci.lotusFramework.implementation.input.Mouse;

import game.ai.BanditAI;

public class RPGLootScreen extends Screen
{
	enum GameState
	{
		Ready, Running, Paused, GameOver, KeepMenu, GameMapMenu, BuildingPrompt, NonPlayerTurn
	}

	public static Tile[][] tileMap;
	
	GameState state = GameState.Running;//Ready
	
	// Variable Setup
	int xScrll, yScrll;
	private Level level;
	
	private Animation anim, hanim, sorcAnimDown,sorcAnimUp,sorcAnimLeft, sorcAnimRight;
	private Camera screen;

	private BanditAI banditFaction = new BanditAI();
	private Player player;

	private Keyboard key;

	private Mouse mouse;
	Random r;	

	public RPGLootScreen(LotusRenderView game) 
	{
		super(game);
		level = new Level(game);
		this.player = new Player(0, 0);
		this.player.init(level);
		level.setPlayer(this.player);
		level.getPlayer().setTribe("Regni");// init new game player tribe	
		screen = new Camera(game.getFrame().getWidth(), game.getFrame().getHeight());
		screen.init(game);
		screen.setOffset(0, 0);
		// if new game
		this.player.setCurTurn(1);
		this.player.setPrevTurn(1);
		this.player.setTotalTurns(1);
		
		
	}

	@Override
	public void update(double deltaTime) 
	{		
		List<InputEvent> inputEvents = game.getInput().getInputEvents();
		
		//System.out.println("Canvas " + game.getCanvas().getWidth() + " " + game.getCanvas().getHeight());
		//System.out.println("Frame " + game.getFrame().getWidth() + " " + game.getFrame().getHeight());
		
		// Depending on the state of the game, we call different update methods.
		if (state == GameState.Ready)
			updateReady(inputEvents);
		if (state == GameState.Running)
			updateRunning(inputEvents);


		if(game.getKeyboard().checkForESC())
		{
			pause();
			//game.getFrame().cleanAndQuit();
		}
	}

	@Override
	public void paint(double deltaTime) 
	{
		Graphics g = game.getCanvas().getBufferStrategy().getDrawGraphics();
		
		g.setColor(Color.WHITE);
		g.drawImage(Assets.lootChest, 40 + (game.getCanvas().getWidth() / 2), 30 + (game.getCanvas().getHeight() / 2), null);
		xScrll = screen.getxOffset();
		yScrll = screen.getyOffset();
		
		//screen.setGraphics(g);
		//g.clearScreen(0);
	
		level.render(xScrll, yScrll, screen, g);
		
		// g.drawImage(Assets.combatModeGrid, 0, 0, null);
		
		/*if( game.getMouse().isMouseClicked())
		{
			g.drawString("mouseX: " + this.game.getMouse().getX() + " MouseY: " + this.game.getMouse().getY(), 2 * 32, 10 * 32);
		}*/
		//g.drawString("fps: " + this.frames, 48 * 32, 28 * 32);
		
	
	}
	
	private void updateReady(List<InputEvent> inputEvents)
	{
		// This example starts with a "Ready" screen.
		// When the user touches the screen, the game begins.
		// state now becomes GameState.Running.
		// Now the updateRunning() method will be called!

		if (inputEvents.size() > 0)
			state = GameState.Running;
	}
	
	private void updateRunning(List<InputEvent> inputEvents)
	{

		int len = inputEvents.size();
		for (int i = 0; i < len; i++)
		{
			InputEvent input = inputEvents.get(i);
			if(input.type == InputEvent.TOUCH_UP)
			{
			}
			if(input.checkForESC())
			{
				
			}

		}
		level.update();


		game.getMouse().update();
		game.getKeyboard().update();
	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void backButton() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update12EverySec() 
	{
		// TODO Auto-generated method stub
		
	}

}
