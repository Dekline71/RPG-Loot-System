package com.ci.game.level;



import game.ai.CombatEvent;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.PixelGrabber;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

import com.ci.game.entity.Enemy;
import com.ci.game.entity.Entity;
import com.ci.game.entity.Entity.Direction;
import com.ci.game.entity.Resource;
import com.ci.game.entity.Enemy.UnitState;
import com.ci.game.entity.Entity.UnitType;
import com.ci.game.entity.Player;
import com.ci.game.entity.armyunit.ArmyUnit;
import com.ci.game.entity.buildings.Barrack;
import com.ci.game.entity.buildings.BuildingEntity;
import com.ci.game.entity.buildings.City;
import com.ci.game.entity.mob.NPC;
import com.ci.game.entity.particle.Particle;
import com.ci.game.entity.projectile.Projectile;
import com.ci.game.graphics.Assets;
import com.ci.game.graphics.Camera;
import com.ci.game.graphics.Sprite;
import com.ci.game.level.tile.Tile;
import com.ci.game.loot.Item;
import com.ci.game.loot.Item.ItemType;
import com.ci.game.loot.lootsystem.LootSystem;
import com.ci.lotusFramework.implementation.LotusRenderView;

/**************************************************************************
 * @author C Galletta
 *  This class contains all level entities from particles to city entities
 *  organized in linked lists.
 **************************************************************************/

public class Level 
{	
	protected int width, height;// hold width & height of level
	protected static Tile[] tilesInt;// tiles IDs/Index
	protected int[] tiles;//contain color values of pixel in level
	//public static Level spawn = new SpawnLevel("/levels/spawn.png");	
	
	
	//Data structures for entities
	private LinkedList<ArmyUnit> playerArmyEntities = new LinkedList<ArmyUnit>();
	private LinkedList<Enemy> enemyArmyEntities = new LinkedList<Enemy>();
	private LinkedList<City> playerCityEntities = new LinkedList<City>();
	private LinkedList<Resource> droppedResourceEntities = new LinkedList<Resource>();
	private LinkedList<Particle> particleEntities = new LinkedList<Particle>();
	private LinkedList<Projectile> projectileEntities = new LinkedList<Projectile>();
	
	private Player player;
	String mapPath;
	
	private Queue<CombatEvent> combatEvents = new LinkedBlockingQueue<CombatEvent>();
	
	// old structs that are unused (to be removed)
	private List<Entity> entities = new ArrayList<Entity>();
	public List<Projectile> projectiles = new ArrayList<Projectile>();
	public List<Particle> particles = new ArrayList<Particle>();
	public List<NPC> npcs = new ArrayList<NPC>();
	
	private TileMap buildingTileMap; // grid struct for building entities
	private TileMap armyMovementTileMap; // grid struct for npc/soldier entities
	private int aMMW; // Army Movement Map Width
	private int aMMH; // Army Movement Map Height
	
	private int tileMapXSize, tileMapYSize;
	private LotusRenderView game;
	
	//private BuildingEntity selectedBuilding;
	private int barrackX;
	private int barrackY;
	private ArmyUnit selectedPlayerEntity = new ArmyUnit();
	private Entity selectedEntity = new Entity();
	private boolean isEntitySelected = false;
	private boolean isPlayerEntitySelected = false;
	private boolean isTouchingDown = false;
	private boolean isEnemyEntitySelected = false;
	private boolean uiPaneDrawTrig;
	private boolean isUnitUIItem_1Selected;
	private boolean isUIItem_1Selected;
	private boolean isProcessingNextTurn = false;
	private boolean selectedDir;
	private City selectedCity;
	
	
	
	
	private int [][] contestedMapTerritoryDataLayer = new int [95][85];
	
	public static int brownUIx = 50;
	public static int brownUIy = 8;

	public static int UIxItem_1 = 48;
	public static int UIyItem_1 = 128;
	public static int UIyItem_2 = 128;
	public static int UIxItem_2 = 16;

	
	public Level(int width, int height)
	{
		this.width = width;
		this.height = height;
		tilesInt = new Tile[this.width * this.height];//size of map int values for tile indexes on map
		generateLevel();		
		//System.out.println("Max Tiles: " + tiles.length);
	}
	
	public Level(LotusRenderView g)
	{
		this.setGame(g);
		
		this.height = 5440; 
				//Assets.map.getHeight();
		this.width = 6100;//Assets.map.getWidth();
		System.out.println("Width: " + this.width + " Height: " + this.height);
		tiles = new int [this.width * this.height];

		// Building tile map thats 32x32
		tilesInt = new Tile [this.width * this.height]; // Init 
		//Assets.lvlmap.getRGB(0, 0, this.width, this.height, null, 0, 60);// convert pixel data into tiles[]
		
		this.buildingTileMap = new TileMap(this.width, this.height);// create new tilemap for 32x32
				

		
		// 64x64 px army movement tilemap init
		this.aMMW = this.getWidth() / 64; // Army Movement Map Width	
		this.aMMH = this.getHeight() / 64;
		this.armyMovementTileMap = new TileMap(aMMW, aMMH);	
		
		for(int yy = 0; yy < this.aMMH ; yy++)
		{
			for(int xx = 0; xx < this.aMMW; xx++ )
			{
				//tilesInt[xx + (yy * this.getWidth())] = getTile(xx, yy);
				
				this.armyMovementTileMap.getTileGrid()[xx][yy] = new Tile(xx, yy);
				//this.getTileMap().setTileGrid(this.getTileMap().getTileGrid());
			}
		}



		generateLevel();
	}

	protected void generateLevel() 
	{
 
	}
	
	
	/***********************************************************************
	 * Randomly spawn general enemy entity
	 ***********************************************************************/
	public void spawn()
	{
		Random r = new Random();
		int rnd = r.nextInt(10);
		int rndYPos= r.nextInt(18);
		if(rnd >= 6)
		{
			Enemy enemy = new Enemy(1, rndYPos, this, Entity.UnitType.Melee);
			this.enemyArmyEntities.add(enemy);
			System.out.println("Spawn");
		}
		//SpawnPoint spawn = new SpawnPoint(1, rndYPos);		
	}
		
	/********************************************************************
	 *  Updates level entities 60 times a second for rendering, etc
	 ********************************************************************/
	public void update()
	{	
		if(game.getMouse().inBounds(getGame().getCanvas().getWidth()/2 + 60, getGame().getCanvas().getHeight()/2 + 60, 60, 60) && game.getMouse().isMouseClicked())
		{
			player.checkForSlainEnemy();
		}

		
		LinkedList<Particle> particles = this.particleEntities;
		for(int i = 0; i < particles.size(); i++)
		{
			Entity e = particles.get(i);
			
			//System.out.println("X: " + e.getCenterX() +" Y: " + e.getCenterY());
			if(e.isVisible() == true && e != null)
			{
				e.update();
			}
			else
			{
				particleEntities.remove(i);
			}
		}
		

		
	}	
	

	public List<Projectile> getProjectiles()
	{
		return projectiles;
	}
	
	private void time()
	{
		
	}
	
	// Prob delete..its unused and relies on old classes
	public boolean tileCollision(double x, double y, double xAxis, double yAxis, int size)// x & y will be position of entity, xa/xa is dir its heading
	{
		boolean solid = false;
		for(int c = 0; c < 4; c++)
		{
			int xt = (((int)x + (int)xAxis) + c % 2 * size * 2 - 12) / 16;
			int yt = (((int)y + (int)yAxis) + c / 2 * size + 2 ) / 16;
			
			if (getTile( xt, yt).solid()) solid = true;// check future x/y locations for solids	
		
		}
		return solid;
	}
	
