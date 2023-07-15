from flask import Flask, jsonify, request
import time
import client_db as db
import selector_pb2 as sel_proto
import selector_pb2_grpc as sel_grpc
import grpc_client
import dotenv
import requests

print(f"Client Module is ready",flush=True)
# db = client_db.DataBase()
# print(db.addUser(),flush=True)

from dotenv import dotenv_values

config = dotenv_values(".env")

print(f"ML Module is ready. DB is on {config['POSTGRES_HOST']}",flush=True)

VK_APPLICATION_ID = config['VK_APPLICATION_ID']
VK_APPLICATION_SECRET = config["VK_APPLICATION_ID"]

app = Flask(__name__)

@app.route('/')
def index():
    return "Hello, Jurnalik!"


@app.route('/user/new')
def newUser():
    return jsonify(db.DataBase().addUser())

@app.route('/user/<int:user_id>/sources')
def getSources(user_id):
    '''
    Source → Enum
    0 - Telegram
    1 - VK
    2 - Inst
    '''
    return jsonify(db.DataBase().getUsersSources(user_id))

@app.route('/user/<int:user_id>/work/ping')
def pingJurnalik(user_id):
    grpc_client.pingSelector(0,user_id)
    # grpc_client.pingSelector(1,user_id)
    return jsonify(True)

# TODO Удалить счетчик
TEST_COUNTER = 0

@app.route('/user/<int:user_id>/work/check')
def checkJurnalik(user_id):
    return jsonify(db.DataBase().checkPreparedPostsForUser(user_id))

@app.route('/user/<int:user_id>/posts')
def getPosts(user_id):
    # posts = [{'post_id':"2313123","group_id":"8217389712839",\
    #           "items":[{"item":"Some cool text","type":"text"},{"item":"https://cdn4.telegram-cdn.org/file/K5ZzQIE3z5f7sa09Dapztm7Npe1bcIV4mkyRo4WT1XzC5XOILcTVUcJxKlqRdGID4VOy4eFFDKDgxK5GZGleL_FrHsiK4CrbnfTSzdEz6GajFkeU3VNxjhOi63SPsRGjXEZFZeqo_elP3E7uA7XHksQbG2nLssKQoZN_jbvBDFcMpJ7kJaB8LxVqdsxydGi-Ct7QKQGNnewHP73xR29WFuRFpwO2piJs7pOI-XueAtT6zHJjqYmr8GEpvY-Km7jzzy_NWXV6mHwB_-r_WuwJFse6gFk5E60hOkpeIsaA5-E5rI8dEB9MHb5FOOhEWdERhkJYTw2raD9fwCDF3ZHnAw.jpg","type":"url"}],"category":"news"},\
    #             {'post_id':"2313123","group_id":"3129837123987",\
    #           "items":[{"item":"Some another cool text","type":"text"},\
    #                    {"item":"https://cdn4.telegram-cdn.org/file/K5ZzQIE3z5f7sa09Dapztm7Npe1bcIV4mkyRo4WT1XzC5XOILcTVUcJxKlqRdGID4VOy4eFFDKDgxK5GZGleL_FrHsiK4CrbnfTSzdEz6GajFkeU3VNxjhOi63SPsRGjXEZFZeqo_elP3E7uA7XHksQbG2nLssKQoZN_jbvBDFcMpJ7kJaB8LxVqdsxydGi-Ct7QKQGNnewHP73xR29WFuRFpwO2piJs7pOI-XueAtT6zHJjqYmr8GEpvY-Km7jzzy_NWXV6mHwB_-r_WuwJFse6gFk5E60hOkpeIsaA5-E5rI8dEB9MHb5FOOhEWdERhkJYTw2raD9fwCDF3ZHnAw.jpg","type":"url"}],\
    #                     "category":"news"}]
    posts = db.DataBase().getCompletePostsForUser(user_id)
    return jsonify(posts)

@app.route('/user/<int:user_id>/groups')
def getGroups(user_id):
    # groups = [{'group_id':"8217389712839","group_name":"Офигенная группа из ТГ","picture":"https://cdn4.telegram-cdn.org/file/mmbNmK_bHW_sqT4aFR5pC_BUftg4IdrVrAR9AvyGmulpkHyVtkeeYuuI4Mj7wKUl27sqmxE2-wORX6RLq_zP3UhTMk3AVjlw1cV5k3EOwPD9jLwgV0FKFrXUU-J19CGpumi-p38VD442ZKouJzxfRViNRze7um61fvs6fmFBlJfv9t0kajuBdMobPokAo1cLPGwqStAj8T33QVzfevVSZlNacO4r14Rxvg8maGC0CeJsaYlAgPFcr7ogK5PgD7YguHbIAYsIDDUkx9k9-2ONNiEx87TTUwj0b8tF6933JI1tnVtbvlx0zJ0WJuh3LeYVaQYhVk-Hp3CboK4ZlISbuA.jpg","source":"Telegram"},\
    #           {'group_id':"3129837123987","group_name":"Невероятная группа из VK","picture":"https://yandex.ru/images/search?text=обезьяна+улыбается+картинка&img_url=https%3A%2F%2Fkrot.info%2Fuploads%2Fposts%2F2022-03%2F1646146774_35-krot-info-p-smeshnaya-ulibka-smeshnie-foto-38.jpg&pos=1&rpt=simage&stype=image&lr=121642&parent-reqid=1689281633196850-3016692286679148818-balancer-l7leveler-kubr-yp-vla-89-BAL-357&source=serp","source":"VK"}]
    groups = db.DataBase().getGroupsByUserId(user_id)
    return jsonify(groups)

@app.route('/user/VK')
def vkAuth():
    # request.args.get('username')
    print(request.args, flush=True)
    code = request.args.get('code')
    user_id = request.args.get('state')
    print(f"https://oauth.vk.com/access_token?client_id={VK_APPLICATION_ID}&client_secret={VK_APPLICATION_SECRET}&redirect_uri=http://85.234.110.105/user/VK&code={code}", flush=True)
    r = requests.get(f"https://oauth.vk.com/access_token?client_id={VK_APPLICATION_ID}&client_secret={VK_APPLICATION_SECRET}&redirect_uri=http://85.234.110.105/user/VK&code={code}")
    response = r.json()
    print(response, flush=True)
    token = response['access_token']
    vk_id = response['user_id']
    if db.DataBase().authVkUser(user_id,vk_id,token):
        return "Вы успешно авторизированы! Можете возвращаться в приложение!"


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=80,debug=True)