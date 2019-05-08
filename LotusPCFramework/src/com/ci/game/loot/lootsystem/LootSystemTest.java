
package com.ci.game.loot.lootsystem;
import static org.junit.Assert.*;
import java.util.LinkedList;
import org.junit.Test;
import com.ci.game.loot.Item;

public class LootSystemTest {

	@Test
	public void calcGoldTest() {
		
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
	public void getTempLootTest(){
		LootSystem.initItems();
		
		LinkedList<Item> actual = LootSystem.getTempLoot();
		LinkedList<Item> expected = new LinkedList<Item>();
		
		assertEquals(expected, actual);
	}
}
