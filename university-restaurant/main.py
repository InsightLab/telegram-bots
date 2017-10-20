import bot
import menu
from time import sleep

from datetime import date,time,datetime


def main():
    today = date.today()
    sended = False
    attempts = 17 #se não sair o cardápio até as 14h, não deve ter cardápio naquele dia

    while ((not sended) and (attempts>0)):
        print(today.weekday())
        if today.weekday()<5:
            #dia com possível cardápio
            text = menu.getMenu()

            if(text==""):
                sleep(1800) #esperar 30 min para refazer requisição
                attempts-=1

            else:
                print("cardápio encontrado")
                menu.sendMenu(text,"@ru_ufc_fortaleza")
                sended = True


main()
