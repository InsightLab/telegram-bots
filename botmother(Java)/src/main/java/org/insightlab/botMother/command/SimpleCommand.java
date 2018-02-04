package org.insightlab.botMother.command;

import org.telegram.telegrambots.api.objects.Update;

public abstract class SimpleCommand implements Command {
	protected String command;
	
	public void setCommand(String command){
		if(command.startsWith("/"))
			this.command = command;
		else
			this.command = "/"+command;
	}

	public boolean match(Update update) {
		try{
			return update.hasMessage() && update.getMessage().getText().equals(this.command);
		}
		catch(NullPointerException e){
			return false;
		}
		
	}

}
