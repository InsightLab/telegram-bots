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

    tables = html.find_all("table", {"class":"refeicao"})

    refeicao = ""
    opcao = ""

    menu = {}

    if(len(tables)==1):
        return ""

    else:
        for table,refeicao in zip(tables,["Desjejum", "Almoço","Jantar"]):
            menu[refeicao] = {}
            for line in table.find_all("tr"):
                lines = line.find_all("td")

                opcao = lines[0].get_text()

                pratos = [prato.get_text() for prato in lines[1].find_all("span")]
                menu[refeicao][opcao] = pratos

        #pprint(menu)
        #return menu
        text = "\n".join(getMealText(menu,meal) for meal in ["Desjejum","Almoço","Jantar"])
        return text

def sendMenu(menu,chat):
    menu = "<b>Cardápio de hoje: "+dt.today().strftime("%d-%m-%Y")+"\n\n</b>"+menu
    bot.send(menu,chat_id=chat)

