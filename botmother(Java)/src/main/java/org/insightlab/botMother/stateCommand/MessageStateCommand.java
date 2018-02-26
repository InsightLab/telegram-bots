package org.insightlab.botMother.stateCommand;

import org.telegram.telegrambots.api.objects.Update;

public abstract class MessageStateCommand implements StateCommand {

	public boolean match(Update update) {
		return true;
	}

}