	/**********************************************************************
	*  Draw inside current level
	*  int xScroll: camera x position
	*  int yScroll: camera y position
	*  Camera camera: instance of camera
	*  Graphics g: canvas object camera is rendering to
	***********************************************************************/
	public void render(int xScroll, int yScroll, Camera screen, Graphics g)
	{		
		if(xScroll < 0 || xScroll > 6100) xScroll = 0;
		if(yScroll < 0 || yScroll > 5450) yScroll = 0;
		
		screen.setOffset(xScroll, yScroll);//set offsets to location of player
		//System.out.println("xScroll: " + xScroll/64 + "yScroll: " + yScroll/64);
		this.getPlayer().xScroll = xScroll;
		this.getPlayer().yScroll = yScroll;
		
		int mapX = screen.getxOffset() / 32;
		int mapY = screen.getyOffset() / 32;
		int mapXoffset = xScroll % 64;
		int mapYoffset = yScroll % 64;
		
		int a = getWidth() * 32 - getGame().getFrame().getWidth();
		
		
		g.drawImage(Assets.map, (screen.getxOffset() % 64) - screen.getxOffset(), (screen.getyOffset() % 64+32) - yScroll, null);		
	//System.out.println("Total Memory:" + Runtime.getRuntime().totalMemory());
	//System.out.println("Max Memory:" + Runtime.getRuntime().maxMemory());

		drawLootPanel(g);
		
		// render Entities
		/*for(int i = 0; i < playerCityEntities.size(); i++)
		{
			if(playerCityEntities.get(i).isVisible())
			{			
					playerCityEntities.get(i).render(screen);
			}
				//if (playerBuildingEntities.get(i).isBarrack())// if building is a barrack
		}				
		
		if( isTouchingDown() || isEntitySelected())
		{
			// render grid entity identifiers
			drawGridPointers(screen, g);
		}
		
		drawArrows(g, screen);
		
		for(int i = 0; i < droppedResourceEntities.size(); i++)
		{
			if(droppedResourceEntities.get(i).isVisible())
			{
				droppedResourceEntities.get(i).render(screen);
			}
		}
				
		for(int i = 0; i < playerArmyEntities.size(); i++)
		{
			if(playerArmyEntities.get(i).isVisible())
			{
				playerArmyEntities.get(i).render(screen);
			}
		}	
				
		for(int i = 0; i < enemyArmyEntities.size(); i++)
		{
			if(enemyArmyEntities.get(i).isVisible())
			{
				enemyArmyEntities.get(i).render(screen);
			}
		}
		
		for(int i = 0; i < particleEntities.size(); i++)
		{
			if(particleEntities.get(i).isVisible())
			{
				particleEntities.get(i).render(screen);
			}
		}
		
		for(int i = 0; i < projectileEntities.size(); i++)
		{
			if(projectileEntities.get(i).isVisible())
			{
				projectileEntities.get(i).render(screen);
			}
		}		
		*/
		/*for(int i = 0; i < projectiles.size(); i++)
		{
			projectiles.get(i).render(screen);
	
		}
		
		for (int i = 0; i < particles.size(); i++)
		{
			particles.get(i).render(screen);
		}
		
		for(int i = 0; i < npcs.size(); i++)
		{
			npcs.get(i).render(screen);
	
		}*/
		
		//drawGUI(g);		
	}	
	
	private void drawLootPanel(Graphics g) 
	{
		if(this.getPlayer().isLootDropped() == true)
		{
			g.drawImage(Assets.resPanel, 10, 10, null);
			
			// Get total items including gold in temp loot drop
			int ii = 2;
			for(int i = 0; i < LootSystem.tempLoot.size(); i++)
			{

				if(LootSystem.getTempLoot().get(i).getItemType() == ItemType.Gold)
				{
					if(LootSystem.getTempLoot().get(i).getGold() > 0)
					{			
						g.drawImage(Assets.goldCoin, 15, 15, null);
						g.drawString(LootSystem.getTempLoot().get(i).getGold() + " Gold", 78, 58);
					}
				}
				else if(LootSystem.getTempLoot().get(i).getItemType() == Item.ItemType.Weapon)
				{
					if(i == 0)
					{

						g.drawImage(LootSystem.getTempLoot().get(i).getImage(), 15, 15, null);
						switch(LootSystem.getTempLoot().get(i).getRarity())
						{
						case 'c':
							g.setColor(Color.WHITE);
							g.drawString(LootSystem.getTempLoot().get(i).getName(), 78, 58);
							break;
						case 'u':
							g.setColor(Color.GREEN);
							g.drawString(LootSystem.getTempLoot().get(i).getName(), 78, 58);
							break;
						case 'r':
							g.setColor(Color.BLUE);
							g.drawString(LootSystem.getTempLoot().get(i).getName(), 78, 58);
							break;
						case 'l':
							g.setColor(Color.ORANGE);
							g.drawString(LootSystem.getTempLoot().get(i).getName(), 78, 58);
							break;
						default:
							break;							
						}
					}
					else
					{
						g.drawImage(LootSystem.getTempLoot().get(i).getImage(), 15, 30 * ( ii), null);
						
						switch(LootSystem.getTempLoot().get(i).getRarity())
						{
						case 'c':
							g.setColor(Color.WHITE);
							g.drawString(LootSystem.getTempLoot().get(i).getName(), 78, 36 * (ii));
							break;
						case 'u':
							g.setColor(Color.GREEN);
							g.drawString(LootSystem.getTempLoot().get(i).getName(), 78, 36 * (ii));
							break;
						case 'r':
							g.setColor(Color.BLUE);
							g.drawString(LootSystem.getTempLoot().get(i).getName(), 78, 36 * (ii));
							break;
						case 'l':
							g.setColor(Color.ORANGE);
							g.drawString(LootSystem.getTempLoot().get(i).getName(), 78, 36 * (ii));
							break;
						default:
							break;							
						}
					}
				}
				else if(LootSystem.getTempLoot().get(i).getItemType() == Item.ItemType.Armor)
				{
					if(i == 0)
					{

						g.drawImage(LootSystem.getTempLoot().get(i).getImage(), 15, 15, null);
						switch(LootSystem.getTempLoot().get(i).getRarity())
						{
						case 'c':
							g.setColor(Color.WHITE);
							g.drawString(LootSystem.getTempLoot().get(i).getName(), 78, 58);
							break;
						case 'u':
							g.setColor(Color.GREEN);
							g.drawString(LootSystem.getTempLoot().get(i).getName(), 78, 58);
							break;
						case 'r':
							g.setColor(Color.BLUE);
							g.drawString(LootSystem.getTempLoot().get(i).getName(), 78, 58);
							break;
						case 'l':
							g.setColor(Color.ORANGE);
							g.drawString(LootSystem.getTempLoot().get(i).getName(), 78, 58);
							break;
						default:
							break;							
						}
					}
					else
					{
						g.drawImage(LootSystem.getTempLoot().get(i).getImage(), 15, 30 * ( ii), null);
						
						switch(LootSystem.getTempLoot().get(i).getRarity())
						{
						case 'c':
							g.setColor(Color.WHITE);
							g.drawString(LootSystem.getTempLoot().get(i).getName(), 78, 36 * (ii));
							break;
						case 'u':
							g.setColor(Color.GREEN);
							g.drawString(LootSystem.getTempLoot().get(i).getName(), 78, 36 * (ii));
							break;
						case 'r':
							g.setColor(Color.BLUE);
							g.drawString(LootSystem.getTempLoot().get(i).getName(), 78, 36 * (ii));
							break;
						case 'l':
							g.setColor(Color.ORANGE);
							g.drawString(LootSystem.getTempLoot().get(i).getName(), 78, 36 * (ii));
							break;
						default:
							break;							
						}
					}
					

					
				}
				ii++;
			}
		}	
		
	}

	/***************************************************************************************
	 * Graphics g: canvas for camera to render on
	 * Camera screen: instance of camera
	 ***************************************************************************************/
	private void drawContestedFactionTerritory(Graphics g, Camera screen) 
	{
		for(int y = 0; y < 85; y++)
		{
			for(int x = 0; x < 95; x++)
			{		
				if(this.contestedMapTerritoryDataLayer[x][y] == 1)
				{
					g.drawImage(Sprite.factionBlue.getLotus(), (x * 64) - screen.getxOffset() - (screen.getxOffset() % 64), (y * 64 + 32) - screen.getyOffset() - (screen.getyOffset() % 64), null);
				}
				else if(this.contestedMapTerritoryDataLayer[x][y] == 2)
				{
					g.drawImage(Sprite.factionPurp.getLotus(), (x * 64) - screen.getxOffset() - (screen.getxOffset() % 64), (y * 64 + 32) - screen.getyOffset() - (screen.getyOffset() % 64), null);

				}
				else if(this.contestedMapTerritoryDataLayer[x][y] == 3)
				{
					g.drawImage(Sprite.factionRed.getLotus(), (x * 64) - screen.getxOffset() - (screen.getxOffset() % 64), (y * 64 + 32) - screen.getyOffset() - (screen.getyOffset() % 64), null);

				}
			}
		}		
	}

