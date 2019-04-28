package com.ci.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.ci.game.graphics.Assets;
import com.ci.game.loot.lootsystem.LootSystem;
import com.ci.lotusFramework.Screen;
import com.ci.lotusFramework.implementation.LotusRenderView;

public class LoadingScreen extends Screen
{
	private String path;
	public LoadingScreen(LotusRenderView game) 
	{
		super(game);
	}
	
	long timer = System.currentTimeMillis();

	@Override
	public void update(double deltaTime)
	{
		LootSystem.initItems();
		System.out.println("Loading!!!");

		BufferedImage img;
		this.path =  "res/textures/misc/splash.jpg";		
		
		try 
		{
			img = ImageIO.read(this.getClass().getResource("/ui/l_StonePanelShort.png"));
			Assets.resPanel = img;
			
			img = ImageIO.read(this.getClass().getResource("/textures/misc/splash2.jpg"));
			Assets.splash = img;
			
			img = ImageIO.read(this.getClass().getResource("/items/weapons/shortsword-ico.png"));
			Assets.shortSword = img;	
			
			img = ImageIO.read(this.getClass().getResource("/items/weapons/staff-ico.png"));
			Assets.staff = img;	
			
			img = ImageIO.read(this.getClass().getResource("/items/armor/leatherhelmet-ico.png"));
			Assets.leatherHelmet = img;	
			
			img = ImageIO.read(this.getClass().getResource("/items/armor/greaves-ico.png"));
			Assets.greaves = img;
			
			img = ImageIO.read(this.getClass().getResource("/items/armor/leather-chest-ico.png"));
			Assets.leatherChest = img;
			
			img = ImageIO.read(this.getClass().getResource("/items/armor/leather-belt-ico.png"));
			Assets.leatherBelt = img;
			
			img = ImageIO.read(this.getClass().getResource("/items/gold-coin-ico.png"));
			Assets.goldCoin = img;
			
			img = ImageIO.read(this.getClass().getResource("/items/loot-chest-ico.png"));
			Assets.lootChest = img;
                        
                        img = ImageIO.read(this.getClass().getResource("/items/nothing-ico.png"));
			Assets.nothing = img;
                        
			
			img = ImageIO.read(this.getClass().getResource("/items/monster/bit-of-cloth-ico.png"));
			Assets.bitOfCloth = img;
			
			img = ImageIO.read(this.getClass().getResource("/items/monster/bone-ico.png"));
			Assets.bone = img;
			
			img = ImageIO.read(this.getClass().getResource("/items/monster/dragon-egg-ico.png"));
			Assets.dragonEgg = img;	
			
			img = ImageIO.read(this.getClass().getResource("/items/monster/eagle-feather-ico.png"));
			Assets.eagleFeather = img;	
			
			img = ImageIO.read(this.getClass().getResource("/items/monster/pocket-sand-ico.png"));
			Assets.pocketSand = img;	
			
			img = ImageIO.read(this.getClass().getResource("/items/monster/water-flask-ico.png"));
			Assets.waterFlask = img;
			
			img = ImageIO.read(this.getClass().getResource("/items/armor/breastplate-ico.png"));
			Assets.breastplate = img;
			
			img = ImageIO.read(this.getClass().getResource("/items/armor/chainmail-gloves-ico.png"));
			Assets.chainmailGloves = img;
			
			img = ImageIO.read(this.getClass().getResource("/items/armor/chainmail-shirt-ico.png"));
			Assets.chainmailShirt = img;
			
			img = ImageIO.read(this.getClass().getResource("/items/armor/helmet-ico.png"));
			Assets.helmet = img;
                        
                        img = ImageIO.read(this.getClass().getResource("/items/armor/leather-leggings-ico.png"));
			Assets.leatherLeggings = img;
                        
                        img = ImageIO.read(this.getClass().getResource("/items/armor/plate-armor-suit-ico.png"));
			Assets.plateArmorSuit = img;
			
			img = ImageIO.read(this.getClass().getResource("/items/armor/plate-vambrace-ico.png"));
			Assets.plateVambrace = img;
			
			img = ImageIO.read(this.getClass().getResource("/items/armor/ringmail-shirt-ico.png"));
			Assets.ringmailShirt = img;	
			
			img = ImageIO.read(this.getClass().getResource("/items/armor/shield-ico.png"));
			Assets.shield = img;	
			
			img = ImageIO.read(this.getClass().getResource("/items/weapons/axe-ico.png"));
			Assets.axe = img;	
			
			img = ImageIO.read(this.getClass().getResource("/items/weapons/crossbow-ico.png"));
			Assets.crossbow = img;
			
			img = ImageIO.read(this.getClass().getResource("/items/weapons/greataxe-ico.png"));
			Assets.greataxe = img;
			
			img = ImageIO.read(this.getClass().getResource("/items/weapons/greatclub-ico.png"));
			Assets.greatclub = img;
			
			img = ImageIO.read(this.getClass().getResource("/items/weapons/greatsword-ico.png"));
			Assets.greatsword = img;
			
			img = ImageIO.read(this.getClass().getResource("/items/weapons/longsword-ico.png"));
			Assets.longsword = img;
                        
                        img = ImageIO.read(this.getClass().getResource("/items/weapons/mace-ico.png"));
			Assets.mace = img;

                        img = ImageIO.read(this.getClass().getResource("/items/weapons/scimitar-ico.png"));
			Assets.scimitar = img;
			
			img = ImageIO.read(this.getClass().getResource("/items/weapons/warhammer-ico.png"));
			Assets.warhammer = img;

		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		if(System.currentTimeMillis() - timer > 500)
		{
			//timer += 10000;
			//System.out.println("Bam!!");
			game.setScreen(new RPGLootScreen(game));		
		}		
	}

	@Override
	public void paint(double deltaTime) 
	{
		Graphics g = this.game.getCanvas().getBufferStrategy().getDrawGraphics();
		//screen.setGraphics(g);
		if(game.getFrame().getWidth() > 800)
		{
			g.drawImage(Assets.splash, game.getFrame().getWidth() / 10, game.getCanvas().getHeight() / 10, null);		
			g.setColor(Color.WHITE);
			g.drawString("Please Wait while assets are being fetched & loaded ...", game.getCanvas().getWidth() / 3, (int)(game.getCanvas().getHeight() / 1.5));
		}
		else
		{
			g.setColor(Color.WHITE);
			g.drawString("Please Wait while assets are being fetched & loaded ...", game.getCanvas().getWidth() / 3, (int)(game.getCanvas().getHeight() / 1.5));

		}	
	}

	@Override
	public void pause() 
	{
		
	}

	@Override
	public void resume() 
	{
		
	}

	@Override
	public void dispose() 
	{
		
	}

	@Override
	public void backButton()
	{
		
	}

	@Override
	public void update12EverySec() 
	{
		
	}
}