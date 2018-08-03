
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


package org.insightlab.botMother.stateCommand;

import org.insightlab.botMother.bot.ConversationBot;
import org.telegram.telegrambots.api.objects.Update;

/**
 * StateCommand interface used by the commands that are going to be inserted on a ConversationBot.
 * @see org.insightlab.botMother.bot.ConversationBot
 * @author Lucas Peres
 * @since 1.0
 */
public interface StateCommand {
    /**
     * Executes the command
     * @param update update that triggered the command.
     * @param bot Bot object that received the update.
     * @return the new state after this command execution.
     */
	Object run(Update update, ConversationBot bot);

    /**
     * Check if the update triggers the command
     * @param update updated that will be tested.
     * @return true if this command should process this update. False otherwise.
     */
	boolean match(Update update);
}
