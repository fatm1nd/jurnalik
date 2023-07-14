import psycopg2
from dotenv import dotenv_values

config = dotenv_values(".env")

# print(config,flush=True)
HOST = config["POSTGRES_HOST"]
PORT = config["POSTGRES_PORT"]
USER = config["POSTGRES_USER"]
PASSWORD = config["POSTGRES_PASSWORD"]
DATABASE = config["POSTGRES_DATABASE"]
HOST = "localhost"
# con = psycopg2.connect(database=DATABASE, user=USER, password=PASSWORD, host=HOST, port=PORT)
# cur = con.cursor()
# data = {'user_id': [], 'post_id': [], 'text': []}
# cur.execute("SELECT user_id , p.post_id, item FROM raw_posts as p \
# JOIN items as i ON i.post_id=p.post_id WHERE user_id=%s AND type ='text'", (user_id))
# news = cur.fetchall()
# for n in news:
#     data['user_id'].append(n[0])
#     data['post_id'].append(n[1])
#     data['text'].append(n[2])
# con.close()

class DataBase():

    con = None

    def __init__(self):
        self.con = psycopg2.connect(database=DATABASE, user=USER, password=PASSWORD, host=HOST, port=PORT)

    def addUser(self):
        self.con = psycopg2.connect(database=DATABASE, user=USER, password=PASSWORD, host=HOST, port=PORT)
        # INSERT INTO full_users_ids (user_id, vk_id,telegram_id,instagram_id) VALUES ((SELECT MAX(user_id) + 1 FROM full_users_ids), null, null, null)
        cur = self.con.cursor()
        cur.execute("INSERT INTO full_users_ids (user_id, vk_id,telegram_id,instagram_id) VALUES ((SELECT MAX(user_id) + 1 FROM full_users_ids), null, null, null)")
        cur.execute("SELECT MAX(user_id) FROM full_users_ids")
        result = cur.fetchone()
        self.con.commit()
        self.con.close()
        return result[0]

    def getPostsByUserId(self,user_id):
        self.con = psycopg2.connect(database=DATABASE, user=USER, password=PASSWORD, host=HOST, port=PORT)
        # SELECT prepared_posts.post_id, prepared_posts.group_id, prepared_posts.category FROM prepared_posts JOIN full_users_ids ON prepared_posts.user_id = full_users_ids.user_id
        cur = self.con.cursor()
        cur.execute("SELECT prepared_posts.post_id, prepared_posts.group_id, prepared_posts.category FROM prepared_posts JOIN full_users_ids ON prepared_posts.user_id = full_users_ids.user_id WHERE full_users_ids.user_id = '%s'",(int(user_id),))
        postsq = cur.fetchall()
        result = []
        print(result)
        self.con.close()

    def getItemsByUserId(self, user_id):
        self.con = psycopg2.connect(database=DATABASE, user=USER, password=PASSWORD, host=HOST, port=PORT)
        cur = self.con.cursor()

        cur.execute("SELECT pp.post_id, item, type FROM items JOIN prepared_posts pp on items.post_id = pp.post_id WHERE pp.user_id = '%s',",(int(user_id),))
        itemsq = cur.fetchall()
        
    def getGroupsByYserId(self, user_id):
        self.con = psycopg2.connect(database=DATABASE, user=USER, password=PASSWORD, host=HOST, port=PORT)
        cur = self.con.cursor()
        cur.execute("SELECT pp.post_id, group_name, picture, source FROM groups_and_channels JOIN prepared_posts pp on groups_and_channels.group_id = pp.group_id WHERE pp.user_id = '%s',",(int(user_id),))
        groupsq = cur.fecthall()


db = DataBase()

print(db.getPostsByUserId(207))