import configparser

import telebot
from telebot import custom_filters
from telebot.handler_backends import State, StatesGroup
from telebot.types import InlineKeyboardMarkup, InlineKeyboardButton, ReplyKeyboardMarkup, KeyboardButton, \
    ReplyKeyboardRemove
from telebot.storage import StateMemoryStorage

import db

config = configparser.ConfigParser()
config.read("config.ini")

# Setting configuration values
token = config['Telegram']['token']

state_storage = StateMemoryStorage()
bot = telebot.TeleBot(config['Telegram']['token'], state_storage=state_storage)

channels = dict()


class BotStates(StatesGroup):
    authentication = State()
    waiting_for_channels = State()
    delete_channel = State()
    stay = State()
    edit = State()


@bot.message_handler(commands=['start'])
def start_ex(message):
    bot.set_state(message.from_user.id, BotStates.authentication, message.chat.id)
    bot.send_message(message.chat.id,
                     'Привет! Это бот Jurnalik.\n'
                     'Пришлите реферальный код из нашего приложения, чтобы начать.')


@bot.message_handler(state=BotStates.authentication, func=lambda message: True)
def start_ex(message):
    code = message.text
    try:
        code = int(code)
    except Exception:
        bot.send_message(message.chat.id, 'Пожалуйста, пришлите действительный код.')
        return
    user = db.auth_user(code, message.chat.id)
    if not user:
        bot.send_message(message.chat.id, 'Пожалуйста, пришлите действительный код.')
        return
    channels[message.chat.id] = []
    bot.set_state(message.from_user.id, BotStates.waiting_for_channels, message.chat.id)
    bot.send_message(message.chat.id,
                     'Отлично!\n'
                     'Сейчас мы составим список каналов, новости которых вы хотите читать.\n'
                     'Пришлите ссылку на желаемый канал или перешлите из него пост.')


@bot.message_handler(state=BotStates.waiting_for_channels, func=lambda message: message.forward_from_chat,
                     content_types=["text", "photo", "video", "document", "audio"])
def handle_forwarded_message(message):
    markup = InlineKeyboardMarkup()
    markup.row_width = 2
    markup.add(InlineKeyboardButton("Удалить канал", callback_data="delete_channel"))
    markup.add(InlineKeyboardButton("Завершить", callback_data="end_with_channels"))

    # print(message.forward_from_chat.type)
    # if message.forward_from_chat.type == 'private':
    #     bot.send_message(message.chat.id, "Извините, разрешены только открытые каналы.")
    #     return

    try:
        chat = bot.get_chat(message.forward_from_chat.id)
    except Exception:
        bot.send_message(message.chat.id, "Извините, разрешены только открытые каналы.")
        return

    if chat.photo:
        file_id = chat.photo.big_file_id
        file_info = bot.get_file(file_id)
        photo_url = f"https://api.telegram.org/file/bot{bot.token}/{file_info.file_path}"
    else:
        photo_url = ""

    channels[message.chat.id].append(
        {"id": message.forward_from_chat.id, "title": message.forward_from_chat.title, "photo": photo_url})
    bot.send_message(message.chat.id, f'Канал "{message.forward_from_chat.title}" успешно добавлен!')
    all_channels = [x["title"] for x in channels[message.chat.id]]
    bot.send_message(message.chat.id, "Ваши каналы:\n" + '\n'.join(all_channels), reply_markup=markup)


@bot.message_handler(state=BotStates.waiting_for_channels, func=lambda message: True)
def handle_all_messages(message):
    markup = InlineKeyboardMarkup()
    markup.row_width = 2
    markup.add(InlineKeyboardButton("Удалить канал", callback_data="delete_channel"))
    markup.add(InlineKeyboardButton("Завершить", callback_data="end_with_channels"))

    bot.send_message(message.chat.id,
                     "Пришлите ссылку на желаемый канал или перешлите из него пост.")


