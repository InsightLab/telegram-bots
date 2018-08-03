
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.insightlab.botMother.command.Command;
import org.insightlab.botMother.stateCommand.StateCommand;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Bot class that can handle simple commands and conversations.
 * The Bot can work like a Finite-State Machine. It has an initial state (entry-point) and intermediary states.
 * Each state can have a different set of commands.
 * Each user has its own state.
 * Simple commands doens't change Bots state, only StateCommands.
 *
 * @author Lucas Peres
 * @since 1.0
 */

public class ConversationBot extends SimpleBot {

    /**
     * Macro that represents the end of the Finite-State Machine.
     * The Finite-State Machine can be ended by returning ConversationBot.CONVERSATION_END by a StateCommand.
     */
	public static Object CONVERSATION_END = null;

    /**
     * Map from user id and its current state
     */
	Map<Integer,Object> userState;

    /**
     * Map from state and its commands
     */
	Map<Object,List<StateCommand>> commandsOfStates;

    /**
     * List of StateCommands that start conversations
     */
	List<StateCommand> conversationsEntryPoints;
    /**
     * List of StateCommands that can end a conversation in any state
     */
	List<StateCommand> fallbacks;

    /**
     * Simple constructor that assumes an empty username to the Bot.
     * @param token Bot's token.
     */
	public ConversationBot(String token){
		this(token, "");
	}

    /**
     * Complete constructor that takes Bot's token and username.
     * @param token Bot's token.
     * @param username Bot's username.
     */
	public ConversationBot(String token, String username) {
		super(token, username);
		
		userState = new HashMap<>();
		
		commandsOfStates = new HashMap<>();
		conversationsEntryPoints = new ArrayList<>();
		fallbacks = new ArrayList<>();
	}

    /**
     * Adds a StateCommand that will start a conversation.
     * @param command the command that start a conversation.
     */
	public void addEntryPoint(StateCommand command){
		conversationsEntryPoints.add(command);
	}

    /**
     * Adds a new state to the Bot.
     * If the state already exists, nothing happens.
     * @param state state that will be added to the bot.
     */
	public void addState(Object state){
		if(!commandsOfStates.containsKey(state))
			commandsOfStates.put(state, new ArrayList<>());
	}

    /**
     * Remove a state from the Bot.
     * @param state state that will be removed from the Bot.
     */
	public void removeState(Object state){
		commandsOfStates.remove(state);
	}

    /**
     * Adds a command that can be executed only when the Bot is at the certain state.
     * @param state the state that the command can be executed.
     * @param command the command that can be executed.
     */
	public void addCommandToState(Object state, StateCommand command){
		if(!commandsOfStates.containsKey(state))
			this.addState(state);
		
		commandsOfStates.get(state).add(command);
	}

    /**
     * Adds a fallback to the Bot.
     * A fallback is a StateCommand that can executed at any state of the Finite-State Machine..
     * @param command the command that will act as an fallback.
     */
	public void addFallback(StateCommand command){
		fallbacks.add(command);
	}

    /**
     * Method called when the Bot receive a new update.
     * @param update the update received by the Bot.
     */
	@Override
	public void onUpdateReceived(Update update) {
		int userId = update.getMessage().getFrom().getId();

		//check if the user is outside the finite-state machine
		if(userState.get(userId)==null){
		    //check if the user will enter in a conversation
			for(StateCommand command : conversationsEntryPoints){
				if(command.match(update)){
				    //update user's state
					Object state = command.run(update, this);
					userState.put(userId, state);
					
					return;
				}
			}

            // if no entry point was found, check the simple commands
            for(Command command : commands){
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
		else{
			//checking if is a fallback
			for(StateCommand command : fallbacks){
				if(command.match(update)){
				    //take the new state and update it
                    Object newState = command.run(update, this);
                    //if is an end of a conversation, we remove the user from the states Map
                    if(newState == ConversationBot.CONVERSATION_END)
                        userState.remove(userId);
					
					return;
				}
			}
			
			//if is not a fallback, proceed
			Object state = userState.get(userId);
			for(StateCommand command : commandsOfStates.get(state)){
				if(command.match(update)){
					Object newState = command.run(update, this);
                    //if is an end of a conversation, we remove the user from the states Map
					if(newState == ConversationBot.CONVERSATION_END)
						userState.remove(userId);
					else
						userState.put(userId, newState);
					
					return;
				}
			}
			
			//No command triggered on current state
			try {
				this.replyMessage(update, "Message not recognized");
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
			
		}

	}

}
