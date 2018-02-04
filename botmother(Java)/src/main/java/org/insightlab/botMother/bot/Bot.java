package org.insightlab.botMother.bot;

import org.insightlab.botMother.command.Command;
import org.telegram.telegrambots.api.methods.ParseMode;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public abstract class Bot extends TelegramLongPollingBot {
	
	public abstract void addCommand(Command command);
	
	public abstract void removeCommand(Command command);
	
	public void replyMessage(Update update, String message) throws TelegramApiException{
		this.replyMessage(update, message, ParseMode.HTML);
	}
	
	public void replyMessage(Update update, String message, String parser) throws TelegramApiException{
		SendMessage sm = new SendMessage();
		sm.setChatId(update.getMessage().getChatId());

		sm.setParseMode(parser);
		
		sm.setText(message);
		
		sendMessage(sm);
	}
}
