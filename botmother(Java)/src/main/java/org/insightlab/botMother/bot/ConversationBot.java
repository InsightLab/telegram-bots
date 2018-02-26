package org.insightlab.botMother.bot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.insightlab.botMother.command.Command;
import org.insightlab.botMother.stateCommand.StateCommand;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class ConversationBot extends SimpleBot {
	
	public static Object CONVERSATION_END = null;
	
	Map<Integer,Object> userState;
	Map<Object,List<StateCommand>> commandsOfStates;
	
	List<StateCommand> conversationsEntryPoints;
	List<StateCommand> fallbacks;
	
	public ConversationBot(String token){
		this(token, "");
	}
	
	public ConversationBot(String token, String username) {
		super(token, username);
		
		userState = new HashMap<>();
		
		commandsOfStates = new HashMap<>();
		conversationsEntryPoints = new ArrayList<>();
		fallbacks = new ArrayList<>();
	}

	public void addEntryPoint(StateCommand command){
		conversationsEntryPoints.add(command);
	}
	
	public void addState(Object state){
		if(!commandsOfStates.containsKey(state))
			commandsOfStates.put(state, new ArrayList<>());
	}
	
	public void removeState(Object state){
		commandsOfStates.remove(state);
	}
	
	public void addCommandToState(Object state, StateCommand command){
		if(!commandsOfStates.containsKey(state))
			commandsOfStates.put(state, new ArrayList<>());
		
		commandsOfStates.get(state).add(command);
	}
	
	public void addFallback(StateCommand command){
		fallbacks.add(command);
	}
	
	@Override
	public void onUpdateReceived(Update update) {
		int userId = update.getMessage().getFrom().getId();
		
		
		if(userState.get(userId)==null){
			for(StateCommand command : conversationsEntryPoints){
				if(command.match(update)){
					Object state = command.run(update, this);
					userState.put(userId, state);
					
					return;
				}
			}
		}
		else{
			//checking if is a fallback
			for(StateCommand command : fallbacks){
				if(command.match(update)){
					command.run(update, this);
					userState.remove(userId);
					
					return;
				}
			}
			
			//if is not a fallback, proceed
			Object state = userState.get(userId);
			for(StateCommand command : commandsOfStates.get(state)){
				if(command.match(update)){
					Object newState = command.run(update, this);
					
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

}
