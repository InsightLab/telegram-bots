package org.insightlab.botMother.stateCommands;

import org.insightlab.botMother.bot.ConversationBot;
import org.insightlab.botMother.stateCommand.SimpleStateCommand;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class HelloCommand extends SimpleStateCommand {
	
	public HelloCommand(){
		super("hi");
	}
	
	@Override
	public Object run(Update update, ConversationBot bot) {
		try {
			bot.replyMessage(update, "Hy, I'm a Curious Bot!. What's your name?");
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		return "name_state";
	}

}