	/***************************************************************************************
	 * Graphics g: canvas for camera to render on
	 * Camera screen: instance of camera
	 ***************************************************************************************/
	private void renderSelectedBuilding(Graphics g, Camera screen)
	{
		int x,y;
		x = getGame().getMouse().getX() / 32;
		y = getGame().getMouse().getY() / 32;
		x = (x * 32) + (x%32);
		y = (y * 32) + (y%32);		
		
		g.drawImage(Sprite.stoneWallV.getLotus(), ((((getGame().getMouse().getX() / 32) * 32 )) + ((screen.getxOffset() % 32)) + 8 ), (((getGame().getMouse().getY() /32 ) * 32) + (screen.getyOffset() % 32)), null);
	}
	
	/*************************************************
	 * Triggers build-mode grid draw
	 *************************************************/
	private void renderBuildGrid(Graphics g) 
	{
		if(getPlayer().isBuildMode())
		{
			paintBuildGrid(g);
		}
	}

	/*****************************************************
	 * Draws build-mode grid depending on if panel is on
	 ****************************************************/
	private void paintBuildGrid(Graphics g)
	{
		if(getPlayer().isPanelOn() && getPlayer().isBuildMode())
		{
			g.drawImage(Assets.buildModeGrid, 200, 0, null);
		}
		else if(getPlayer().isBuildMode())
		{
			g.drawImage(Assets.buildModeGridWide, 0, 0, null);
		}
	}

	/******************************************
	 * Draws combat grid depending if panel is on
	 * @param g: canvas for drawing
	 ******************************************/
	private void renderCombatGrid(Graphics g) 
	{
		if(!getPlayer().isPanelOn() && this.isEntitySelected())	
		{
			g.drawImage(Assets.combatModeGrid, 0, 0, null);	
		}
		else if (getPlayer().isPanelOn() && this.isEntitySelected())
		{
			g.drawImage(Assets.combatModeGridWide, 192, 0, null);
		}		
	}
	
	/************************************
	 *  Draws all Graphical UI to screen 
	 *  Graphics g: canvas for drawing
	 ************************************/
	private void drawGUI(Graphics g) 
	{	
		int w = (this.getGame().getFrame().getWidth() - 200);
		g.drawImage(Assets.resPane, 200, 0, w, 32, null);
		g.drawImage(Assets.resWidget, 0, -1,  null);
		drawHUD(g);
		drawPane(g);
	}

	/**********************************
	 *  Draw left panel & pane
	 **********************************/
	private void drawPane(Graphics g) 
	{
		int unit_1_UIx = 16;
		int unit_1_UIy = 64;
		int unit_2_UIx = 16;
		int unit_2_UIy = 96;
		
		if(getPlayer().isPanelOn() && isEntitySelected())
		{
			g.drawImage(Assets.resPanel, 0, 42, null);
		}
		else if(getPlayer().isPanelOn() && !isEntitySelected() && getPlayer().isTownManagerMode())
		{
			g.drawImage(Assets.resPanel, 0, 41, null);
			
			// display name of city
			for(int i = 0; i < this.selectedCity.getCityName().length(); i++)
			{
				g.drawImage(this.getLetter(this.selectedCity.getCityName().charAt(i)).getLotus(), 0 + (18 * i), 27, null);
			}
			
		// draw producible units for this city	
			// unit 1
			drawUnit1(g, unit_1_UIx, unit_1_UIy);
			
			// unit 2
			drawUnit2(g, unit_2_UIx, unit_2_UIy);
			
			
		}
		
		if(!getPlayer().isPanelOn())
		{
			g.drawImage(Assets.nextTurnButton, 0, getGame().getCanvas().getHeight()-32, null, null);
		}
		
		if (this.getPlayer().isBuildingSelected() && this.getPlayer().isPanelOn() && this.getPlayer().isBuildingSelected())
		{
			//g.drawImage(Assets.brownUI, brownUIx, brownUIy);
			g.drawImage(Sprite.castleWall.getLotus(), UIxItem_1, UIyItem_1, null );
			//g.drawSprite(Sprite.stoneWallV, UIxItem_1 , UIyItem_1);
			//g.drawSprite(Sprite.castleWall, UIxItem_2, UIyItem_2);
			g.drawImage(Assets.buildingIcon_select, unit_1_UIx, unit_1_UIy, null);
			g.drawImage(Assets.militaryIcon, unit_2_UIx, unit_2_UIy, null);
		}
		else if(this.getPlayer().isMilitarySelected() && this.getPlayer().isPanelOn() && this.getPlayer().isMilitarySelected())
		{
			//g.drawImage(Assets.brownUI, brownUIx, brownUIy);
			g.drawImage(Sprite.peasSpriteD.getLotus(), UIxItem_1, UIyItem_1, null );
			g.drawImage(Sprite.archerD.getLotus(), UIxItem_2, UIyItem_2, null );
			g.drawImage(Assets.buildingIcon, unit_1_UIx, unit_1_UIy, null);
			g.drawImage(Assets.militaryIcon_select, unit_2_UIx, unit_2_UIy, null);
		}
		//else if (this.isPlayerEntitySelected() && this.getPlayer().isPanelOn())
		//{
			//g.drawSprite(Sprite.num_0, 25, 75);
			// create draw method for cleanlieness
		  //}
		else if (this.isPlayerEntitySelected() && getPlayer().isPanelOn())
		{
			if(selectedEntity != null)
			{
				int moves = this.selectedEntity.getGridMoves();
				//System.out.println(moves + " Action Points" );
				
				Direction d = getSelectedEntity().getDirection();
				
				if(d == Entity.Direction.DOWN)
				{
					g.drawImage(Sprite.positionArrowU.getLotus(), 28, 200, null);

					g.drawImage(Sprite.positionArrowR.getLotus(), 62, 200, null);

					g.drawImage(Sprite.selectPositionArrowD.getLotus(), 96, 200, null);

					g.drawImage(Sprite.positionArrowL.getLotus(), 130, 200, null);
				}
				else if(d == Direction.UP)
				{
					g.drawImage(Sprite.selectPositionArrowU.getLotus(), 28, 200, null);

					g.drawImage(Sprite.positionArrowR.getLotus(), 62, 200, null);

					g.drawImage(Sprite.positionArrowD.getLotus(), 96, 200, null);

					g.drawImage(Sprite.positionArrowL.getLotus(), 130, 200, null);
				}
				else if(d == Direction.LEFT)
				{
					g.drawImage(Sprite.positionArrowU.getLotus(), 28, 200, null);

					g.drawImage(Sprite.positionArrowR.getLotus(), 62, 200, null);

					g.drawImage(Sprite.positionArrowD.getLotus(), 96, 200, null);

					g.drawImage(Sprite.selectPositionArrowL.getLotus(), 130, 200, null);
				}
				else if(d == Direction.RIGHT)
				{
					g.drawImage(Sprite.positionArrowU.getLotus(), 28, 200, null);

					g.drawImage(Sprite.selectPositionArrowR.getLotus(), 62, 200, null);

					g.drawImage(Sprite.positionArrowD.getLotus(), 96, 200, null);

					g.drawImage(Sprite.positionArrowL.getLotus(), 130, 200, null);
				}

				g.setColor(Color.BLACK);
				g.setFont(new Font("Century", 0, 18));
			
				// draw sprite of selected entity
				g.drawImage(Sprite.peasSpriteR.getLotus(), 5, 55, null);

				g.drawString("Spearman ", 40, 85);
				g.drawString("Action Points: ", 35, 105);
				g.drawString(String.valueOf(moves), 155, 105);
				g.drawString("Health: ", 35, 125);
				g.drawString(String.valueOf(getPlayerArmyEntities().get(selectedEntity.getPosInLink()).getHealth()) + "/100", 100, 125);
				g.drawString("Attack: ", 35, 145);
				g.drawString(String.valueOf(selectedEntity.getAtkRating()), 100, 145);
				g.drawString("Defence: ", 35, 165);
				g.drawString(String.valueOf(selectedEntity.getDefRating()), 107, 165);
				g.drawString("Vet Lvl: ", 35, 185);
				g.drawString(String.valueOf(selectedEntity.getVetLvl()), 107, 185);
				
				if(isUIItem_1Selected())
				{
					g.drawImage(Assets.eatIconSelected, 15, 245, null);

				}
				else
				{
					g.drawImage(Assets.eatIcon, 15, 245, null);
				}
			}
			this.getPlayer().setBuildingSelected(false);
			this.getPlayer().setMilitarySelected(false);
			// create draw method for cleanliness
		}
		else if (this.isEnemyEntitySelected() && getPlayer().isPanelOn())
		{
			if(selectedEntity != null)
			{
				int moves = this.selectedEntity.getGridMoves();
				//System.out.println(moves + " Action Points" );

				Direction d = getSelectedEntity().getDirection();
				
				if(d == Entity.Direction.DOWN)
				{
					g.drawImage(Sprite.positionArrowU.getLotus(), 28, 200, null);

					g.drawImage(Sprite.positionArrowR.getLotus(), 62, 200, null);

					g.drawImage(Sprite.selectPositionArrowD.getLotus(), 96, 200, null);

					g.drawImage(Sprite.positionArrowL.getLotus(), 130, 200, null);
				}
				else if(d == Direction.UP)
				{
					g.drawImage(Sprite.selectPositionArrowU.getLotus(), 28, 200, null);

					g.drawImage(Sprite.positionArrowR.getLotus(), 62, 200, null);

					g.drawImage(Sprite.positionArrowD.getLotus(), 96, 200, null);

					g.drawImage(Sprite.positionArrowL.getLotus(), 130, 200, null);
				}
				else if(d == Direction.LEFT)
				{
					g.drawImage(Sprite.positionArrowU.getLotus(), 28, 200, null);

					g.drawImage(Sprite.positionArrowR.getLotus(), 62, 200, null);

					g.drawImage(Sprite.positionArrowD.getLotus(), 96, 200, null);

					g.drawImage(Sprite.selectPositionArrowL.getLotus(), 130, 200, null);
				}
				else if(d == Direction.RIGHT)
				{
					g.drawImage(Sprite.positionArrowU.getLotus(), 28, 200, null);

					g.drawImage(Sprite.selectPositionArrowR.getLotus(), 62, 200, null);

					g.drawImage(Sprite.positionArrowD.getLotus(), 96, 200, null);

					g.drawImage(Sprite.positionArrowL.getLotus(), 130, 200, null);
				}
				
				g.setColor(Color.BLACK);
				g.setFont(new Font("Century", 0, 18));
			
				// draw sprite of selected entity
				g.drawImage(Sprite.enemSpriteR.getLotus(), 5, 55, null);

				g.drawString("Bandit Swordsman ", 37, 85);
				g.drawString("Action Points: ", 35, 105);
				g.drawString(String.valueOf(moves), 155, 105);
				g.drawString("Health: ", 35, 125);
				g.drawString(String.valueOf(selectedEntity.getHealth()) + "/100", 100, 125);
				g.drawString("Attack: ", 35, 145);
				g.drawString(String.valueOf(selectedEntity.getAtkRating()), 100, 145);
				g.drawString("Defence: ", 35, 165);
				g.drawString(String.valueOf(selectedEntity.getDefRating()), 107, 165);
				g.drawString("Vet Lvl: ", 35, 185);
				g.drawString(String.valueOf(selectedEntity.getVetLvl()), 107, 185);
			}
			this.getPlayer().setBuildingSelected(false);
			this.getPlayer().setMilitarySelected(false);
		}
		else
		{
			this.getPlayer().setBuildingSelected(false);
			this.getPlayer().setMilitarySelected(false);
		}
		
		
		// Check for selection of first building ui item 
		if (getPlayer().isPanelOn() && getPlayer().isBuildingSelected() && getGame().getMouse().inBounds( Level.UIxItem_1, Level.UIyItem_1, 32, 32) )
		{
			//this.level.setSelectedBuilding(wall);
			//GameManager.level.getPlayer().setBuildMode(true);
			getPlayer().setUiItem_1(true);
		}
		else if (getPlayer().isPanelOn() && getPlayer().isBuildingSelected() && getGame().getMouse().inBounds(Level.UIxItem_2, Level.UIyItem_2, 32, 32))
		{	
			//GameManager.level.getPlayer().setBuildMode(true);
			getPlayer().setUiItem_2(true);					
		}
		
		// set/check selected military entities
		if (getPlayer().isPanelOn() && getPlayer().isMilitarySelected() && getGame().getMouse().inBounds( Level.UIxItem_1, Level.UIyItem_1, 32, 32) )
		{
			//this.level.setSelectedBuilding(wall);
			getPlayer().setBuildMode(false);
			getPlayer().setUiItem_1(true);
		}
		else if (getPlayer().isPanelOn() && getPlayer().isMilitarySelected() && getGame().getMouse().inBounds( Level.UIxItem_2, Level.UIyItem_2, 32, 32))
		{	
			getPlayer().setBuildMode(false);
			getPlayer().setUiItem_2(true);					
		}		
	}

