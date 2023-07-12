import configparser
import asyncio
from datetime import date, datetime, timedelta
import threading

from telethon import TelegramClient
from telethon.errors import SessionPasswordNeededError
from telethon.tl.functions.messages import (GetHistoryRequest)
from telethon.tl.types import (
    PeerChannel
)
import MediaParser


async def login(client, phone):
    if await client.is_user_authorized() == False:
        await client.send_code_request(phone)
        try:
            await client.sign_in(phone, input('Enter the code: '))
        except SessionPasswordNeededError:
            await client.sign_in(password=input('Password: '))


async def fetch_messages(client, channel, offset_id, limit):
    return await client(GetHistoryRequest(
        peer=channel,
        offset_id=offset_id,
        offset_date=None,
        add_offset=0,
        limit=limit,
        max_id=0,
        min_id=0,
        hash=0
    ))


def process_message(channel, yesterday, message, all_messages):
    if date(message.date.year, message.date.month, message.date.day) == yesterday:
        message_date = datetime(message.date.year, message.date.month, message.date.day, message.date.hour,
                                message.date.minute)
        str_date = str(message_date)
        if str_date not in all_messages:
            all_messages[str_date] = {'id': message.id, 'items': []}
        else:
            if message.message:
                text = message.message
                text = text.replace("'", "''")
                all_messages[str_date]['text'] = text
            return
        url = f"https://t.me/s/{channel.username}/{str(message.id)}"
        if message.message:
            text = message.message
            text = text.replace("'", "''")
            all_messages[str_date]['text'] = text
        content = MediaParser.parse_media(url, message_date)
        if content:
            all_messages[str_date]['items'] += content
    elif date(message.date.year, message.date.month, message.date.day) < yesterday:
        return


async def main(client, phone, user_input_channel):
    await client.start()
    print("Client Created")
    # await login(client, phone)

    me = await client.get_me()

    if user_input_channel.isdigit():
        entity = PeerChannel(int(user_input_channel))
    else:
        entity = user_input_channel

    channel = await client.get_entity(entity)

    offset_id = 0
    limit = 100
    all_messages = {}

    history = await fetch_messages(client, channel, offset_id, limit)
    messages = history.messages
    yesterday = date.today() - timedelta(days=1)

    tasks = []
    for message in messages:
        tasks.append(threading.Thread(target=process_message, args=(channel, yesterday, message, all_messages)))
        tasks[-1].start()

    for task in tasks:
        task.join()

    channel_posts = {}

    for value in all_messages.values():
        if "text" in value:
            channel_posts[value["id"]] = {"text": value["text"], "urls": value["items"]}
        else:
            channel_posts[value["id"]] = {"urls": value["items"]}

    return channel_posts


def collect_posts(channel_id):
    config = configparser.ConfigParser()
    config.read("config.ini")

    # Setting configuration values
    api_id = config['Telegram']['api_id']
    api_hash = config['Telegram']['api_hash']

    api_hash = str(api_hash)

    phone = config['Telegram']['phone']
    username = config['Telegram']['username']

    client = TelegramClient(phone, api_id, api_hash)

    with client:
        posts = client.loop.run_until_complete(main(client, phone, channel_id))
        return posts

# posts = collect_posts("https://t.me/StrayKids_JYP")
# for key, value in posts.items():
#     print(f"{key} : {value}")
