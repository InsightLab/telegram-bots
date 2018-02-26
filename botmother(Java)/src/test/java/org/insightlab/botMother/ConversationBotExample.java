package org.insightlab.botMother;

import org.insightlab.botMother.bot.ConversationBot;
import org.insightlab.botMother.commands.EchoMessage;
import org.insightlab.botMother.commands.PingCommand;
import org.insightlab.botMother.fallbacks.FallbackCommand;
import org.insightlab.botMother.service.BotService;
import org.insightlab.botMother.stateCommands.AgeCommand;
import org.insightlab.botMother.stateCommands.HelloCommand;
import org.insightlab.botMother.stateCommands.NameCommand;

public class ConversationBotExample {

	public static void main(String[] args) {
		BotService.start();
		
		ConversationBot bot = new ConversationBot("431452517:AAGa-6fX5H5mj9x0rFa4SOHD-qHf8eyAgbA");
		
		bot.addCommand(new PingCommand());
		bot.addCommand(new EchoMessage());
		
		bot.addEntryPoint(new HelloCommand());
		bot.addCommandToState("name_state", new NameCommand());
		bot.addCommandToState("age_state", new AgeCommand());
		bot.addFallback(new FallbackCommand());
		
		BotService.addBot(bot);
	}
}
