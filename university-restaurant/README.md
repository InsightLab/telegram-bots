# BotRU [![Telegram](https://img.shields.io/badge/Telegram-@ru_ufc_fortaleza-blue.svg)](http://t.me/ru_ufc_fortaleza)

Informe-se pelo _telegram_ sobre o cardápio do Restaurante Universitário da [Universidade Federal do Ceará](http://www.ufc.br/) - Campus do Pici 

## Dependências

Para instalar as dependências, execute 

```bash
pip install -r requirements.txt
```

Para rodar o script é necessário:

* python >= 3.5
* [python-telegram-bot](https://pypi.org/project/python-telegram-bot/)
* [BeautifulSoup4](https://pypi.org/project/beautifulsoup4/)
* token de um bot gerado pelo [@BotFather no telegram](https://telegram.me/BotFather)
* ID de um chat o qual receberá as mensagens (o bot precisa ter **autorização** para postar no grupo/canal)

## Operação

O programa deve ser agendado no sistema operacional (**crontab** no Linux) para executar todos os dias úteis (segunda a sexta) em um horário desejado.

O programa realiza 17 requisições (1 em cada 30 min), tentando obter o cardápio do dia. Caso não consiga, o programa para.

