package org.insightlab.qualisbot.commands;

import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public interface Command {

	public void run(Update update, TelegramLongPollingBot bot);
	
}
