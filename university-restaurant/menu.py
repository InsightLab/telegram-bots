from bs4 import BeautifulSoup as bs
import urllib.request as req
import bot
from datetime import datetime as dt
from pprint import pprint

#bot.getChats()

url = "http://www.ufc.br/restaurante/cardapio/1-restaurante-universitario-de-fortaleza"

opcoes = {
    "Desjejum":["Bebidas","Pães","Frutas","Especial"],
    "Almoço":["Principal","Vegetariano","Salada","Guarnição","Acompanhamento","Suco","Sobremesa"],
    "Jantar":["Principal","Vegetariano","Salada","Guarnição","Acompanhamento","Suco","Sobremesa"]

}

def getMealText(menu,meal):
    text = "<b>{}</b>:\n".format(meal.upper())

    for opcao in opcoes[meal]:
        text+="\t\t{}:\n".format(opcao)
        text+="\t\t\t\t\t\t\t\t- <i>{}</i>\n".format('\n\t\t\t\t\t\t\t\t- '.join([prato for prato in menu[meal][opcao]]))

    return text

def getMenu():
    site = req.urlopen(url).read()

    html = bs(site,"html.parser")

    table = html.find_all("tbody")[0].find_all("tr")

    refeicao = ""
    opcao = ""

    menu = {}

    if(len(table)==1):
        return ""

    else:
        for line in table:
            lines = line.find_all("td")
            
            if(len(lines)==1):
                refeicao = lines[0].find_all("h3")[0].get_text()
                menu[refeicao] = {}

            else:
                opcao = lines[0].find_all("span")[0].get_text()

                pratos = [prato.get_text() for prato in lines[1].find_all("span") if '(' not in prato.get_text()]
                menu[refeicao][opcao] = pratos

        #pprint(menu)
        #return menu
        text = "\n".join(getMealText(menu,meal) for meal in ["Desjejum","Almoço","Jantar"])
        return text

def sendMenu(menu=getMenu(),chat=-156030146):
    menu = "<b>Cardápio de hoje: "+dt.today().strftime("%d-%m-%Y")+"\n\n</b>"+menu
    bot.send(menu,chat_id=chat)

