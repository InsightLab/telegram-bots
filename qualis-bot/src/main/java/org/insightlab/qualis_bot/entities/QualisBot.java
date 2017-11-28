package org.insightlab.qualis_bot.entities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.insightlab.qualis_bot.commands.Command;
import org.insightlab.qualis_bot.commands.ConferenceCommand;
import org.insightlab.qualis_bot.commands.JournalCommand;
import org.insightlab.qualis_bot.commands.StartCommand;
import org.telegram.telegrambots.api.methods.ParseMode;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class QualisBot extends TelegramLongPollingBot{

	private Map<String, Command> commandHash = new HashMap<String, Command>();
	
	public QualisBot() throws IOException {
		super();
		commandHash.put("/conferencia", new ConferenceCommand());
		commandHash.put("/periodico", new JournalCommand());
		commandHash.put("/start", new StartCommand());
		commandHash.put("/ajuda", new StartCommand());
		
	}
	
	public String getBotUsername() {
		String botUsername = "verificadorQualisCAPESBot";
		return botUsername;
	}

	public void onUpdateReceived(Update update) {
		
		if(update.hasMessage()) {
			Message message = update.getMessage();
			String[] command = message.getText().split(" ");

			if(command[0].startsWith("/") && commandHash.containsKey(command[0])) {
				commandHash.get(command[0]).run(update, this);
			} 
			
			else {
				SendMessage sm = new SendMessage();
				sm.setChatId(update.getMessage().getChatId());

				sm.setParseMode(ParseMode.HTML);
				
				sm.setText("Comando Inválido. Digite <b>/ajuda</b> para a lista de comandos.");
				try {
					sendMessage(sm);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}

	@Override
	public String getBotToken() {
		String botToken = System.getenv("QUALIS-BOT-TOKEN");
		return botToken;
	}

}
