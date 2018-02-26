package org.insightlab.botMother.bot;

import java.util.ArrayList;
import java.util.List;

import org.insightlab.botMother.command.Command;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class SimpleBot extends Bot{
	
	protected String username;
	protected String token;
	protected List<Command> commands;
	
	public SimpleBot(String token){
		this(token, "");
	}
	
	public SimpleBot(String token, String username){
		this.token = token;
		this.username = username;
		
		commands = new ArrayList<>();
	}
	
	public String getBotUsername() {
		return username;
	}

	public void onUpdateReceived(Update update) {
		for(Command command : commands){
			if(command.match(update)){
				command.run(update, this);
				return;
			}
		}
		
		//No command triggered
		try {
			this.replyMessage(update, "Message not recognized");
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addCommand(Command command) {
		commands.add(command);
	}

	@Override
	public void removeCommand(Command command) {
		commands.remove(command);
	}

	@Override
	public String getBotToken() {
		return token;
	}

}
