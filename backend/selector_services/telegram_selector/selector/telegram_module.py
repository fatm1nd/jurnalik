import db
import get_posts


def handle_one(user_id):
    channels = db.get_channels(user_id)
    for channel_username in channels:
        posts = get_posts.collect_posts(channel_username)
        db.write_posts(user_id, channel_username, posts)


def handle_all():
    ids = db.get_ids()
    for id in ids:
        handle_one(id)
