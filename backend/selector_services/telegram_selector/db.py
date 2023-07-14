import psycopg2
import logging

logging.basicConfig(level=logging.INFO, filename="py_log.log", filemode="w")


from dotenv import dotenv_values

config = dotenv_values(".env")
HOST = config["POSTGRES_HOST"]
PORT = config["POSTGRES_PORT"]
USER = config["POSTGRES_USER"]
PASSWORD = config["POSTGRES_PASSWORD"]
DATABASE = config["POSTGRES_DATABASE"]

try:
    conn = psycopg2.connect(database="jurnalik", user="postgres", password="1710", host="127.0.0.1", port="5432")
except:
    print('Can`t establish connection to database')
    exit()

cursor = conn.cursor()


def auth_user(user_id, telegram_id):
    cursor.execute(f"SELECT * FROM full_users_ids WHERE user_id={user_id}")
    try_user = cursor.fetchall()
    if not try_user:
        return False

    upd = f"UPDATE full_users_ids SET telegram_id={telegram_id} WHERE user_id={user_id};"

    cursor.execute(upd)
    conn.commit()

    return True


def save_channels(id, channels):
    cursor.execute(f"SELECT user_id FROM full_users_ids WHERE telegram_id={id}")
    user_id = cursor.fetchone()[0]
    for channel in channels:
        channel_id = str(channel['id'])[4:]
        query = f"SELECT group_id FROM groups_and_channels WHERE group_id = {channel_id}"
        cursor.execute(query)
        existing_group = cursor.fetchone()
        if existing_group is None:
            channel_name = channel["title"]
            channel_picture = channel["photo"]
            add_channel = f"INSERT INTO groups_and_channels(group_id, group_name, picture, source) VALUES ({channel_id}, '{channel_name}', '{channel_picture}', 'tg')"
            cursor.execute(add_channel)
            conn.commit()
        channel_username = channel["username"]
        add_channel = f"INSERT INTO telegram_channels(group_id, username, user_id) VALUES ({channel_id}, '{channel_username}', {user_id})"
        cursor.execute(add_channel)
        conn.commit()


def get_ids():
    get_query = f"SELECT user_id FROM full_users_ids WHERE telegram_id IS NOT NULL"
    cursor.execute(get_query)
    ids = cursor.fetchall()
    user_ids = []
    for id in ids:
        user_id = str(id[0])
        user_ids.append(user_id)
    return user_ids


def get_channels(user_id):
    get_query = f"SELECT username FROM telegram_channels WHERE user_id = {'user_id'}"
    cursor.execute(get_query)
    channels = cursor.fetchall()
    user_channels = []
    for channel in channels:
        username = str(channel[0])
        user_channels.append(username)
    return user_channels


def write_posts(user_id, channel_username, posts):
    for key, value in posts.items():
        channel_id_query = f"SELECT group_id from telegram_channels WHERE username='{channel_username}'"
        cursor.execute(channel_id_query)
        channel_id = cursor.fetchone()
        channel_id = channel_id[0]
        post_id = f"{str(channel_id)}_{str(key)}"
        write_query = f"INSERT INTO raw_posts(post_id, user_id, group_id) VALUES ('{post_id}', {user_id}, {channel_id})"
        cursor.execute(write_query)
        if "text" in value:
            cursor.execute(f"SELECT * FROM items WHERE item = '{value['text']}'")
            if not cursor.fetchall():
                write_query = f"INSERT INTO items(item, post_id, type) VALUES ('{value['text']}', '{post_id}', 'text')"
                cursor.execute(write_query)
        for url in value["urls"]:
            cursor.execute(f"SELECT * FROM items WHERE item = '{url}'")
            if not cursor.fetchall():
                write_query = f"INSERT INTO items(item, post_id, type) VALUES ('{url}', '{post_id}', 'url')"
                cursor.execute(write_query)
    conn.commit()
