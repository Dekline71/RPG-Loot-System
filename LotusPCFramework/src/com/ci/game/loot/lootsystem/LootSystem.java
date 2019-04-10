package com.ci.game.loot.lootsystem;

import java.util.Random;

public class LootSystem 
{
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
		
		if(i <= 69) // 69% chance?
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

	public static void dropLoot(int enemyLevel) 
	{
		int gold = 0;
		gold = calcGold(enemyLevel);
		System.out.println(gold + " Gold Dropped.");

	}

	private static int calcGold(int enemyLevel) 
	{
		Random r = new Random();
		int rValue = r.nextInt(5);
		int totalGold = ((rValue + enemyLevel)/(enemyLevel*2));
		return totalGold;
	}
}