	/*************************************************
	 * Draw Archer unit in city mode for production 
	 * g: drawing obj
	 * x: x pos in pixels to draw
	 * y: y pos in pixels to draw 
	 ********************************************/
	private void drawUnit1(Graphics g, int x, int y) 
	{
		String s = "Archer";
		// unit 1
		g.drawImage(Sprite.archerUnitIcon.getLotus(), x, y, null);
		
		// draw unit name
		for(int i = 0; i < s.length(); i++)
		{
			g.drawImage(this.getLetter(s.charAt(i)).getLotus(), x * i + 42, y, null);
		}
		
		// display amt of turns for this unit to be built
		g.drawImage(this.getNumber(5).getLotus(),  x + 134, y, null);		
	}
	
	/*************************************************
	 * Draw Spearman unit in city mode for production 
	 * g: drawing obj
	 * x: x pos in pixels to draw
	 * y: y pos in piels to draw 
	 ********************************************/
	private void drawUnit2(Graphics g, int x, int y) 
	{
		String s = "Spears";
		// unit 1
		g.drawImage(Sprite.spearUnitIcon.getLotus(), x, y, null);
		
		// draw unit name
		for(int i = 0; i < s.length(); i++)
		{
			g.drawImage(this.getLetter(s.charAt(i)).getLotus(), x * i + 42, y, null);
		}
		
		// display amt of turns for this unit to be built
		g.drawImage(this.getNumber(4).getLotus(),  x + 134, y, null);		
	}

