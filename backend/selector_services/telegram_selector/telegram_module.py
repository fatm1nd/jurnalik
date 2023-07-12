import db
import get_posts


def handle_one(user_id):
    channels = db.get_channels(user_id)
    for channel_id in channels:
        posts = get_posts.collect_posts(channel_id)
        db.write_posts(user_id, channel_id, posts)


def handle_all():
    ids = db.get_ids()
    for id in ids:
        handle_one(id)