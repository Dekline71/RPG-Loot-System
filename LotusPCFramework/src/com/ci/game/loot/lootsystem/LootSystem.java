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
	public static Item[] goblinloot = new Item[11];
	public static Item[] banditloot = new Item[10];
        public static Item[] ogreloot = new Item[10];
        public static Item[] dwarfloot = new Item[12];
        public static Item[] griffinloot = new Item[6];
        // lootClass is a 2D array for generating loot based on Monster Level (mLvl), which determines which lootClass is used.
        public static Item[][] lootClass = new Item[][]{goblinloot, banditloot, ogreloot, dwarfloot, griffinloot};
        
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
	
	public static Item greatsword;
	public static Item axe;
	public static Item crackedLeatherLeggings;
	public static Item chainmailGloves;
	public static Item mace;
	public static Item chainmailShirt;
	public static Item vanquishersGreatsword;
	public static Item axeOfFechmar;
	public static Item gauntletsOfGlory;
	public static Item neanderthalMace;
        
        public static Item scimitar;
	public static Item ringmailShirt;
	public static Item longsword;
	public static Item breastplate;
	public static Item greatclub;
	public static Item plateVambrace;
	public static Item skycutter;
	public static Item shardOfHate;
	public static Item templarsArmor;
	public static Item skornsClub;
        
        public static Item warhammer;
	public static Item greataxe;
	public static Item plateArmorSuit;
	public static Item helmet;
	public static Item crossbow;
	public static Item shield;
	public static Item thorsHammer;
	public static Item axeOfFendor;
	public static Item sacredShroud;
	public static Item casqueOfCarcasonne;
        public static Item goldenCrossbow;
	public static Item knightsShield;
        
        public static Item bitOfCloth;
	public static Item bone;
	public static Item dragonEgg;
	public static Item eagleFeather;
	public static Item pocketSand;
	public static Item waterFlask;

        
	public LootSystem()
	{
		initItems();
	}
	
	public static void initItems() 
                
                // ITEM PARAMETERS --  * denotes monsterLootClass parameter
                // BufferedImage img, *
                // int lootClass, *
                // String itemName, *
                // int defense OR int damage
                // String wType
                // int durability, 
                // int qLvl, *
                // int odds, *
                // char rarity *
	{
                bitOfCloth = new Item(Assets.bitOfCloth, 5, "Bit of cloth", 1, 2, 'c');
                bone = new Item(Assets.bone, 5, "A bone", 1, 4, 'c');
                dragonEgg = new Item(Assets.dragonEgg, 5, "A dragon egg", 1, 6, 'c');
                eagleFeather = new Item(Assets.eagleFeather, 5, "An eagle feather", 1, 8, 'c');
                pocketSand = new Item(Assets.pocketSand, 5, "Pocket Sand!", 1, 10, 'c');
                waterFlask = new Item(Assets.waterFlask, 5, "A flask of water", 1, 12, 'c');
            
		shortSword = new Item(Assets.shortSword, 0, "Short Sword", 1.0f, "1H-S", 15, 1, 2, 'c');
		leatherHelmet = new Item(Assets.leatherHelmet, 0, "Leather Helmet", 15, 15, 1, 4, 'c');
		birchStaff = new Item(Assets.staff, 0, "Birch Staff", 0.5f, "1H-B", 12, 1, 7, 'c');
		wolfSkinArmor = new Item(Assets.leatherChest, 0, "Wolf Skin Armor", 35, 35, 3, 9, 'c');
		dagger = new Item(Assets.shortSword, 0, "Dagger", 0.5f, "1H-S", 12, 1, 12, 'c');
		greaves = new Item(Assets.greaves, 0, "Greaves", 40, 40, 3, 14, 'c');
		hardLeatherBelt = new Item(Assets.leatherBelt, 0, "Hard Leather Belt", 20, 20, 3, 16, 'c');
		soldiersShortSword = new Item(Assets.shortSword, 0, "Soldiers Short Sword", 1.0f, "1H-S", 15, 1, 18, 'u');
		helmOfOdin = new Item(Assets.leatherHelmet, 0, "Helm of Odin", 58, 58, 3, 19, 'r');
		baneStaff = new Item(Assets.staff, 0, "Bane Staff", 1.0f, "1H-B", 15, 1, 20, 'r');
		howlfangsFur = new Item(Assets.leatherHelmet, 0, "Howlfangs Fur", 58, 58, 3, 21, 'l');

                greatsword = new Item(Assets.greatsword, 1, "Greatsword", 2.0f, "2H-S", 15, 1, 2, 'c');
		axe = new Item(Assets.axe, 1, "Axe", 1.0f, "1H-S", 15, 1, 4, 'c');
		crackedLeatherLeggings = new Item(Assets.leatherLeggings, 1, "Cracked Leather Leggings", 45, 15, 1, 7, 'c');
		chainmailGloves = new Item(Assets.chainmailGloves, 1, "Chainmail Gloves", 20, 20, 3, 9, 'c');
		mace = new Item(Assets.mace, 1, "Mace", 1.0f, "1H-S", 15, 1, 12, 'c');
		chainmailShirt = new Item(Assets.chainmailShirt, 1, "Chainmail Shirt", 50, 50, 3, 14, 'c');
		vanquishersGreatsword = new Item(Assets.greatsword, 1, "Vanquisher's Greatsword", 3.0f, "2H-S", 20, 3, 16, 'l');
		axeOfFechmar = new Item(Assets.axe, 1, "Axe of Fechmar", 2.0f, "1H-S", 20, 1, 18, 'r');
		gauntletsOfGlory = new Item(Assets.chainmailGloves, 1, "Gauntlets of Glory", 45, 45, 3, 19, 'r');
		neanderthalMace = new Item(Assets.mace, 1, "Neanderthal Mace", 2.0f, "1H-B", 15, 3, 20, 'u');
		
                scimitar = new Item(Assets.scimitar, 2, "Scimitar", 1.5f, "1H-S", 15, 1, 2, 'c');
		ringmailShirt = new Item(Assets.ringmailShirt, 2, "Ringmail Shirt", 20, 20, 1, 4, 'c');
		longsword = new Item(Assets.longsword, 2, "Longsword", 1.0f, "1H-S", 15, 1, 7, 'c');
		breastplate = new Item(Assets.breastplate, 2, "Breastplate", 35, 35, 3, 9, 'c');
		greatclub = new Item(Assets.greatclub, 2, "Greatclub", 2.5f, "2H-B", 20, 1, 12, 'c');
		plateVambrace = new Item(Assets.plateVambrace, 2, "Plate Vambrace", 45, 45, 3, 14, 'c');
		skycutter = new Item(Assets.scimitar, 2, "Skycutter", 2.5f, "1H-S", 30, 3, 16, 'u');
		shardOfHate = new Item(Assets.longsword, 2, "Shard of Hate", 3.0f, "1H-S", 15, 1, 18, 'u');
		templarsArmor = new Item(Assets.breastplate, 2, "Templar's Armor", 62, 62, 3, 19, 'r');
		skornsClub = new Item(Assets.greatclub, 2, "Skorn's Club", 4.0f, "2H-B", 30, 1, 20, 'l');
                
                warhammer = new Item(Assets.warhammer, 3, "Warhammer", 5.0f, "2H-B", 30, 1, 2, 'c');
		greataxe = new Item(Assets.greataxe, 3, "Greataxe", 5.0f, "2H-S", 30, 1, 4, 'c');
		plateArmorSuit = new Item(Assets.plateArmorSuit, 3, "Plate Armor Suit", 75, 12, 1, 7, 'c');
		helmet = new Item(Assets.helmet, 3, "Helmet", 50, 50, 3, 9, 'c');
		crossbow = new Item(Assets.crossbow, 3, "Crossbow", 3.5f, "2H-R", 25, 1, 12, 'c');
		shield = new Item(Assets.shield, 3, "Shield", 40, 40, 3, 14, 'c');
		thorsHammer = new Item(Assets.warhammer, 3, "Thor's Hammer", 7.5f, "1H-B", 99, 3, 16, 'l');
		axeOfFendor = new Item(Assets.greataxe, 3, "Axe of Fendor", 7.0f, "2H-S", 45, 1, 18, 'l');
		sacredShroud = new Item(Assets.plateArmorSuit, 3, "Sacred Shroud", 100, 75, 3, 19, 'u');
		casqueOfCarcasonne = new Item(Assets.helmet, 3, "Casque of Carcassone", 65, 50, 1, 20, 'r');
                goldenCrossbow = new Item(Assets.crossbow, 3, "Golden Crossbow", 5.5f, "2H-R",50, 3, 21, 'r');
		knightsShield = new Item(Assets.shield, 3, "Knight's Shield", 58, 58, 3, 22, 'u');
                
                // mLvl 1-3
		goblinloot[0] = shortSword;
		goblinloot[1] = leatherHelmet;
		goblinloot[2] = birchStaff;
		goblinloot[3] = wolfSkinArmor;
		goblinloot[4] = dagger;
		goblinloot[5] = greaves;
		goblinloot[6] = hardLeatherBelt;
		goblinloot[7] = soldiersShortSword;
		goblinloot[8] = helmOfOdin;
		goblinloot[9] = baneStaff;
		goblinloot[10] = howlfangsFur;
                
                // mLvl 4-6
                banditloot[0] = greatsword;
		banditloot[1] = axe;
		banditloot[2] = crackedLeatherLeggings;
		banditloot[3] = chainmailGloves;
		banditloot[4] = mace;
		banditloot[5] = chainmailShirt;
		banditloot[6] = vanquishersGreatsword;
		banditloot[7] = axeOfFechmar;
		banditloot[8] = gauntletsOfGlory;
		banditloot[9] = neanderthalMace;
                
                // mLvl 7-9
                ogreloot[0] = scimitar;
		ogreloot[1] = ringmailShirt;
		ogreloot[2] = longsword;
		ogreloot[3] = breastplate;
		ogreloot[4] = greatclub;
		ogreloot[5] = plateVambrace;
		ogreloot[6] = skycutter;
		ogreloot[7] = shardOfHate;
		ogreloot[8] = templarsArmor;
		ogreloot[9] = skornsClub;
                
                // mLvl 10-12
                dwarfloot[0] = warhammer;
		dwarfloot[1] = greataxe;
		dwarfloot[2] = plateArmorSuit;
		dwarfloot[3] = helmet;
		dwarfloot[4] = crossbow;
		dwarfloot[5] = shield;
		dwarfloot[6] = thorsHammer;
		dwarfloot[7] = axeOfFendor;
		dwarfloot[8] = sacredShroud;
		dwarfloot[9] = casqueOfCarcasonne;
		dwarfloot[10] = goldenCrossbow;
                dwarfloot[11] = knightsShield;
                
                // mLvl 13-15 (for now...)
                griffinloot[0] = bitOfCloth;
                griffinloot[1] = bone;
                griffinloot[2] = dragonEgg;
                griffinloot[3] = eagleFeather;
                griffinloot[4] = pocketSand;
                griffinloot[5] = waterFlask;
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
	public static void dropLoot(String monster) 
	{
                //int mLvl = enemyLevel;
                int m = 0;
		// //Pick LootClass from list based on monster level
		//extractLC(enemyLevel);
		if(monster=="goblin")
                    {
                        m=0;
                    }
                    else if(monster=="bandit")
                    {
                        m=1;
                    }
                    else if(monster=="ogre")
                    {
                        m=2;
                    }
                    else if(monster=="dwarf")
                    {
                        m=3;
                    }
                    else if (monster=="griffin")
                    {
                        m=4;
                    }
                
		Random r = new Random();
                
			int amtItems = lootClass[m].length;
			
			/*for(int i = 0; i < amtItems; i++)
			{
				probSum += lootclass1[i].getChanceToDrop();
			}*/
			
			// get random value between, 0 to the weight 
			// of the last Item in the loot class
			int roll = r.nextInt(lootClass[m][amtItems-1].getChanceToDrop());
			System.out.println("rolled: " + roll);
			
			/***************************************************
			 * Loop for the length of Items in said loot class.
			 * For each loop starting from [0], compare roll value
			 * to each previous Items weighted range. 
			 *****************************************************/
			for(int i = 0; i < amtItems; i++)
			{
                                
				// Check first items Drop value & compare to roll value
				if(i == 0 && roll <= lootClass[m][i].getChanceToDrop()) 
				{
					chosenLoot = lootClass[m][i];
					getTempLoot().add(lootClass[m][i]);
					showItemToConsole(m,i);

				}
				// Compare previous Items Drop value to current
				else if(i != 0 && roll > lootClass[m][i-1].getChanceToDrop() && roll <= lootClass[m][i].getChanceToDrop())
				{
					chosenLoot = lootClass[m][i];
					getTempLoot().add(lootClass[m][i]);
					showItemToConsole(m,i);

				}				
			}			
	}
	
	/********************************************
	 * Display Contents of Loot Drop to Console
	 * <p>
	 * int i: lootclass1[] index value
	 *******************************************/
	private static void showItemToConsole(int mLvl, int i) 
	{
            int m = mLvl;
		System.out.println("\n" + lootClass[m][i].getName());
		if(lootClass[m][i].getItemType() == Item.ItemType.Weapon)
		{
			System.out.println(lootClass[m][i].getWeaponType());
			System.out.println("Damage: " + lootClass[m][i].getDamage() + " damage per second");
		}
		else if(lootClass[m][i].getItemType() == Item.ItemType.Armor)
		{
			System.out.println("Armor: " + lootClass[m][i].getArmor());
		}
		System.out.println("Durability: " + lootClass[m][i].getDurability() + "/" + lootClass[m][i].getDurability());
		System.out.println("");
	}

	private static void extractLC(int enemyLevel) 
	{

		
	}

	public static int calcGold(int enemyLevel) 
	{
		Random r = new Random();
		int rValue = r.nextInt(10);
		int totalGold = ((rValue + enemyLevel)/((enemyLevel)+1));
                if(totalGold == 0)
                {
                    totalGold = totalGold + 1;
                }
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