@bot.message_handler(state=BotStates.delete_channel, func=lambda message: True)
def handle_delete_messages(message):
    if message.text == "Вернуться":
        markup = InlineKeyboardMarkup()
        markup.row_width = 2
        markup.add(InlineKeyboardButton("Удалить канал", callback_data="delete_channel"))
        markup.add(InlineKeyboardButton("Завершить", callback_data="end_with_channels"))

        a = ReplyKeyboardRemove()

        all_channels = [x["title"] for x in channels[message.chat.id]]
        bot.send_message(message.chat.id, "Готово! Ваш список каналов:\n" + '\n'.join(all_channels), reply_markup=a)
        bot.send_message(message.chat.id,
                         "Вы можете переслать другие каналы для подписки или завершить изменения.",
                         reply_markup=markup)
        bot.set_state(message.chat.id, BotStates.waiting_for_channels, message.chat.id)
        return

    for x in channels[message.chat.id]:
        if x["title"] == message.text:
            channels[message.chat.id].remove(x)

    markup = ReplyKeyboardMarkup(row_width=1)
    all_channels = [x["title"] for x in channels[message.chat.id]]
    for x in all_channels:
        markup.add(KeyboardButton(x))

    markup.add(KeyboardButton("Вернуться"))

    bot.send_message(message.chat.id, f'Канал "{message.text}" успешно удален.\n')
    all_channels = [x["title"] for x in channels[message.chat.id]]
    bot.send_message(message.chat.id, "Ваш список каналов:\n" + '\n'.join(all_channels), reply_markup=markup)


@bot.callback_query_handler(func=lambda call: True)
def callback_query(call):
    if call.data == "end_with_channels":
        # markup = InlineKeyboardMarkup()
        # markup.row_width = 2
        # markup.add(InlineKeyboardButton("Добавить другие каналы", callback_data="edit_channels"))
        # markup.add(InlineKeyboardButton("Удалить канал", callback_data="delete_channel"))

        all_channels = [x["title"] for x in channels[call.message.chat.id]]
        bot.send_message(call.message.chat.id, "Отлично! Ваш список каналов составлен:\n" + '\n'.join(all_channels))

        db.save_channels(call.message.chat.id, channels[call.message.chat.id])

        channels[call.message.chat.id].clear()
        bot.set_state(call.message.chat.id, BotStates.stay, call.message.chat.id)

    if call.data == "delete_channel":
        if not channels[call.message.chat.id]:
            # TODO: get from db
            pass
        bot.set_state(call.message.chat.id, BotStates.delete_channel, call.message.chat.id)
        markup = ReplyKeyboardMarkup(row_width=1)
        all_channels = [x["title"] for x in channels[call.message.chat.id]]
        for x in all_channels:
            markup.add(KeyboardButton(x))
        markup.add(KeyboardButton("Вернуться"))
        bot.send_message(call.message.chat.id, "Выберите канал, которые желаете удалить", reply_markup=markup)

    if call.data == "edit_channels":
        if not channels[call.message.chat.id]:
            # TODO: get from db
            pass
        markup = InlineKeyboardMarkup()
        markup.row_width = 2
        markup.add(InlineKeyboardButton("Удалить канал", callback_data="delete_channel"))
        markup.add(InlineKeyboardButton("Завершить", callback_data="end_with_chanpip install psycopg2nels"))

        all_channels = [x["title"] for x in channels[call.message.chat.id]]
        bot.send_message(call.message.chat.id, "Ваши каналы:\n" + '\n'.join(all_channels), reply_markup=markup)

        bot.set_state(call.message.from_user.id, BotStates.waiting_for_channels, call.message.chat.id)
        bot.send_message(call.message.chat.id,
                         'Пришлите ссылку на канал, который хотите подписаться, или перешлите из него пост.')


bot.add_custom_filter(custom_filters.StateFilter(bot))

bot.infinity_polling(skip_pending=True)
