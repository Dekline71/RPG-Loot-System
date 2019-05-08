package com.ci.game.entity;

public class Monster {

	private String[] monsterType = {"goblin","bandit","ogre","dwarf","griffin"};
	private String monster;
	
	//constructor
	public Monster (int i) {
		monster = monsterType[i];
	}
	
	public String getSlainMonster() {
		return monster;
	}
	
}
