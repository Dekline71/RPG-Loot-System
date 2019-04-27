package com.ci.audio;

import java.applet.Applet;
import java.applet.AudioClip;

public class Audio 
{
	public static final Audio loot = new Audio("select.wav");
        public static final Audio nothing = new Audio("/audio/ui/nothing.wav");
	private AudioClip clip;
	
	public Audio(String name)
	{
		try 
		{
			clip = Applet.newAudioClip(Audio.class.getResource(name));
		} catch (Throwable e) 
		{
			e.printStackTrace();
		}
	}
	
	public void play() 
	{
		try 
		{
			new Thread() 
			{
				public void run()
				{
					clip.play();
				}
			}.start();
		} catch (Throwable e) 
		{
			e.printStackTrace();
		}
	}
	
	
}
