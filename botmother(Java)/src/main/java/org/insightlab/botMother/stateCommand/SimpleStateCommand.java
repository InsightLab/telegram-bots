package org.insightlab.botMother.stateCommand;

import org.telegram.telegrambots.api.objects.Update;

public abstract class SimpleStateCommand implements StateCommand {

	protected String command;

	public void setCommand(String command) {
		if (command.startsWith("/"))
			this.command = command;
		else
			this.command = "/" + command;
	}

	public boolean match(Update update) {
		try {
			return update.hasMessage() && update.getMessage().getText().split(" ")[0].equals(this.command);
		} catch (NullPointerException e) {
			return false;
		}

	}

}