	private void drawHUD(Graphics g) 
	{
		int cGold;
		int cTurn; // year
		int cSeason; //  
		String s = new String();
		g.drawImage(Sprite.meat.getLotus(), (26*16) + (s.length() * 16) + 15, 0, null);
		
		//draw current turn/year and seasons
		g.drawImage(Sprite.daysText.getLotus(), 85, -5, null);
		cTurn = getPlayer().getCurTurn();
		cTurn = 1059;//hard value for testing

		s = String.valueOf(cTurn);
		for(int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			if(c == '0')
			{
				g.drawImage(Sprite.num_0.getLotus(), (3*16 ) + (16 * i), 15, null);
			}
			else if (c == '1')
			{
				g.drawImage(Sprite.num_1.getLotus(), (3*16 ) + (16 * i), 15, null);
			}
			else if(c == '2')
			{
				g.drawImage(Sprite.num_2.getLotus(), (3*16 ) + (16 * i), 15, null);
			}
			else if(c == '3')
			{
				g.drawImage(Sprite.num_3.getLotus(), (3*16 ) + (16 * i), 15, null);
			}
			else if(c == '4')
			{
				g.drawImage(Sprite.num_4.getLotus(), (3*16 ) + (16 * i), 15, null);
			}
			else if (c == '5')
			{
				g.drawImage(Sprite.num_5.getLotus(), (3*16 ) + (16 * i), 15, null);
			}
			else if (c == '6')
			{
				g.drawImage(Sprite.num_6.getLotus(), (3*16 ) + (16 * i), 15, null);
			}
			else if(c == '7')
			{
				g.drawImage(Sprite.num_7.getLotus(), (3*16 ) + (16 * i), 15, null);
			}
			else if(c == '8')
			{
				g.drawImage(Sprite.num_8.getLotus(), (3*16 ) + (16 * i), 15, null);
			}
			else if(c == '9')
			{
				g.drawImage(Sprite.num_9.getLotus(), (3*16 ) + (16 * i), 15, null);
			}
		}
		
		
		// draw current gold
		g.drawImage(Sprite.gold.getLotus(), (17*13) + (s.length() * 16) + 24, -28, null);
		cGold = getPlayer().getGold();
		s = String.valueOf(cGold);
		//g.drawImage(Sprite.goldText.getLotus(), 20*16, 2, null);
		for(int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			if(c == '0')
			{
				g.drawImage(Sprite.num_0.getLotus(), (23*16) + (16 * i), 6, null);
			}
			else if (c == '1')
			{
				g.drawImage(Sprite.num_1.getLotus(), (23*16) + (16 * i), 6, null);
			}
			else if(c == '2')
			{
				g.drawImage(Sprite.num_2.getLotus(), (23*16) + (16 * i), 6, null);
			}
			else if(c == '3')
			{
				g.drawImage(Sprite.num_3.getLotus(), (23*16) + (16 * i), 6, null);
			}
			else if(c == '4')
			{
				g.drawImage(Sprite.num_4.getLotus(), (23*16) + (16 * i), 6, null);
			}
			else if (c == '5')
			{
				g.drawImage(Sprite.num_5.getLotus(), (23*16) + (16 * i), 6, null);
			}
			else if (c == '6')
			{
				g.drawImage(Sprite.num_6.getLotus(), (23*16) + (16 * i), 6, null);
			}
			else if(c == '7')
			{
				g.drawImage(Sprite.num_7.getLotus(), (23*16) + (16 * i), 6, null);
			}
			else if(c == '8')
			{
				g.drawImage(Sprite.num_8.getLotus(), (23*16) + (16 * i), 6, null);
			}
			else if(c == '9')
			{
				g.drawImage(Sprite.num_9.getLotus(), (23*16) + (16 * i), 6, null);
			}
		}
			
		// draw cur food
		int food = getPlayer().getFood();
		s = String.valueOf(food);
		for(int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			if(c == '0')
			{
				g.drawImage(Sprite.num_0.getLotus(), (29*16 + 20) + (13 * i), 8, null);
			}
			else if (c == '1')
			{
				g.drawImage(Sprite.num_1.getLotus(), (29*16 + 20) + (13 * i), 8, null);
			}
			else if(c == '2')
			{
				g.drawImage(Sprite.num_2.getLotus(), (29*16 + 20) + (13 * i), 8, null);
			}
			else if(c == '3')
			{
				g.drawImage(Sprite.num_3.getLotus(), (29*16 + 20) + (13 * i), 8, null);
			}
			else if(c == '4')
			{
				g.drawImage(Sprite.num_4.getLotus(), (29*16 + 20) + (13 * i), 8, null);
			}
			else if (c == '5')
			{
				g.drawImage(Sprite.num_5.getLotus(), (29*16 + 20) + (13 * i), 8, null);
			}
			else if (c == '6')
			{
				g.drawImage(Sprite.num_6.getLotus(), (29*16 + 20) + (13 * i), 8, null);
			}
			else if(c == '7')
			{
				g.drawImage(Sprite.num_7.getLotus(), (29*16 + 20) + (13 * i), 8, null);
			}
			else if(c == '8')
			{
				g.drawImage(Sprite.num_8.getLotus(), (30*16 + 20) + (13 * i), 8, null);
			}
			else if(c == '9')
			{
				g.drawImage(Sprite.num_9.getLotus(), (30*16 + 20) + (13 * i), 8, null);
			}
		}			
	}

	public void add(Entity e)
	{
		e.init(this);
		if (e instanceof Particle)
		{
			particles.add((Particle) e);
		}
		else if(e instanceof Projectile)
		{
			projectiles.add((Projectile) e);
		}
		else
		{
			entities.add(e);
		}
	}
	
	public void addProjectile(Projectile p)
	{
		p.init(this);// set lvl to current level
		projectiles.add(p);
	}
	
	public void addNPC(NPC n)
	{
		npcs.add(n);
	}
	
	public void moveEnemyEntity() 
	{	
			this.setIsTouchingDown(false);
			this.setIsEntitySelected(false);
			this.setPlayerEntitySelected(false);
			//this.selectedEntity = null;	
	}
	
	
	/**********************************************
	 * Return number sprite based on passed integer
	 * i: integer number
	 **********************************************/
	public Sprite getNumber(int i)
	{
		Sprite s = null;
		switch(i)
		{
			case 1:
				s = Sprite.scribe_num_1;
				break;
			case 2:
				s = Sprite.scribe_num_2;
				break;
			case 3:
				s = Sprite.scribe_num_3;
				break;
			case 4:
				s = Sprite.scribe_num_4;
				break;
			case 5:
				s = Sprite.scribe_num_5;
				break;
			case 6:
				s = Sprite.scribe_num_6;
				break;
			case 7:
				s = Sprite.scribe_num_7;
				break;
			case 8:
				s = Sprite.scribe_num_8;
				break;
			case 9:
				s = Sprite.scribe_num_9;
				break;
				
			default:
				s = null;
				break;
			
		}
		return s;
	}
	
	public Sprite getLetter(char c)
	{
		Sprite s = null;
		switch(c)
		{
			case 'a':
				s = Sprite.lowerA;
				break;
			case 'b':
				s = Sprite.lowerB;
				break;
			case 'c':
				s = Sprite.lowerC;
				break;
			case 'd':
				s = Sprite.lowerD;
				break;
			case 'e':
				s = Sprite.lowerE;
				break;
			case 'f':
				s = Sprite.lowerF;
				break;
			case 'g':
				s = Sprite.lowerG;
				break;
			case 'h':
				s = Sprite.lowerH;
				break;
			case 'i':
				s = Sprite.lowerI;
				break;
			case 'j':
				s = Sprite.lowerJ;
				break;
			case 'k':
				s = Sprite.lowerK;
				break;
			case 'l':
				s = Sprite.lowerL;
				break;
			case 'm':
				s = Sprite.lowerM;
				break;
			case 'n':
				s = Sprite.lowerN;
				break;
			case 'o':
				s = Sprite.lowerO;
				break;
			case 'p':
				s = Sprite.lowerP;
				break;
			case 'q':
				s = Sprite.lowerQ;
				break;
			case 'r':
				s = Sprite.lowerR;
				break;
			case 's':
				s = Sprite.lowerS;
				break;
			case 't':
				s = Sprite.lowerT;
				break;
			case 'u':
				s = Sprite.lowerU;
				break;
			case 'v':
				s = Sprite.lowerV;
				break;
			case 'w':
				s = Sprite.lowerW;
				break;
			case 'x':
				s = Sprite.lowerX;
				break;
			case 'y':
				s = Sprite.lowerY;
				break;
			case 'z':
				s = Sprite.lowerZ;
				break;
				
			case 'A':
				s = Sprite.upperA;
				break;
			case 'B':
				s = Sprite.upperB;
				break;
			case 'C':
				s = Sprite.upperC;
				break;
			case 'D':
				s = Sprite.upperD;
				break;
			case 'E':
				s = Sprite.upperE;
				break;
			case 'F':
				s = Sprite.upperF;
				break;
			case 'G':
				s = Sprite.upperG;
				break;
			case 'H':
				s = Sprite.upperH;
				break;
			case 'I':
				s = Sprite.upperI;
				break;
			case 'J':
				s = Sprite.upperJ;
				break;
			case 'K':
				s = Sprite.upperK;
				break;
			case 'L':
				s = Sprite.upperL;
				break;
			case 'M':
				s = Sprite.upperM;
				break;
			case 'N':
				s = Sprite.upperN;
				break;
			case 'O':
				s = Sprite.upperO;
				break;
			case 'P':
				s = Sprite.upperP;
				break;
			case 'Q':
				s = Sprite.upperQ;
				break;
			case 'R':
				s = Sprite.upperR;
				break;
			case 'S':
				s = Sprite.upperS;
				break;
			case 'T':
				s = Sprite.upperT;
				break;
			case 'U':
				s = Sprite.upperU;
				break;
			case 'V':
				s = Sprite.upperV;
				break;
			case 'W':
				s = Sprite.upperW;
				break;
			case 'X':
				s = Sprite.upperX;
				break;
			case 'Y':
				s = Sprite.upperY;
				break;
			case 'Z':
				s = Sprite.upperZ;
				break;
				
			default:
				s = null;
				break;
			
		}
		return s;
	}
	
