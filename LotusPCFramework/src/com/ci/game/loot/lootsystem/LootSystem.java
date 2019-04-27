package com.ci.game.loot.lootsystem;

import java.util.LinkedList;
import java.util.Random;
import java.awt.image.BufferedImage;

import com.ci.game.entity.armyunit.ArmyUnit;
import com.ci.game.graphics.Assets;
import com.ci.game.loot.Item;

public class LootSystem 
{
	//LootClass lc;
	public static Item[] lootclass1 = new Item[11];
	public static Item[] lootclass2;
	
	public static LinkedList<Item> tempLoot = new LinkedList<Item>();

	
	public static Item shortSword;
	public static Item birchStaff;
	public static Item leatherHelmet;
	public static Item wolfSkinArmor;
	public static Item chosenLoot;
	public static Item dagger;
	public static Item greaves;
	public static Item soldiersShortSword;
	public static Item helmOfOdin;
	public static Item baneStaff;
	public static Item howlfangsFur;
	public static Item hardLeatherBelt;
	
	
	public LootSystem()
	{
		initItems();
	}
	
	public static void initItems() 
                
                // ITEM PARAMETERS
                // BufferedImage img, 
                // int lootClass, 
                // String itemName, 
                // int defense, 
                // int durability, 
                // int qLvl, 
                // int odds, 
                // char rarity
	{
		shortSword = new Item(Assets.shortSword, 0, "Short Sword", 1.0f, "1H-S", 15, 1, 2, 'c');
		leatherHelmet = new Item(Assets.leatherHelmet, 0, "Leather Helmet", 15, 15, 1, 4, 'c');
		birchStaff = new Item(Assets.staff, 0, "Birch Staff", 0.5f, "1H-S", 12, 1, 7, 'c');
		wolfSkinArmor = new Item(Assets.leatherChest, 0, "Wolf Skin Armor", 35, 35, 3, 9, 'c');
		dagger = new Item(Assets.shortSword, 0, "Dagger", 0.5f, "1H-S", 12, 1, 12, 'c');
		greaves = new Item(Assets.greaves, 0, "Greaves", 40, 40, 3, 14, 'c');
		hardLeatherBelt = new Item(Assets.leatherBelt, 0, "Hard Leather Belt", 20, 20, 3, 16, 'c');
		soldiersShortSword = new Item(Assets.shortSword, 0, "Soldiers Short Sword", 1.0f, "1H-S", 15, 1, 18, 'u');
		helmOfOdin = new Item(Assets.leatherHelmet, 0, "Helm of Odin", 58, 58, 3, 19, 'r');
		baneStaff = new Item(Assets.staff, 0, "Bane Staff", 1.0f, "1H-S", 15, 1, 20, 'r');
		howlfangsFur = new Item(Assets.leatherHelmet, 0, "Howlfangs Fur", 58, 58, 3, 21, 'r');


		
		lootclass1[0] = shortSword;
		lootclass1[1] = leatherHelmet;
		lootclass1[2] = birchStaff;
		lootclass1[3] = wolfSkinArmor;
		lootclass1[4] = dagger;
		lootclass1[5] = greaves;
		lootclass1[6] = hardLeatherBelt;
		lootclass1[7] = soldiersShortSword;
		lootclass1[8] = helmOfOdin;
		lootclass1[9] = baneStaff;
		lootclass1[10] = howlfangsFur;

	}

	/**********************************
	 * Calculates the chance of no loot 
	 * being dropped.
	 * @return boolean
	 **********************************/
	public static boolean calcNoDrop()
	{
		boolean isNoDrop = false;
		int noDropValue = 100;
		double probSum;
		Random r = new Random();
		
		int i = r.nextInt(noDropValue);
		
		if(i <= 59) // 59% drop rate
		{
			//drop
			return true;
		}
		else
		{
			//no drop
			return false;
		}
		//int newNoDrop = (int) (probSum/(1/(noDropValue/(noDropValue+probSum))-1));
		
	}
	
	/*****************************************
	 * Drop Item based on enemy level.
	 * <p>
	 * int enemyLevel: level of enemy
	 ******************************************/
	public static void dropLoot(int enemyLevel) 
	{

		// //Pick LootClass from list based on monster level
		//extractLC(enemyLevel);
		
		Random r = new Random();
		if(enemyLevel <= 3) // Pick LootClass 1
		{
			int amtItems = lootclass1.length;
			
			/*for(int i = 0; i < amtItems; i++)
			{
				probSum += lootclass1[i].getChanceToDrop();
			}*/
			
			// get random value between, 0 to the weight 
			// of the last Item in the loot class
			int roll = r.nextInt(lootclass1[amtItems-1].getChanceToDrop());
			System.out.println("rolled: " + roll);
			
			/***************************************************
			 * Loop for the length of Items in said loot class.
			 * For each loop starting from [0], compare roll value
			 * to each previous Items weighted range. 
			 *****************************************************/
			for(int i = 0; i < amtItems; i++)
			{
				// Check first items Drop value & compare to roll value
				if(i == 0 && roll <= lootclass1[i].getChanceToDrop()) 
				{
					chosenLoot = lootclass1[i];
					getTempLoot().add(lootclass1[i]);
					showItemToConsole(i);

				}
				// Compare previous Items Drop value to current
				else if(i != 0 && roll > lootclass1[i-1].getChanceToDrop() && roll <= lootclass1[i].getChanceToDrop())
				{
					chosenLoot = lootclass1[i];
					getTempLoot().add(lootclass1[i]);
					showItemToConsole(i);

				}				
			}			
		}
		else if(enemyLevel >= 6) // Pick Loot class 2
		{
			int i = r.nextInt(1);
			switch(i)
			{
			case 0:
				// use loot class 0
				break;
			case 1:
			 // use loot class 1
				break;
			}
		}
	}
	
	/********************************************
	 * Display Contents of Loot Drop to Console
	 * <p>
	 * int i: lootclass1[] index value
	 *******************************************/
	private static void showItemToConsole(int i) 
	{
		System.out.println("\n" + lootclass1[i].getName());
		if(lootclass1[i].getItemType() == Item.ItemType.Weapon)
		{
			System.out.println(lootclass1[i].getWeaponType());
			System.out.println("Damage: " + lootclass1[i].getDamage() + " damage per second");
		}
		else if(lootclass1[i].getItemType() == Item.ItemType.Armor)
		{
			System.out.println("Armor: " + lootclass1[i].getArmor());
		}
		System.out.println("Durability: " + lootclass1[i].getDurability() + "/" + lootclass1[i].getDurability());
		System.out.println("");
	}

	private static void extractLC(int enemyLevel) 
	{

		
	}

	public static int calcGold(int enemyLevel) 
	{
		Random r = new Random();
		int rValue = r.nextInt(5);
		int totalGold = (((rValue + enemyLevel)/(enemyLevel*2))+1);
		return totalGold;
	}

	public static LinkedList<Item> getTempLoot() {
		return tempLoot;
	}

	public void setTempLoot(LinkedList<Item> tempLoot) {
		this.tempLoot = tempLoot;
	}

	public static boolean calcGoldDrop() 
	{
		boolean isNoDrop = false;
		int noDropValue = 100;
		double probSum;
		Random r = new Random();
		
		int i = r.nextInt(noDropValue);
		
		if(i <= 76) // 76% drop rate
		{
			//drop
			return true;
		}
		else
		{
			//no drop
			return false;
		}
	}
}
