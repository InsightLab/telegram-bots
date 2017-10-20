import telegram

#token do seu bot gerado pelo @BotFather no telegram
my_token = ''

#chat que receberá mensagens do bot
default_chat_id = ''

def send(msg, token=my_token, chat_id=default_chat_id):
    """
    Envia uma mensagem atraves de um bot para um determinado chat
    """
    try:
        bot = telegram.Bot(token=token)
        bot.sendMessage(chat_id=chat_id, text=msg, parse_mode=telegram.ParseMode.HTML)
    
    except Exception as e:
    	print("Erro ao enviar mensagem:")
    	print(e)
