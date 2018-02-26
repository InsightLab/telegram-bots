package org.insightlab.botMother.stateCommands;

import org.insightlab.botMother.bot.ConversationBot;
import org.insightlab.botMother.stateCommand.MessageStateCommand;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class NameCommand extends MessageStateCommand {

	@Override
	public Object run(Update update, ConversationBot bot) {
		String name = update.getMessage().getText();
		try {
			bot.replyMessage(update, "Hello "+name+". What is your age?");
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		return "age_state";
	}

}
