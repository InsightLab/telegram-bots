package org.insightlab.botMother.command;

import org.telegram.telegrambots.api.objects.Update;

public abstract class MessageCommand implements Command {
	
	public boolean match(Update update) {
		return true;
	}

}
