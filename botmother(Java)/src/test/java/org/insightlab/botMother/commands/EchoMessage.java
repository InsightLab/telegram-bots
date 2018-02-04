package org.insightlab.botMother.commands;

import org.insightlab.botMother.bot.Bot;
import org.insightlab.botMother.command.MessageCommand;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class EchoMessage extends MessageCommand {

	@Override
	public void run(Update update, Bot bot) {
		try {
			if (update.hasMessage())
				bot.replyMessage(update, update.getMessage().getText());
			else
				bot.replyMessage(update, "Message with no text sent");
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
