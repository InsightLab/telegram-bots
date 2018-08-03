
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

package org.insightlab.botMother.command;

import org.telegram.telegrambots.api.objects.Update;

/**
 * Command that executes only for updates which text starts with a specific command.
 * All commands start with '/'.
 *
 * @author Lucas Peres
 * @since 1.0
 */
public abstract class SimpleCommand implements Command {
    /**
     * Command text that will be used at match method.
     */
    protected String command;

    public SimpleCommand(String command){
        if(command.length()==0 || command.equals("/"))
            throw new Error("Command text can't be empty");

        this.setCommand(command);
    }

    /**
     * Set the command text
     * @param command command that will be set.
     */
	public void setCommand(String command){
        if(command.length()==0 || command.equals("/"))
            throw new Error("Command text can't be empty");

		if(command.startsWith("/"))
			this.command = command;
		else
			this.command = "/"+command;
	}

    /**
     * Check if the update triggers the command
     * @param update updated that will be tested.
     * @return true if this command should process this update. False otherwise.
     */
	public boolean match(Update update) {
		try{
			return update.hasMessage() && update.getMessage().getText().split(" ")[0].equals(this.command);
		}
		catch(NullPointerException e){
			return false;
		}
		
	}

}
