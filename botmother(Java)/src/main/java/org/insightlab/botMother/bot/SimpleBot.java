
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

import java.util.ArrayList;
import java.util.List;

import org.insightlab.botMother.command.Command;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Simple Bot class that only deals with simple commands.
 *
 * @author Lucas Peres
 * @since 1.0
 */

public class SimpleBot extends Bot{

    /**
     * Bot's username. Doesn't need to be the same defined at @BotFather.
     */
	protected String username;

	/**
     * Bot's token retrieved from @BotFather.
     */
	protected String token;

    /**
     *  Commands that the Bot will execute.
     */
	protected List<Command> commands;

    /**
     * Simple constructor that assumes an empty username to the Bot.
     * @param token Bot's token.
     */
	public SimpleBot(String token){
		this(token, "");
	}

    /**
     * Complete constructor that takes Bot's token and username.
     * @param token Bot's token.
     * @param username Bot's username.
     */
	public SimpleBot(String token, String username){
	    super();
		this.token = token;
		this.username = username;
		
		commands = new ArrayList<>();
	}

    /**
     * Get Bot's username.
     * @return Bot's username.
     */
	public String getBotUsername() {
		return username;
	}

    /**
     * Method called when the Bot receive a new update.
     * @param update the update received by the Bot.
     */
	public void onUpdateReceived(Update update) {
		//try to identify a command that can be triggered by this update
	    for(Command command : commands){
	        //the first command that is triggered ends the loop
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

    /**
     * Adds a command to the Bot. The order that the commands are inserted is important.
     * If two or more commands can match in a same Update, only the first one will be executed.
     * @param command the command that will be inserted.
     */
	@Override
	public void addCommand(Command command) {
		commands.add(command);
	}

    /**
     * Removes a command from the Bot.
     * @param command the command that will be removed.
     */
	@Override
	public void removeCommand(Command command) {
		commands.remove(command);
	}

    /**
     * Get Bot's token.
     * @return Bot's token.
     */
	@Override
	public String getBotToken() {
		return token;
	}

}
