import pandas as pd
from langdetect import detect
import pymorphy3
import pickle
import psycopg2
from sklearn.feature_extraction.text import TfidfVectorizer
import nltk
from nltk.tokenize import RegexpTokenizer
from nltk.stem import PorterStemmer
from dotenv import dotenv_values

nltk.download('stopwords')
# nltk.download('punkt')
# from nltk.corpus import stopwords

config = dotenv_values(".env")

HOST = config["POSTGRES_HOST"]
PORT = config["POSTGRES_PORT"]
USER = config["POSTGRES_USER"]
PASSWORD = config["POSTGRES_PASSWORD"]
DATABASE = config["POSTGRES_DATABASE"]


def tokenizer_data(data):
    tokenizer = RegexpTokenizer(r'\w+')
    return data['text_lower'].apply(lambda x: tokenizer.tokenize(x))


def stop_words_clean(text):
    print(text,flush=True)
    language = detect(" ".join(text))

    if language == 'en':
        stop_words = set(nltk.corpus.stopwords.words("english"))
        return [item for item in text if item not in stop_words]

    stop_words = set(nltk.corpus.stopwords.words("russian"))
    return [item for item in text if item not in stop_words]


def normalize_tokens(text):
    print(text,flush=True)
    language = detect(" ".join(text))

    if language == 'en':
        stemmer = PorterStemmer()
        return [stemmer.stem(y) for y in text]

    morph = pymorphy3.MorphAnalyzer()
    return [morph.parse(tok)[0].normal_form for tok in text]


def preprocessing(data):
    data['text_lower'] = data['text'].apply(lambda x: x.lower())
    data['text_clean'] = tokenizer_data(data)
    data = data.drop(['text_lower'], axis=1)
    for i in range(len(data['text_clean'])):
        buffer = stop_words_clean(data['text_clean'][i])
        if len(buffer) != 0:
            data['text_clean'][i] = buffer
        data['text_clean'][i] = normalize_tokens(data['text_clean'][i])

    data['text_clean'] = data['text_clean'].apply(lambda x: " ".join(x).lower())

    return data


def remove_duplicates(data):
    vect = TfidfVectorizer(min_df=1)
    tfidf = vect.fit_transform(data)
    pairwise_similarity = tfidf * tfidf.T

    sim = pairwise_similarity.toarray()
    to_remove = []

    for i in range(len(sim)):
        for j in range(len(sim[0])):
            if i != j and sim[i][j] > 0.4:
                if len(data[i]) == len(data[j]):
                    to_remove.append(min(i, j))
                elif len(data[i]) > len(data[j]):
                    to_remove.append(j)
                else:
                    to_remove.append(i)

    return list(set(to_remove))


def decoder(label):
    labels = {0: 'accident', 1: 'business', 2: 'entertainment', 3: 'politics', 4: 'spam', 5: 'sport', 6: 'technology'}
    return labels[label]


def delete_data():
    con = psycopg2.connect(database=DATABASE, user=USER, password=PASSWORD, host=HOST, port=PORT)
    cur = con.cursor()

    cur.execute("DELETE FROM prepared_posts")

    con.commit()


def get_one(user_id):
    print(user_id)

    con = psycopg2.connect(database="jurnalik", user="postgres", password="13062013", host="127.0.0.1", port="5432")
    cur = con.cursor()

    data = {'user_id': [], 'post_id': [], 'text': []}

    cur.execute("SELECT post_id FROM raw_posts WHERE user_id=%s", (int(user_id),))
    post_ids = cur.fetchall()

    for post_id in post_ids:
        cur.execute("SELECT item FROM raw_posts as p \
                JOIN items as i ON i.post_id=p.post_id WHERE user_id=%s AND p.post_id = %s AND type ='text'",
                    (user_id, post_id))
        news = cur.fetchall()
        if len(news) == 0:
            data['user_id'].append(user_id)
            data['post_id'].append(post_id)
            data['text'].append(
                'Disney показал трейлер финального эпизода мультсериала «Удивительный мир Микки Мауса», в котором мышонок столкнётся с мультивселенной. По сюжету Микки Маус и его друзья должны помешать сотням копий старого Микки из культового «Пароходика Вилли» сеять хаос в городе. Серия выйдет 28 июля на Disney+')
        else:
            data['user_id'].append(user_id)
            data['post_id'].append(post_id)
            data['text'].append(news[0][0])

    con.close()
    return pd.DataFrame(data=data)


