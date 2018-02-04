package org.insightlab.botMother.service;

import org.insightlab.botMother.bot.Bot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class BotService {
	private static TelegramBotsApi bots;
	
	public static void start(){
		ApiContextInitializer.init();
		bots = new TelegramBotsApi();
	}
	
	public static void addBot(Bot bot){
		try {
			bots.registerBot(bot);
		} catch (TelegramApiRequestException e) {
			e.printStackTrace();
		}
	}
}
