package org.insightlab.qualisbot.helpers;

import org.telegram.telegrambots.api.objects.Update;

public interface LogHelper {
	public void write(Update update);
}
