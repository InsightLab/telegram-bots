package org.insightlab.qualisbot.entities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;

import org.insightlab.qualisbot.commands.Command;
import org.insightlab.qualisbot.commands.ConferenceCommand;
import org.insightlab.qualisbot.commands.JournalCommand;
import org.insightlab.qualisbot.commands.StartCommand;
import org.insightlab.qualisbot.helpers.FileLog;
import org.insightlab.qualisbot.helpers.LogHelper;
import org.telegram.telegrambots.api.methods.ParseMode;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class QualisBot extends TelegramLongPollingBot{

	private Map<String, Command> commandHash = new HashMap<String, Command>();
	private String token;
	private LogHelper logger;
	
	public QualisBot(String token) throws IOException {
		super();
		
		this.token = token;
		
		commandHash.put("/conferencia", new ConferenceCommand());
		commandHash.put("/periodico", new JournalCommand());
		commandHash.put("/start", new StartCommand());
		commandHash.put("/ajuda", new StartCommand());
		
		logger = new FileLog();
		
		System.out.println("Bot running...");
		
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
				logger.write(update);
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
		return token;
	}

}
