package org.insightlab.botMother.commands;

import org.insightlab.botMother.bot.Bot;
import org.insightlab.botMother.command.SimpleCommand;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class PingCommand extends SimpleCommand {
	
	public PingCommand(){
		super("ping");
	}
	
	@Override
	public void run(Update update, Bot bot) {
		try {
			bot.replyMessage(update, "pong!");
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}		
	}

}
