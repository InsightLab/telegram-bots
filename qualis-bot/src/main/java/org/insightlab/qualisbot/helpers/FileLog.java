package org.insightlab.qualisbot.helpers;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Semaphore;

import org.telegram.telegrambots.api.objects.Update;

public class FileLog implements LogHelper {
	private static Semaphore semaphore;
	
	private static Semaphore getSemaphore(){
		if(semaphore==null)
			semaphore = new Semaphore(1);
		return semaphore;
	}
	
	private String stringfyUpdate(Update update){
		//data,id_usuario,username,tipo_busca,busca
		String s = "";
		
		s += update.getMessage().getDate()+",";
		s += update.getMessage().getFrom().getId()+",";
		s += update.getMessage().getFrom().getUserName()+",";
				
		if(update.getMessage().getText().contains("/conferencia")){
			s += "conference,";
			s += "\""+update.getMessage().getText().replace("/conferencia ", "")+"\""; 
		}
		else{
			s += "journal,";
			s += "\""+update.getMessage().getText().replace("/periodico ", "")+"\"";
		}
		
		s += "\n";
		
		return s;
	}
	
	public void write(Update update) {
		Semaphore s = getSemaphore();
		
		try {
			s.acquire();
			
			FileWriter fw = new FileWriter("log.csv", true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter writer = new PrintWriter(bw);
			
			writer.write(stringfyUpdate(update));
			writer.close();
			
			s.release();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
