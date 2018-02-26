package org.insightlab.botMother.stateCommands;

import org.insightlab.botMother.bot.ConversationBot;
import org.insightlab.botMother.stateCommand.MessageStateCommand;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class AgeCommand extends MessageStateCommand {

	@Override
	public Object run(Update update, ConversationBot bot) {
		
		try{
			int age = Integer.parseInt(update.getMessage().getText());
			bot.replyMessage(update, "So you are "+age+" years old? Interesting...");
			bot.replyMessage(update, "Ok. See you later :D");
		} catch(TelegramApiException e){
			e.printStackTrace();
		}
		catch(Exception e){
			try {
				bot.replyMessage(update, "That is not a valid age. Please try again");
				return "age_state";
			} catch (TelegramApiException e1) {
				e1.printStackTrace();
			}
		}
		
		return ConversationBot.CONVERSATION_END;
	}

}
