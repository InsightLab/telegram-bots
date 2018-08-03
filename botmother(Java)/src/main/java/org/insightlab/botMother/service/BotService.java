
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

package org.insightlab.botMother.service;

import org.insightlab.botMother.bot.Bot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

/**
 * Class that start the Bot API.
 *
 * @author Lucas Peres
 * @since 1.0
 */
public class BotService {
    /**
     * Bots API from telegrambots.
     * @see org.telegram.telegrambots.TelegramBotsApi
     */
	private static TelegramBotsApi bots;

    /**
     * Starts the API.
     * This method should be used before any Bot instantiation.
     */
	public static void start(){
		ApiContextInitializer.init();
		bots = new TelegramBotsApi();
	}

    /**
     * Adds a Bot to the API
     * @param bot Bot that will be added.
     */
	public static void addBot(Bot bot){
		try {
			bots.registerBot(bot);
		} catch (TelegramApiRequestException e) {
			e.printStackTrace();
		}
	}
}
