package org.insightlab.qualis_bot.commands;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.insightlab.qualis_bot.entities.Conference;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.ParseMode;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class ConferenceCommand implements Command {

	List<Conference> conferenceList = new ArrayList<Conference>();

	public ConferenceCommand() throws IOException {

		String conferenceFilePath = ConferenceCommand.class.getClassLoader().getResource("conferences.json").getPath();

		InputStream is = new FileInputStream(conferenceFilePath);
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

			JSONArray conference = (JSONArray) object;

			conferenceList
					.add(new Conference(conference.getString(0), conference.getString(1), conference.getString(2)));

		}

		br.close();

	}

	public void run(Update update, TelegramLongPollingBot bot) {

		SendMessage sm = new SendMessage();
		sm.setChatId(update.getMessage().getChatId());
		sm.setParseMode(ParseMode.HTML);
		
		Message message = update.getMessage();
		
		
		List<Conference> results = new ArrayList<Conference>();
		
		for (Conference conference : conferenceList) {
			if(conference.equals(message.getText().replaceAll("/conferencia ", ""))) {
				results.add(conference);
			}
		}
		
		String finalMessage;
		
		if(results.size() != 0) {
			finalMessage = new String("<b>Conferências encontradas</b>\n");
		} else {
			finalMessage = new String("<b>Nenhuma conferência encontrada</b>\n");
		}
		
		for (Conference conference : results) {
			finalMessage = finalMessage.concat("\n<i>" + conference.getName() + "</i>- <b>" + conference.getQualis() + "</b>\n");
		}
		
		sm.setText(finalMessage);
		
		try {
			bot.sendMessage(sm);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
