package com.ci.game.entity;

import java.awt.Toolkit;

import com.ci.game.entity.projectile.Projectile;
import com.ci.game.entity.projectile.WizardProjectile;
import com.ci.game.graphics.Camera;
import com.ci.game.graphics.Sprite;
import com.ci.game.loot.Item;
import com.ci.game.loot.lootsystem.LootSystem;
import com.ci.lotusFramework.implementation.input.Keyboard;
import com.ci.lotusFramework.implementation.input.Mouse;

public class Player extends Entity
{	
	private int gold;// players total gold
	private int food = 0;// players total gold
	//private static int xp;// players total experience gained
	
	private boolean barrackUIPaneActive;
	private boolean isBuildMode;
	private boolean isBuildingSelected;
	private boolean isMilitarySelected;
	private boolean isArcherUnitSelected;
	private boolean isSpearmanUnitSelected;
	private boolean isPanelOn;
	
	private Keyboard input;
	private Mouse mouse;
	private Sprite sprite;
	public int xScroll, yScroll;
	
	private int anim = 0;
	private boolean walking = false;
	
	private int fireRate = 0;
	private int x;
	private int y;
	private int direction;
	private boolean uiItem_1;
	private boolean uiItem_2;
	protected boolean isCombatMode;
	
	private boolean isTownManagerMode = false;
	private int curTurn;
	private int totalTurns;
	private int prevTurn;
	private String tribe;
	
	private boolean isLootDropped = false;

	
	public Player(int x, int y) 
	{
		super(x, y, EntityType.Void);
		this.setBarrackUIPaneActive(false);
		setBuildMode(false);
		setCombatMode(false);
		setBuildingSelected(false);
		setMilitarySelected(false);
	}

	public void update()
	{
		//if(input.checkForESC())
		//{
		//	System.exit(0);
		//}
		checkForSlainEnemy();

		
		//if (fireRate > 0){fireRate--;}
		//int xPos = 0, yPos = 0;// detect player position/movement
		//if (anim < 7500){anim++;}
	//	if (input.up){ yPos--;}
	//	if (input.down){ yPos++;}
	//	if (input.left){ xPos--;}
	//	if (input.right){ xPos++;}
	
		
		//if (xPos != 0 || yPos != 0)
		//{
			  //move(xPos, yPos);
			//walking = true;
		//}
		//else
		//{
			//walking = false;
		//}

		
		
	}
	
	public void checkForSlainEnemy() 
	{
		// check for slain enemy
		
		boolean lootDrop, goldDrop;
		LootSystem.tempLoot.clear();

		System.out.println("Loot Box Clicked");
		int mLvl;
		//mLvl = generateSlainMonster(); //** TBD **
		mLvl = 1; // Testing level 1 enemy/monster
		
		lootDrop = LootSystem.calcNoDrop();
		//System.out.println(lootDrop);
		goldDrop = LootSystem.calcGoldDrop();

		if(goldDrop)
		{
			setLootDropped(true);
			System.out.println("$$ Loot Drop!!");
			int gold = LootSystem.calcGold(mLvl); // calc gold based mLvl
			if(gold > 0)
			{
				Item g = new Item(gold);
				LootSystem.getTempLoot().add(new Item(g.getGold()));
			}
			System.out.println(gold + " Gold Dropped.");
		}
		
		if(lootDrop) // if there is a drop
		{
			LootSystem.dropLoot(mLvl);
		}
		else
		{
			
		}
			
	}

	private void clear()
	{
		for(int i = 0; i < level.getProjectiles().size(); i++)
		{
			Projectile p = level.getProjectiles().get(i);
			if(p.isRemoved()){level.getProjectiles().remove(i);}
		}
	}
	
	private void updateShooting()
	{
		if (Mouse.getButton() == 1 && fireRate <= 0)
		{
			//double dx = Mouse.getX() - Toolkit.getDefaultToolkit().getScreenSize().width / 2;
			//double dy = Mouse.getY() - Toolkit.getDefaultToolkit().getScreenSize().height / 2;// Game.getWindowHeight()
			//double dir = Math.atan2(dy, dx);
			//shoot(x, y, dir);
			fireRate = WizardProjectile.FIRE_RATE;
		}
	}
	
