package org.insightlab.botMother.command;

import org.insightlab.botMother.bot.Bot;
import org.telegram.telegrambots.api.objects.Update;

public interface Command {
	public void run(Update update, Bot bot);
	public boolean match(Update update);
}
