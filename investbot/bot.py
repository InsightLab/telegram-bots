import telegram
import feedparser
from datetime import datetime, date
from bs4 import BeautifulSoup as bs
import urllib.request as req

# token do seu bot gerado pelo @BotFather no telegram
my_token = ''

# grupo que receberá mensagens do bot
default_chat_id = ''

rss = "http://www.infomoney.com.br/mercados/acoes-e-indices/rss"
calls = ['Ibovespa', 'Vale (VALE3)']

# http://www.infomoney.com.br/onde-investir/acoes/noticia/4164521/frases-curtas-inspiradoras-sobre-investimento
frases = {
	'Morgan Housel': 'Se preocupe somente quando você achar que tiver tudo resolvido.',
	'Tom Gardner': 'Encontre lideres notáveis e uma missão para o longo da vida.',
	'Bill Mann': 'Busque investir em conjunto com grandes gestores, depois, é só ser paciente.',
	'Barry Ritholtz': 'Mantenha a simplicidade, faço menos e administre sua estupidez.',
	'Robert Brokamp': 'Diversificação reduz os riscos, aumenta a previsibilidade e impulsiona os retornos.',
	'Michael Batnick': 'Evitar erros catastróficos é mais importante do que construir o portfólio perfeito.',
	'Tim Hanson': 'Compre ações impressionantes por preços que não refletem sua grandiosidade.',
	'Rich Greifner': 'Pense a longo prazo, seja paciente e busque por retornos assimétricos.',
	'Eddy Elfenbein': 'Seja paciente e ignore modismos. Foque no valor e não entre em pânico.',
	'Michael Kitces': 'Invista pensando no longo prazo, não especule, mas, não ignore as flutuações do mercado.',
	'Ben Carlson': 'Menos é mais. O processo de investimento deve ser mais importante que os resultados. Comportamento correto na hora de investir é a chave.',
	'James Early': 'Explore a fraqueza cognitiva dos outros.',
	'Ron Gross': 'Compre ações de companhias que são sólidas e que possuem uma margem de segurança e segure-as.',
	'Tren Griffin': 'Compre ativos a preços que possam te dar uma margem de segurança.',
	'Jeff Fischer': 'Compre ações e use opções para gerar renda. Assim, você garante fracassos menores.',
	'Justin Wolfers': 'Guarde dinheiro, evite consultorias caras e diversifique os investimentos de forma intensa.',
	'Matt Koppenheffer': 'Busque por negócios que sejam diferenciados, bem administrados e orientados para o consumidor. Compre em baixa e tenha em mente retornos a longo prazo.',
	'Seth Jayson': ' Seja seguro, mantenha os custos em baixo nível e não pense demais.',
	'Craig Shapiro': 'Alinhar interesse próprio com interesses mais amplos irá gerar retornos exponenciais.'
}


def send(msg, token, group_id):
    """
    Envia uma mensagem atraves de um bot para um determinado grupo
    """
    try:
        bot = telegram.Bot(token=token)
        bot.sendMessage(chat_id=group_id, text=msg, parse_mode=telegram.ParseMode.HTML)    
    except Exception as e:
    	print("Erro ao enviar mensagem: %s" % e)


# send(frases['Jeff Fischer'], "450399432:AAEiC7hCsa9FcJgIgR8DH7Nl5fgFa0j4rA0", -212351490)


def crawler_info_trade(item):
	"""
	Crawler da página do InfoTrade
	"""
	result = []
	titulo = item['title']
	url = item['link']
	page = req.urlopen(url).read()
	html = bs(page, 'html.parser')
	# Tratamento para não pegar a repetição mais interna dessa seleção
	callouts = [item for item in html.select('div.callout > div.row') if len(item) >= 7]
	for call in callouts:		
		# Filtra as calls desejadas
		_id = call.find('h5').strong.text
		if _id in calls:
			dado = {}
			dado['papel'] = _id.replace(u'\xa0', u' ')
			parte1 = call.find('h5')
			dado['fechamento'] = parte1.span.text
			dado['valor'] = parte1.text.split()[-1]
			parte2 = str(call.find('h6').text).replace(u'\xa0', u' ').split('Suportes: ')
			dado['resistencias'] = parte2[0].replace('Resistências: ', '')
			dado['suportes'] = parte2[1]
			dado['grafico'] = call.select_one('div.columns > img[src]')['src']
			dado['texto'] = call.find_all('div', {'class': 'columns'})[-1].text.replace(u'\xa0', u' ')
			result.append(dado)
	return result


def get_info_trade(day=date.today()):
	"""
	Recupera publicação do InfoTrade de um determinado dia
	"""
	feed = feedparser.parse(rss)
	for key in feed['entries']:
	    if "InfoTrade" in key['title']:
	    	published = datetime.strptime(key['published'], '%a, %d %b %Y %H:%M:%S %z')
	    	if published.date() == day:
	    		return key


def format_msg(item):
	return "<b>{}</b> ({} - {})\n\n<b>Resistências:</b> {}\n\n<b>Suportes:</b> {}\n\n{}".format(item['papel'].upper(), item['fechamento'], item['valor'], item['resistencias'], item['suportes'], item['texto'])


def main():
	info = get_info_trade()
	itens = crawler_info_trade(info)
	try:
		bot = telegram.Bot(token=my_token)
		for item in itens:
			msg = format_msg(item)
			bot.sendMessage(chat_id=default_chat_id, text=msg, parse_mode=telegram.ParseMode.HTML)
			bot.sendPhoto(chat_id=default_chat_id, photo=item['grafico'])   
	except Exception as e:
		print("Erro ao enviar mensagem: %s" % e)


if __name__ == '__main__':
    main()