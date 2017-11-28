from os import environ
import sys
import telegram
import socket
import psutil
import math
from telegram.ext import Updater, CommandHandler, MessageHandler, Filters
from collections import Counter

#Token do @BotFather
token = environ.get("BOT_OPTIMUS_TOKEN")
machine_name = "Optimus Server"

def get_username(message):
	return "{} {} (@{})".format(message.from_user.first_name, message.from_user.last_name, message.from_user.username)


def get_ip():
	# s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
	# s.connect(("8.8.8.8", 80))
	# return s.getsockname()[0]
	return ""


def start(bot, update):
	update.message.reply_text("Welcome to {} assistant bot! An autobot of Insight Data Science Lab!".format(machine_name))


def ping(bot, update):	
	update.message.reply_text("Hello {}. I am Optimus Prime, leader of the autobots!".format(get_username(update.message)))


def ip(bot, update):
	import socket
	update.message.reply_text("My local IP is {} (SSH on port 2222)".format(get_ip()))


def memory(bot, update):
	mem = psutil.virtual_memory()
	total = math.ceil(mem.total/1024/1024/1024)
	used = round(mem.used/1024/1024/1024, 2)
	perc = round((mem.used/mem.total)*100, 2)
	update.message.reply_text("<b>Total memory:</b> {} GB \n<b>Used memory:</b> {} GB ({}%)".format(total, used, perc), parse_mode=telegram.ParseMode.HTML)


def users(bot, update):
	users = str.join('\n', set([u.name for u in psutil.users()]))
	if users:
		msg = "<b>Online users:</b>\n{}".format(users)
	else:
		msg = "There are no users."
	update.message.reply_text(msg, parse_mode=telegram.ParseMode.HTML)


def error(bot, update, error):
	print(error)
	update.message.reply_text(str(error))

updater = Updater(token)

dp = updater.dispatcher

dp.add_handler(CommandHandler("start", start))
dp.add_handler(CommandHandler("ping", ping))
dp.add_handler(CommandHandler("ip", ip))
dp.add_handler(CommandHandler("memory", memory))
dp.add_handler(CommandHandler("users", users))

dp.add_error_handler(error)

updater.start_polling()

updater.idle()