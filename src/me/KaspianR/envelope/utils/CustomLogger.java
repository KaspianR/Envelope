package me.KaspianR.envelope.utils;

import java.util.logging.Level;

import me.KaspianR.envelope.Main;

public class CustomLogger {
	
	public static void Severe(String s) {
		
		Main.log.log(Level.SEVERE, s);
		
	}
	
	public static void Warning(String s) {
		
		Main.log.log(Level.WARNING, s);
		
	}
	
public static void Info(String s) {
		
		Main.log.log(Level.INFO, s);
		
	}
	
}