def get_all():
    con = psycopg2.connect(database="jurnalik", user="postgres", password="13062013", host="127.0.0.1", port="5432")
    cur = con.cursor()

    arr_data = []

    cur.execute("DELETE FROM prepared_posts")

    cur.execute("SELECT DISTINCT user_id FROM raw_posts as p")
    user_ids = cur.fetchall()

    for user_id in user_ids:
        data = {'user_id': [], 'post_id': [], 'text': []}

        cur.execute("SELECT post_id FROM raw_posts WHERE user_id=%s", (user_id))
        post_ids = cur.fetchall()

        for post_id in post_ids:
            cur.execute("SELECT item FROM raw_posts as p \
                    JOIN items as i ON i.post_id=p.post_id WHERE user_id=%s AND p.post_id = %s AND type ='text'",
                        (user_id, post_id))
            news = cur.fetchall()
            if len(news) == 0:
                data['user_id'].append(user_id)
                data['post_id'].append(post_id)
                data['text'].append('Disney показал трейлер финального эпизода мультсериала «Удивительный мир Микки Мауса», в котором мышонок столкнётся с мультивселенной. По сюжету Микки Маус и его друзья должны помешать сотням копий старого Микки из культового «Пароходика Вилли» сеять хаос в городе. Серия выйдет 28 июля на Disney+')
            else:
                data['user_id'].append(user_id)
                data['post_id'].append(post_id)
                data['text'].append(news[0][0])

        arr_data.append(pd.DataFrame(data=data))
    con.commit()
    return arr_data


def push(data, user_id):
    con = psycopg2.connect(database="jurnalik", user="postgres", password="13062013", host="127.0.0.1", port="5432")
    cur = con.cursor()

    arr_remove = remove_duplicates(data['text_clean'])

    cur.execute("SELECT * FROM raw_posts WHERE user_id=%s", (user_id))
    insert_data = cur.fetchall()

    for j in range(len(insert_data)):
        if data['category'][j] != 'spam' and j not in arr_remove:
            cur.execute("INSERT INTO prepared_posts(post_id, user_id, group_id, category) VALUES (%s, %s, %s, %s);",
                        (insert_data[j][0], insert_data[j][1], insert_data[j][2], data['category'][j]))
        elif data['text'][
            j] == 'Disney показал трейлер финального эпизода мультсериала «Удивительный мир Микки Мауса», в котором мышонок столкнётся с мультивселенной. По сюжету Микки Маус и его друзья должны помешать сотням копий старого Микки из культового «Пароходика Вилли» сеять хаос в городе. Серия выйдет 28 июля на Disney+':
            cur.execute("INSERT INTO prepared_posts(post_id, user_id, group_id, category) VALUES (%s, %s, %s, %s);",
                        (insert_data[j][0], insert_data[j][1], insert_data[j][2], data['category'][j]))
    con.commit()


def run_one(user_id):
    with open("model.pkl", 'rb') as file:
        model = pickle.load(file)

    data = get_one(user_id)

    data = preprocessing(data)

    data_test = data['text_clean']

    data['category'] = model.predict(data_test)
    data['category'] = data['category'].apply(lambda x: decoder(x))
    push(data, str(data['user_id'][0]))


def run_all():
    with open("model.pkl", 'rb') as file:
        model = pickle.load(file)

    data_arr = get_all()

    for data in data_arr:
        data = preprocessing(data)
        data_test = data['text_clean']
        data['category'] = model.predict(data_test)
        data['category'] = data['category'].apply(lambda x: decoder(x))

        push(data, data['user_id'][0])

