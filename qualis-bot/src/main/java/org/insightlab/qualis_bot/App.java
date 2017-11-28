package org.insightlab.qualis_bot;

import java.io.IOException;

import org.insightlab.qualis_bot.entities.QualisBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class App {
	public static void main(String[] args) throws IOException {
		ApiContextInitializer.init();
		TelegramBotsApi bot = new TelegramBotsApi();

		try {
			bot.registerBot(new QualisBot());
		} catch (TelegramApiRequestException e) {
			e.printStackTrace();
		}

	}
}
