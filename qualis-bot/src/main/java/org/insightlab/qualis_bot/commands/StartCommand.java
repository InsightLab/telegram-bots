package org.insightlab.qualis_bot.commands;

import java.util.ArrayList;
import java.util.List;

import org.insightlab.qualis_bot.entities.Conference;
import org.telegram.telegrambots.api.methods.ParseMode;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class StartCommand implements Command {

	public void run(Update update, TelegramLongPollingBot bot) {

		SendMessage sm = new SendMessage();
		sm.setChatId(update.getMessage().getChatId());
		sm.setParseMode(ParseMode.HTML);
		
		String finalMessage = new String("<b>Verificador de Qualis - CAPES</b>\n");
		finalMessage = finalMessage.concat("\nUtilize os seguintes comandos para consulta\n");
		finalMessage = finalMessage.concat("\n<b>/conferencia</b> <i>sigla_conferencia</i> OU <i>nome_conferencia</i>");
		finalMessage = finalMessage.concat("\n<b>/periodico</b> <i>issn_periodico</i> OU <i>nome_periodico</i>");
		
		sm.setText(finalMessage);
		
		try {
			bot.sendMessage(sm);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