	/********************************************************************
	 * Find and select town/city entity in clicked area
	 * x: x pos in pixels
	 * y: y pos in pixels
	 *
	 *******************************************************************/
	public void tileEntityPointer(int x, int y, int xOffset, int yOffset) 
	{		
		for(int i = 0; i < getPlayerCityEntities().size(); i++)
		{
			if(x > (getPlayerCityEntities().get(i).getCenter64X() * 64)  && 
			   x < (getPlayerCityEntities().get(i).getCenter64X() * 64) + 55 - (xOffset %32) && 
			   y > (getPlayerCityEntities().get(i).getCenter64Y() * 64) + 32  &&    
			   y < (getPlayerCityEntities().get(i).getCenter64Y() * 64) + 50 + 32 - (yOffset %32))				   
			   {
				 getPlayer().setPanelOn(true);
				 getPlayer().setTownManagerMode(true);// new flag for tile town mode gui
				 this.selectedCity = getPlayerCityEntities().get(i);
			   }
			else
			{
				//GameManager.level.getPlayer().setPanelOn(false);				
				 //GameManager.level.getPlayer().setTownManagerMode(false);// new flag for tile town mode gui

			}
		}		
	}
	
	public void gridEntityPointerDragged(int x, int y) 
	{
		setIsTouchingDown(true);
		
		if(this.selectedEntity != null)
		{
			//this.selectedEntity = new Entity();
			this.selectedEntity.setCenter64X(x);
			this.selectedEntity.setCenter64Y(y);
			System.out.println("X: " + x + "Y: " + y);
			
			//if(this.selectedPlayerEntity != null)
			//{
				// set units comat arrow target
			if(!getPlayerArmyEntities().isEmpty())
			{
				getPlayerArmyEntities().get(this.selectedPlayerEntity.getPosInLink()).setGoalPos(this.selectedEntity);
			}
		}
	}
		
	public void gridEntityPointer(int x, int y, int xOffset, int yOffset)
	{
		setIsTouchingDown(true);
		
		//this.selectedEntity = new Entity();
		if(this.selectedEntity != null )
		{
			this.selectedEntity.setCenter64X(x);
			this.selectedEntity.setCenter64Y(y);
			//System.out.println("X: " + x + "Y: " + y);
			
		
		}		
		
		//System.out.println("Selected entity.");
		if( !isEntitySelected())
		{
			
			//System.out.println("X: " + ((event.x  / 32) + (screen.xOffset / 32 ) + "Y: " + ((event.y / 32) + (screen.yOffset / 32))));
			for(int i = 0; i < getPlayerArmyEntities().size(); i++)
			{
				if(getPlayerArmyEntities().get(i).getCenter64X() == x && getPlayerArmyEntities().get(i).getCenter64Y() == y)
				{

					// set entity to selected to be controlled.
					this.selectedEntity = new Entity(getPlayerArmyEntities().get(i));
					this.selectedPlayerEntity = new ArmyUnit(getPlayerArmyEntities().get(i));
			
					this.setPlayerEntitySelected(true);
					this.setEnemyEntitySelected(false);

					this.selectedEntity.setCenter64X(x);
					this.selectedEntity.setCenter64Y(y);
					//this.selectedEntity = playerArmyEntities.get(i);
					this.selectedEntity.setPosInLink(i);
					this.selectedPlayerEntity.setPosInLink(i);
					setIsEntitySelected(true);
					System.out.println("Selected player entity.");
					
					// set units comat arrow target
					getPlayerArmyEntities().get(this.selectedEntity.getPosInLink()).setGoalPos(this.selectedEntity);
					
					break;
				
				}
				else
				{
					//this.selectedEntity = null;

					this.setIsEntitySelected(false);
					this.setPlayerEntitySelected(false);
					getPlayer().setPanelOn(false);	
				}
			}
		
			if(!isEntitySelected() && isTouchingDown())
			{
				for(int i = 0; i < enemyArmyEntities.size(); i++)
				{
					if(enemyArmyEntities.get(i).getCenter64X() == x && enemyArmyEntities.get(i).getCenter64Y() == y)
					{
						// set entity to selected to be controlled.
						this.selectedEntity = new Entity(enemyArmyEntities.get(i));
						//this.selectedEntity.setCenter64X(x);
						//this.selectedEntity.setCenter64Y(y);
						//this.selectedEntity = enemyArmyEntities.get(i);
						this.selectedEntity.setPosInLink(i);
							//this.setPlayerEntitySelected(false);
						this.setEnemyEntitySelected(true);

						this.setIsEntitySelected(true);
						System.out.println("Selected enemy entity. " + this.selectedEntity.getPosInLink());
						break;
					
					}
					else
					{

						//this.selectedEntity = null;
						this.setIsEntitySelected(false);
						this.setEnemyEntitySelected(false);		
						getPlayer().setPanelOn(false);
					}
				}
			}	
		}	
		/*for(int i = 0; i < enemyArmyEntities.size(); i++)
		{
			if(enemyArmyEntities.get(i).getCenter64X() == x && enemyArmyEntities.get(i).getCenter64Y() == y)
			{
				enemyArmyEntities.get(i).render(screen);
			}
		}
		*/
		 
	}
	
	public boolean isEntitySelected() 
	{
		return this.isEntitySelected;
	}
	
	public void setPlayerEntitySelected(boolean b) 
	{
		this.isPlayerEntitySelected = b;
	}

	public void setIsEntitySelected(boolean b) 
	{
		this.isEntitySelected = b;		
	}

	public void setEnemyEntitySelected(boolean b) 
	{
		this.isEnemyEntitySelected = b;
	}
	
