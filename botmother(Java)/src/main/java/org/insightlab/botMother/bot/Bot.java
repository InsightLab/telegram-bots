
/*
 * MIT License
 *
 * Copyright (c) 2017 Insight Data Science Lab
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.insightlab.botMother.bot;

import org.insightlab.botMother.command.Command;
import org.insightlab.botMother.service.BotService;
import org.telegram.telegrambots.api.methods.ParseMode;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Abstract Bot class used to define the types of Bot.
 *
 * @author Lucas Peres
 * @since 1.0
 */

public abstract class Bot extends TelegramLongPollingBot {
    /**
     * Constructor to ensure that BotService is started.
     * @see org.insightlab.botMother.service.BotService
     */
    public Bot(){
        if(!BotService.isStarted())
            throw new Error("Bot Service must be started before any bot instantiation");
    }

    /**
     * Add a command to be executed by the bot.
     * @param command the command that will be inserted.
     */
	public abstract void addCommand(Command command);

    /**
     * Remove a command from the bot.
     * @param command the command that will be removed.
     */
	public abstract void removeCommand(Command command);

    /**
     * Convenient method to simplify a message answering. Uses HTML parser by default
     * @param update the object representing the update from which the user will receive the message .
     * @param message the message that will be sent.
     * @throws TelegramApiException
     */
	public void replyMessage(Update update, String message) throws TelegramApiException{
		this.replyMessage(update, message, ParseMode.HTML);
	}

    /**
     * Convenient method to simplify a message answering.
     * @param update the object representing the update from which the user will receive the message.
     * @param message the message that will be sent.
     * @param parser the parser that will be used to decorate the message.
     * Parsers are available at org.telegram.telegrambots.api.methods.ParseMode
     * @throws TelegramApiException
     */
	public void replyMessage(Update update, String message, String parser) throws TelegramApiException{
		SendMessage sm = new SendMessage();
		sm.setChatId(update.getMessage().getChatId());

		sm.setParseMode(parser);
		
		sm.setText(message);
		
		sendMessage(sm);
	}
}
