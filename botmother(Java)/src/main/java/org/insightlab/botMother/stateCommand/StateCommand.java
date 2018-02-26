package org.insightlab.botMother.stateCommand;

import org.insightlab.botMother.bot.ConversationBot;
import org.telegram.telegrambots.api.objects.Update;

public interface StateCommand {
	public Object run(Update update, ConversationBot bot);
	public boolean match(Update update);
}
