package org.insightlab.botMother.fallbacks;

import org.insightlab.botMother.bot.ConversationBot;
import org.insightlab.botMother.stateCommand.SimpleStateCommand;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class FallbackCommand extends SimpleStateCommand {
	
	public FallbackCommand(){
		super("cancel");
	}
	
	@Override
	public Object run(Update update, ConversationBot bot) {
		
		try {
			bot.replyMessage(update, "Ok, we talk later!");
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		
		return ConversationBot.CONVERSATION_END;
	}

}