	public void render(Camera screen)
	{
		if(direction == 0)
		{
			sprite = Sprite.player_forward;
			if (walking)
			{
				if (anim % 20 > 10)
				{
					sprite = Sprite.player_forward_1;
				}
				else
				{
					sprite = Sprite.player_forward_2;
				}
			}
		}
		
		if(direction == 1)
		{
			sprite = Sprite.player_right;
			if (walking)
			{
				if (anim % 20 > 10)
				{
					sprite = Sprite.player_right_1;
				}
				else
				{
					sprite = Sprite.player_right_2;
				}
			}
		}
			
		if(direction == 2)
		{
			sprite = Sprite.player_back;
			if (walking)
			{
				if (anim % 20 > 10)
				{
					sprite = Sprite.player_back_1;
				}
				else
				{
					sprite = Sprite.player_back_2;
				}
			}
		}
		
		if(direction == 3)
		{ 
			sprite = Sprite.player_left;
			if (walking)
			{
				if (anim % 20 > 10)
				{
					sprite = Sprite.player_left_1;
				}
				else
				{
					sprite = Sprite.player_left_2;
				}
			}
		}
		
		screen.renderPlayer(x - 16 , y - 16, sprite);
	
	}
	
	public boolean solid()
	{
		return true;
	}

	public boolean isPanelOn() 
	{
		return isPanelOn;
	}

	public void setPanelOn(boolean isPanelOn)
	{
		this.isPanelOn = isPanelOn;
	}

	public boolean isMilitarySelected()
	{
		return isMilitarySelected;
	}

	public void setMilitarySelected(boolean isMilitarySelected)
	{
		this.isMilitarySelected = isMilitarySelected;
	}

	public boolean isBuildingSelected()
	{
		return isBuildingSelected;
	}

	public void setBuildingSelected(boolean isBuildingSelected) 
	{
		this.isBuildingSelected = isBuildingSelected;
	}

	public boolean isBuildMode() 
	{
		return this.isBuildMode;
	}

	public void setBuildMode(boolean isBuildMode)
	{
		this.isBuildMode = isBuildMode;
	}

	public boolean isBarrackUIPaneActive()
	{
		return barrackUIPaneActive;
	}

	public void setBarrackUIPaneActive(boolean barrackUIPaneActive) 
	{
		this.barrackUIPaneActive = barrackUIPaneActive;
	}

	public void setUiItem_1(boolean b) 
	{
		this.uiItem_1 = b;
	}
	
	public void setUiItem_2(boolean b) 
	{
		this.uiItem_2 = b;
	}

	public boolean isUiItem_1() 
	{
		return this.uiItem_1;
	}
	
	public boolean isUiItem_2() 
	{
		return this.uiItem_2;
	}

	public void setCombatMode(boolean b) 
	{
		this.isCombatMode = b;
	}
	
	public boolean isCombatMode()
	{
		return this.isCombatMode;
	}

	public boolean isTownManagerMode() 
	{
		return isTownManagerMode;
	}

	public void setTownManagerMode(boolean isTownManagerMode) 
	{
		this.isTownManagerMode = isTownManagerMode;
	}

	public boolean isArcherUnitSelected() 
	{
		return isArcherUnitSelected;
	}

	public void setArcherUnitSelected(boolean isArcherUnitSelected) 
	{
		this.isArcherUnitSelected = isArcherUnitSelected;
	}

	public boolean isSpearmanUnitSelected() 
	{
		return isSpearmanUnitSelected;
	}

	public void setSpearmanUnitSelected(boolean isSpearmanUnitSelected) 
	{
		this.isSpearmanUnitSelected = isSpearmanUnitSelected;
	}

	public int getGold() 
	{
		return gold;
	}

	public void setGold(int gold) 
	{
		this.gold = gold;
	}

	public int getFood() 
	{
		return food;
	}

	public void setFood(int food) 
	{
		this.food = food;
	}

	public int getCurTurn() 
	{
		return curTurn;
	}

	public void setCurTurn(int curTurn) 
	{
		this.curTurn = curTurn;
	}

	public int getTotalTurns() 
	{
		return totalTurns;
	}

	public void setTotalTurns(int totalTurns) 
	{
		this.totalTurns = totalTurns;
	}

	public int getPrevTurn() 
	{
		return prevTurn;
	}

	public void setPrevTurn(int prevTurn) 
	{
		this.prevTurn = prevTurn;
	}
	
	public void setTribe(String s)
	{
		this.tribe = s;
	}
	
	public String getTribe()
	{
		return this.tribe;
	}

	public boolean isLootDropped() {
		return isLootDropped;
	}

	public void setLootDropped(boolean isLootDropped) {
		this.isLootDropped = isLootDropped;
	}
}
