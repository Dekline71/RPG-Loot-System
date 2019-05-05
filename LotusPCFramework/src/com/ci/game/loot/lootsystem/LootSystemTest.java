package com.ci.game.loot.lootsystem;

import static org.junit.Assert.*;

import org.junit.Test;

public class LootSystemTest {

	@Test
	public void calcGoldTest() {
		int actual;
		LootSystem.initItems();
		
		int enemyLevel = 10;
		int expectedGoldMin = 1;
		int expectedGoldMax = 10;
		actual = LootSystem.calcGold(enemyLevel);
		assertTrue(actual <= expectedGoldMax);
		assertTrue(actual >= expectedGoldMin);
	}

}
