package org.insightlab.qualisbot.commands;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.insightlab.qualisbot.entities.Conference;
import org.insightlab.qualisbot.entities.Journal;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.ParseMode;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class JournalCommand implements Command {

	List<Journal> journalList = new ArrayList<Journal>();
	
	public JournalCommand() throws IOException {
		
//		String journalFilePath = ConferenceCommand.class.getClassLoader().getResource("journals.json").getPath();

//		InputStream is = new FileInputStream(journalFilePath);
		InputStream is = ConferenceCommand.class.getResourceAsStream("/journals.json");
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		String line = br.readLine();
		String source = new String();

		while (line != null) {

			source = source.concat(line);
			line = br.readLine();

		}

		JSONObject jsonObject = new JSONObject(source);

		JSONArray jsonArray = (JSONArray) jsonObject.get("data");

		for (Object object : jsonArray) {

			JSONArray journal = (JSONArray) object;

			journalList
					.add(new Journal(journal.getString(0), journal.getString(1), journal.getString(2)));

		}

		br.close();
	}

	public void run(Update update, TelegramLongPollingBot bot) {

		SendMessage sm = new SendMessage();
		sm.setChatId(update.getMessage().getChatId());
		sm.setParseMode(ParseMode.HTML);
		
		Message message = update.getMessage();
		
		List<Journal> results = new ArrayList<Journal>();
		
		for (Journal journal : journalList) {
			if(journal.equals(message.getText().replaceAll("/periodico ", ""))) {
				results.add(journal);
			}
		}
		
		String finalMessage;
		
		if(results.size() != 0) {
			finalMessage = new String("<b>Periódicos encontradas</b>\n");
		} else {
			finalMessage = new String("<b>Nenhum periódico encontrado</b>\n");
		}
		
		for (Journal journal : results) {
			finalMessage = finalMessage.concat("\n(" + journal.getIssn() +  ")<i>" + journal.getName() + "</i> - <b>" + journal.getQualis() + "</b>\n");
		}
		
		sm.setText(finalMessage);
		
		try {
			bot.sendMessage(sm);
		} catch (TelegramApiException e) {
			
			sm.setText("<b>Erro interno :-/</b>\n\nTente refinar sua busca.");
			try {
				bot.sendMessage(sm);
			} catch (TelegramApiException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
		}
		
	}

}
