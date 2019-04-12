package com.ci.game.loot;

import java.awt.image.BufferedImage;

public class Item 
{
	public enum ItemType {Armor, Weapon}
	
	enum ItemAttribute {SpellDamage, WeaponDamage, 
		FireResistence, Strength, Health}
	
	// Armor attributes
	private int armor;
	
	// Shared item attributes
	private int durability;
	private int qLvl; // required player lvl for equip
	private String name; // name of item
	private ItemType itemType; // type of item
	//ItemAttribute attribute1Type;
	//ItemAttribute attribute2Type;
	private int lootClass; // loot class index;
	private int chanceToDrop; // chance to drop in lootclass 
	private char rarity; // rarity of item
		
	// Weapon attributes
	private float damage;
	private String weaponType;

	public Item()
	{
		
	}
	
	// Armor item constructor
	public Item (BufferedImage img, int lootClass, String itemName, int defense, int durability, int qLvl, int odds, char rarity)
	{
		this.lootClass = lootClass;
		setName(itemName);
		this.setArmor(defense);
		this.setDurability(durability);
		this.qLvl = qLvl;
		this.chanceToDrop = odds;
		setItemType(ItemType.Armor);
		
	}
	
	//Weapon Item Constructor
	public Item (BufferedImage img, int lootClass, String itemName, float damage, String wType, int durability, int qLvl, int odds, char rarity)
	{
		this.lootClass = lootClass;
		setName(itemName);
		this.setDamage(damage);
		setWeaponType(wType);
		this.setDurability(durability);
		this.qLvl = qLvl;
		this.chanceToDrop = odds;
		setItemType(ItemType.Weapon);
	}

	public int getChanceToDrop() {
		return chanceToDrop;
	}

	public void setChanceToDrop(int chanceToDrop) {
		this.chanceToDrop = chanceToDrop;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWeaponType() {
		return weaponType;
	}

	public void setWeaponType(String weaponType) {
		this.weaponType = weaponType;
	}

	public float getDamage() {
		return damage;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public int getDurability() {
		return durability;
	}

	public void setDurability(int durability) {
		this.durability = durability;
	}
	

}
