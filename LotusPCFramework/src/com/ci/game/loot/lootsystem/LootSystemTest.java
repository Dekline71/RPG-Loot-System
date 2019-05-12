
package com.ci.game.loot.lootsystem;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

import com.ci.game.graphics.Assets;
import com.ci.game.loot.Item;

public class LootSystemTest {

	@Test
	public void calcGoldTest_Success() {
		
		LootSystem.initItems();
		
		int enemyLevel = 3;
		int expectedGoldMin = 1;
		int expectedGoldMax = 4;
		
		int actual = LootSystem.calcGold(enemyLevel);
		assertTrue(actual <= expectedGoldMax);
		assertTrue(actual >= expectedGoldMin);
		
		enemyLevel = 1;
		expectedGoldMax = 5;
		
		actual = LootSystem.calcGold(enemyLevel);
		assertTrue(actual <= expectedGoldMax);
		assertTrue(actual >= expectedGoldMin);
		
		enemyLevel = 10;
		expectedGoldMax = 1;
		
		actual = LootSystem.calcGold(enemyLevel);
		assertEquals(actual, expectedGoldMax);		
	}
	
	@Test
	public void dropLootTest_Success(){
		LootSystem.initItems();
		
		String monster = "goblin";
		
		LootSystem.dropLoot(monster);
				
		Item actual = LootSystem.tempLoot.getFirst();
		LinkedList<Item> possibleItems = new LinkedList<Item>(Arrays.asList(LootSystem.lootClass[0]));
		
		assertTrue(possibleItems.contains(actual));
		
		monster = "bandit";
		LootSystem.dropLoot(monster);
		actual = LootSystem.tempLoot.get(1);
		possibleItems = new LinkedList<Item>(Arrays.asList(LootSystem.lootClass[1]));

		assertTrue(possibleItems.contains(actual));

		monster = "ogre";
		LootSystem.dropLoot(monster);
		actual = LootSystem.tempLoot.get(2);
		possibleItems = new LinkedList<Item>(Arrays.asList(LootSystem.lootClass[2]));

		assertTrue(possibleItems.contains(actual));

		monster = "dwarf";
		LootSystem.dropLoot(monster);
		actual = LootSystem.tempLoot.get(3);
		possibleItems = new LinkedList<Item>(Arrays.asList(LootSystem.lootClass[3]));

		assertTrue(possibleItems.contains(actual));

		monster = "griffin";
		LootSystem.dropLoot(monster);
		actual = LootSystem.tempLoot.get(4);
		possibleItems = new LinkedList<Item>(Arrays.asList(LootSystem.lootClass[4]));

		assertTrue(possibleItems.contains(actual));
	}
}
