package org.insightlab.botMother;

import org.insightlab.botMother.bot.Bot;
import org.insightlab.botMother.bot.SimpleBot;
import org.insightlab.botMother.commands.EchoMessage;
import org.insightlab.botMother.commands.PingCommand;
import org.insightlab.botMother.service.BotService;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class SimpleBotExample {

	public static void main(String[] args) throws TelegramApiRequestException {
		BotService.start();
		
		Bot bot = new SimpleBot("");
		
		bot.addCommand(new PingCommand());
		bot.addCommand(new EchoMessage());
		
		BotService.addBot(bot);
	}

}