	public boolean checkNotTouchingPlyrEntity() 
	{
		if(this.selectedEntity != null)
		{
			if(playerArmyEntities.get(this.selectedEntity.getPosInLink()).getCenter64X() == this.selectedEntity.getCenter64X() && playerArmyEntities.get(this.selectedEntity.getPosInLink()).getCenter64Y() == this.selectedEntity.getCenter64Y())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	public boolean checkNotTouchingEnemEntity() 
	{
		if (this.selectedEntity != null)
		{
			if(enemyArmyEntities.get(this.selectedEntity.getPosInLink()).getCenter64X() == this.selectedEntity.getCenter64X() && enemyArmyEntities.get(this.selectedEntity.getPosInLink()).getCenter64Y() == this.selectedEntity.getCenter64Y())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	private void drawArrows(Graphics g, Camera screen)
	{
		if( !this.getPlayer().isPanelOn())
		{	
			for(int ii = 0; ii < getPlayerArmyEntities().size(); ii++)
			{
				if(getPlayerArmyEntities().get(ii).getGoalPos() != null)
				{
					int xDiff = getPlayerArmyEntities().get(ii).getGoalPos().getCenter64X() - getPlayerArmyEntities().get(ii).getCenter64X();
					int yDiff = getPlayerArmyEntities().get(ii).getGoalPos().getCenter64Y() - getPlayerArmyEntities().get(ii).getCenter64Y();
	
					// drawMoveArrows
					if(Math.abs(xDiff) == 1 )
					{
						if(getPlayerArmyEntities().get(ii).getCenter64X() < getPlayerArmyEntities().get(ii).getGoalPos().getCenter64X() && getPlayerArmyEntities().get(ii).getCenter64Y() == getPlayerArmyEntities().get(ii).getGoalPos().getCenter64Y())
						{	
							g.drawImage(Sprite.combatArrowRight.getLotus(), (getPlayerArmyEntities().get(ii).getGoalPos().getCenter64X() * 64 + (screen.getxOffset() % 64)) - (screen.getxOffset() + 64),(getPlayerArmyEntities().get(ii).getGoalPos().getCenter64Y() * 64 + (screen.getyOffset() % 64))  - (screen.getyOffset() ), null);
						}
						else if(getPlayerArmyEntities().get(ii).getCenter64X() > getPlayerArmyEntities().get(ii).getGoalPos().getCenter64X() && getPlayerArmyEntities().get(ii).getCenter64Y() == getPlayerArmyEntities().get(ii).getGoalPos().getCenter64Y())
						{
							g.drawImage(Sprite.combatArrowLeft.getLotus(), (getPlayerArmyEntities().get(ii).getGoalPos().getCenter64X() * 64 + (screen.getxOffset() % 64)) - (screen.getxOffset()),(getPlayerArmyEntities().get(ii).getGoalPos().getCenter64Y() * 64 + (screen.getyOffset() % 64))  - (screen.getyOffset() ), null);
						}
					}
					else if(Math.abs(xDiff) > 1 )
					{
						int i = 1;
						for(;i < Math.abs(xDiff); i++)
						{
							if(getPlayerArmyEntities().get(ii).getCenter64X() < getPlayerArmyEntities().get(ii).getGoalPos().getCenter64X() && getPlayerArmyEntities().get(ii).getCenter64Y() == getPlayerArmyEntities().get(ii).getGoalPos().getCenter64Y())
							{
								g.drawImage(Sprite.combatArrowHorEx.getLotus(), ((getPlayerArmyEntities().get(ii).getCenter64X() + i) * 64 + (screen.getxOffset() % 64)) - (screen.getxOffset() + 64),  (getPlayerArmyEntities().get(ii).getCenter64Y() * 64 + (screen.getyOffset() % 64))  - (screen.getyOffset() ), null);
							}
							else if(getPlayerArmyEntities().get(ii).getCenter64X() > getPlayerArmyEntities().get(ii).getGoalPos().getCenter64X() && getPlayerArmyEntities().get(ii).getCenter64Y() == getPlayerArmyEntities().get(ii).getGoalPos().getCenter64Y())
							{
								g.drawImage(Sprite.combatArrowHorEx.getLotus(), ((getPlayerArmyEntities().get(ii).getCenter64X() - i) * 64 + (screen.getxOffset() % 64)) - (screen.getxOffset()),  (getPlayerArmyEntities().get(ii).getCenter64Y() * 64 + (screen.getyOffset() % 64))  - (screen.getyOffset() ), null);
							}
						}
				
						if(getPlayerArmyEntities().get(ii).getCenter64X() < getPlayerArmyEntities().get(ii).getGoalPos().getCenter64X() && getPlayerArmyEntities().get(ii).getCenter64Y() == getPlayerArmyEntities().get(ii).getGoalPos().getCenter64Y())
						{
							g.drawImage(Sprite.combatArrowRight.getLotus(), ((getPlayerArmyEntities().get(ii).getGoalPos().getCenter64X() ) * 64 + (screen.getxOffset() % 64)) - (screen.getxOffset() + 64),  (getPlayerArmyEntities().get(ii).getGoalPos().getCenter64Y() * 64 + (screen.getyOffset() % 64))  - (screen.getyOffset() ), null);
						}
						else if(getPlayerArmyEntities().get(ii).getCenter64X() > getPlayerArmyEntities().get(ii).getGoalPos().getCenter64X() && getPlayerArmyEntities().get(ii).getCenter64Y() == getPlayerArmyEntities().get(ii).getGoalPos().getCenter64Y())
						{
							g.drawImage(Sprite.combatArrowLeft.getLotus(), ((getPlayerArmyEntities().get(ii).getGoalPos().getCenter64X() ) * 64 + (screen.getxOffset() % 64)) - (screen.getxOffset() ) ,  (getPlayerArmyEntities().get(ii).getGoalPos().getCenter64Y() * 64 + (screen.getyOffset() % 64))  - (screen.getyOffset() ), null);
						}
				

					} 
					else if (Math.abs(yDiff) == 1)
					{

						if(getPlayerArmyEntities().get(ii).getCenter64Y() < getPlayerArmyEntities().get(ii).getGoalPos().getCenter64Y() && getPlayerArmyEntities().get(ii).getCenter64X() == getPlayerArmyEntities().get(ii).getGoalPos().getCenter64X())
						{
							g.drawImage(Sprite.combatArrowDown.getLotus(), ((getPlayerArmyEntities().get(ii).getCenter64X() * 64 + (screen.getxOffset() % 64)) - 32) - (screen.getxOffset()),  (((getPlayerArmyEntities().get(ii).getCenter64Y()) * 64) + (screen.getyOffset() % 64))  - (screen.getyOffset() - 32), null);
						}
						else if(getPlayerArmyEntities().get(ii).getCenter64Y() > getPlayerArmyEntities().get(ii).getGoalPos().getCenter64Y() && getPlayerArmyEntities().get(ii).getCenter64X() == getPlayerArmyEntities().get(ii).getGoalPos().getCenter64X())
						{
							g.drawImage(Sprite.combatArrowUp.getLotus(), ((getPlayerArmyEntities().get(ii).getGoalPos().getCenter64X() * 64 + (screen.getxOffset() % 64)) - 32) - (screen.getxOffset()),  (((getPlayerArmyEntities().get(ii).getGoalPos().getCenter64Y()) * 64) + (screen.getyOffset() % 64))  - (screen.getyOffset() - 32), null);
						}
					}
					else if (Math.abs(yDiff) > 1)
					{
						int i = 1;
						for(;i < Math.abs(yDiff); i++)
						{
							if(getPlayerArmyEntities().get(ii).getCenter64Y() < getPlayerArmyEntities().get(ii).getGoalPos().getCenter64Y() && getPlayerArmyEntities().get(ii).getCenter64X() == getPlayerArmyEntities().get(ii).getGoalPos().getCenter64X())
							{
								g.drawImage(Sprite.combatArrowVertEx.getLotus(), (((getPlayerArmyEntities().get(ii).getCenter64X() * 64) + (screen.getxOffset() % 64)) - 32) - (screen.getxOffset()), (((getPlayerArmyEntities().get(ii).getCenter64Y() + i-1) * 64) + (screen.getyOffset() % 64)) - (screen.getyOffset() -32), null);
							}
							else if(getPlayerArmyEntities().get(ii).getCenter64Y() > getPlayerArmyEntities().get(ii).getGoalPos().getCenter64Y() && getPlayerArmyEntities().get(ii).getCenter64X() == getPlayerArmyEntities().get(ii).getGoalPos().getCenter64X())
							{
								g.drawImage(Sprite.combatArrowVertEx.getLotus(), (((getPlayerArmyEntities().get(ii).getCenter64X() * 64) + (screen.getxOffset() % 64)) - 32) - (screen.getxOffset()), (((getPlayerArmyEntities().get(ii).getCenter64Y() - i) * 64) + (screen.getyOffset() % 64)) - (screen.getyOffset() -32), null);
							}
						}
				
						if(getPlayerArmyEntities().get(ii).getCenter64Y() < getPlayerArmyEntities().get(ii).getGoalPos().getCenter64Y() && getPlayerArmyEntities().get(ii).getCenter64X() == getPlayerArmyEntities().get(ii).getGoalPos().getCenter64X())
						{
							g.drawImage(Sprite.combatArrowDown.getLotus(), ((getPlayerArmyEntities().get(ii).getGoalPos().getCenter64X() * 64 + (screen.getxOffset() % 64)) - 32) - (screen.getxOffset()),  ((getPlayerArmyEntities().get(ii).getGoalPos().getCenter64Y() -1)* 64 + (screen.getyOffset() % 64))  - (screen.getyOffset() - 32), null);
						}
						else if(getPlayerArmyEntities().get(ii).getCenter64Y() > getPlayerArmyEntities().get(ii).getGoalPos().getCenter64Y() && getPlayerArmyEntities().get(ii).getCenter64X() == getPlayerArmyEntities().get(ii).getGoalPos().getCenter64X())
						{
							g.drawImage(Sprite.combatArrowUp.getLotus(), ((getPlayerArmyEntities().get(ii).getGoalPos().getCenter64X() * 64 + (screen.getxOffset() % 64)) - 32) - (screen.getxOffset()),  ((getPlayerArmyEntities().get(ii).getGoalPos().getCenter64Y() ) * 64 + (screen.getyOffset() % 64))  - (screen.getyOffset() - 32), null);
						}
					}
				}
			}
		}
	}
	
	private void drawGridPointers(Camera screen, Graphics g)
	{
		if(this.selectedEntity != null)
		{
			int x = (((this.selectedEntity.getCenter64X() * 64) - screen.getxOffset() + (screen.getxOffset() % 64)));
			int y = (((this.selectedEntity.getCenter64Y() * 64) - screen.getyOffset() + (screen.getyOffset() % 64)+32));
			//System.out.println("X: " + x + " Y: " + y);
			if(getPlayer().isPanelOn())
			{
				g.drawImage(Assets.gridHorPointer, 200, y, getGame().getCanvas().getWidth(), 4, null);
				g.drawImage(Assets.gridVerPointer, x, 32, 4, getGame().getCanvas().getHeight()-32, null);
						
				g.drawImage(Sprite.selectedEntityPoint.getLotusImage().getImage(), x - 13, y - 13, null);	
				//GameManager.level.getPlayerArmyEntities().get(this.selectedPlayerEntity.getPosInLink());
				for(int i = 0; i < getEnemyArmyEntities().size(); i++)
				{
					Entity e  = getEnemyArmyEntities().get(i);
					if(this.selectedPlayerEntity != null)
					{
						if(selectedPlayerEntity.getCenter64X() + 1 == e.getCenter64X() && selectedPlayerEntity.getCenter64Y() == e.getCenter64Y())
						{
							g.drawImage(Assets.combatArrowRAnim.getImage(), selectedPlayerEntity.getPixel64X() + 220 , selectedPlayerEntity.getPixel64Y()+18, null);
						}
						else if(selectedPlayerEntity.getCenter64X() - 1 == e.getCenter64X() && selectedPlayerEntity.getCenter64Y() == e.getCenter64Y())
						{
							g.drawImage(Assets.combatArrowLAnim.getImage(), selectedPlayerEntity.getPixel64X() +164, selectedPlayerEntity.getPixel64Y()+20, null);
						}
						else if(selectedPlayerEntity.getCenter64X() == e.getCenter64X() && selectedPlayerEntity.getCenter64Y()+1 == e.getCenter64Y())
						{
							g.drawImage(Assets.combatArrowDAnim.getImage(), selectedPlayerEntity.getPixel64X()+193, selectedPlayerEntity.getPixel64Y()+ 38, null);
						}
						else if(selectedPlayerEntity.getCenter64X() == e.getCenter64X() && selectedPlayerEntity.getCenter64Y()-1 == e.getCenter64Y())
						{
							g.drawImage(Assets.combatArrowUAnim.getImage(), selectedPlayerEntity.getPixel64X()+193 , selectedPlayerEntity.getPixel64Y() - 24, null);
						}
					}
				}
			}
			else
			{
				g.drawImage(Assets.gridHorPointer, 0, y, getGame().getCanvas().getWidth(), 4, null);
				g.drawImage(Assets.gridVerPointer, x, 32, 4, getGame().getCanvas().getHeight()-32, null);
				g.drawImage(Sprite.selectedEntityPoint.getLotusImage().getImage(), x - 13, y - 13, null);	
			
			}
		}
	}

	
	
	// Grass = 0xFF00FF00
	// Flower = 0xFFFFFF00
	// Rock = 0xFF7F7F00
	public Tile getTile(int x, int y)
	{		
		System.out.println(" :" + Integer.toHexString(this.tiles[x + (y * this.width)]));
		//System.out.println(Integer.toHexString(tiles[x + (y*width)]));
		if (x < 0 || y < 0 || x >= this.width || y >= this.height) return Tile.voidTile;
		if ("ff15ff00".equals(Integer.toHexString(this.tiles[x + (y*this.width)]))){System.out.println("WEFSA"); return Tile.grass;}
		//if (Integer.toHexString(this.tiles[x + (y * this.getWidth())]).equals(Tile.col_dirt)) return Tile.grass;
		if ("ffffd800".equals(Integer.toHexString(this.tiles[x + (y*this.width)]))) return Tile.sand;
		//if (Integer.toHexString(this.tiles[x + (y * this.getWidth())]).equals(Tile.col_dirt)) return Tile.sand;
		if (Integer.toHexString(this.tiles[x + (y * this.width)]).equals(Tile.col_shrub)) return Tile.shrub;

		//if (tiles[x + y * width] == Tile.col_spawn_floor)return Tile.spawn_floor;
		//if (tiles[x + y * width] == Tile.col_spawn_grass)return Tile.spawn_grass;
		//if (tiles[x + y * width] == Tile.col_spawn_hedge)return Tile.spawn_hedge;
		//if (tiles[x + y * width] == Tile.col_spawn_wall1)return Tile.spawn_wall1;
		//if (tiles[x + y * width] == Tile.col_spawn_wall2)return Tile.spawn_wall2;
		//if (tiles[x + y * width] == Tile.col_spawn_water)return Tile.spawn_water;
			
		return Tile.voidTile;
		//return getTilesInt()[x+y];
	}

	public void setUiPaneDrawTrig(boolean uiPaneDrawTrig) 
	{
		this.uiPaneDrawTrig = uiPaneDrawTrig;
	}
	
	public void setIsTouchingDown(boolean isTouchingDown)
	{
		this.isTouchingDown = isTouchingDown;
	}
	
	public boolean isPlayerEntitySelected() 
	{
		return isPlayerEntitySelected;
	}
	
	public TileMap getBuildingTileMap() 
	{
		return this.buildingTileMap;
	}
	
	public LinkedList<Resource> getDroppedResourceEntities()
	{
		return this.droppedResourceEntities;
	}
	
	public LinkedList<ArmyUnit> getPlayerArmyEntities()
	{
		return this.playerArmyEntities;
	}	
	
	public LinkedList<Enemy> getEnemyArmyEntities()
	{
		return this.enemyArmyEntities;
	}
	
	public LinkedList<City> getPlayerCityEntities()
	{
		return this.playerCityEntities;
	}
	
	public Player getPlayer()
	{
		return player;
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}

	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}

	public TileMap getArmyMovementTileMap() 
	{
		return this.armyMovementTileMap;
	}

	public int getBarrackX() 
	{
		return this.barrackX;
	}

	public int getBarrackY() 
	{
		return this.barrackY;
	}
	
	public Entity getSelectedEntity()
	{
		return this.selectedEntity;
	}
	
	public boolean isTouchingDown() 
	{
		return isTouchingDown;
	}

	public void setSelectedPlayerEntity(ArmyUnit e)
	{
		this.selectedPlayerEntity = e;
	}
	
	public void setSelectedEntity(Entity e)
	{
		this.selectedEntity = e;
	}	
	
	public boolean isEnemyEntitySelected()
	{
		return this.isEnemyEntitySelected;
	}

	public void setIsUnitUIItem_1Selected(boolean f) 
	{
		this.isUnitUIItem_1Selected = f;
	}
	
	public boolean isUnitUIItem_1Selected()
	{
		return this.isUnitUIItem_1Selected;
	}

	public void setIsUIItem_1Selected(boolean b) 
	{
		this.isUIItem_1Selected = b;
	}
	
	public boolean isUIItem_1Selected()
	{
		return this.isUnitUIItem_1Selected;
	}

	public int getAMMW()
	{
		return this.aMMW;
	}
	
	public int getAMMH()
	{
		return this.aMMH;
	}

	public Queue<CombatEvent> getCombatEvents() 
	{
		return this.combatEvents;
	}

	public void setCombatEvents(Queue<CombatEvent> combatEvents) 
	{
		this.combatEvents = combatEvents;
	}

	public boolean isProcessingNextTurn() 
	{
		return this.isProcessingNextTurn;
	}

	public void setProcessingNextTurn(boolean isProcessingNextTurn) 
	{
		this.isProcessingNextTurn = isProcessingNextTurn;
	}

	public LinkedList<Particle> getParticleEntities() 
	{
		return particleEntities;
	}

	public void setParticleEntities(LinkedList<Particle> particleEntities) 
	{
		this.particleEntities = particleEntities;
	}

	public LinkedList<Projectile> getProjectileEntities() 
	{
		return this.projectileEntities;
	}

	public void setProjectileEntities(LinkedList<Projectile> projectileEntities)
	{
		this.projectileEntities = projectileEntities;
	}

	public boolean isSelectedDir() 
	{
		return selectedDir;
	}

	public void setSelectedDir(boolean selectedDir) 
	{
		this.selectedDir = selectedDir;
	}

	public City getSelectedCity()
	{
		return this.selectedCity;
	}

	public LotusRenderView getGame() {
		return game;
	}

	public void setGame(LotusRenderView game) {
		this.game = game;
	}

		
}
