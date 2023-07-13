from flask import Flask, jsonify, request
'''
- /user/new → int:id 
Создает нового пользователя в базе и возвращает его id
- /user/<user_id>/sources → list[source]
Возвращает список авторизованных соц. сетей для конкретного пользователя
- /user/<user_id>/work/ping →bool:flag
Дает сервису понять, что пользователь авторизировался во всех соц. сетях и можно начинать сборку новостей
- user/<user_id>/work/check → bool:flag
Возвращает True/False, закончил ли сервис собирать новости для конкретного пользователя (True - закончил сборку и обработку, False - еще в процессе соборки и обработки)
- /user/<user_id>/posts → list[Post]
Возвращает список всех постов для пользователя на этот день
- /user/<user_id>/groups → list[Groups]
Возвращает список групп пользователя
- /user/<user_id>/VK → html-страница?
Метод нужен для авторизации в сервисе через Вконтакте

'''


app = Flask(__name__)

@app.route('/')
def index():
    return "Hello, Jurnalik!"


@app.route('/user/new')
def newUser():
    return 4207

@app.route('/user/<int:user_id>/sources')
def getSources(user_id):
    '''
    Source → Enum
    0 - Telegram
    1 - VK
    2 - Inst
    '''
    return jsonify(int(user_id) % 3)

@app.route('/user/<int:user_id>/work/ping')
def pingJurnalik(user_id):
    return jsonify(True)

# TODO Удалить счетчик
TEST_COUNTER = 0

@app.route('/user/<int:user_id>/work/check')
def checkJurnalik(user_id):
    global TEST_COUNTER
    if TEST_COUNTER < 4:
        TEST_COUNTER += 1
        return jsonify(False)
    else:
        TEST_COUNTER = 0
        return jsonify(True)

@app.route('/user/<int:user_id>/posts')
def getPosts(user_id):
    posts = [{'post_id':"2313123","group_id":"8217389712839",\
              "items":[{"item":"Some cool text","type":"text"},{"item":"https://cdn4.telegram-cdn.org/file/K5ZzQIE3z5f7sa09Dapztm7Npe1bcIV4mkyRo4WT1XzC5XOILcTVUcJxKlqRdGID4VOy4eFFDKDgxK5GZGleL_FrHsiK4CrbnfTSzdEz6GajFkeU3VNxjhOi63SPsRGjXEZFZeqo_elP3E7uA7XHksQbG2nLssKQoZN_jbvBDFcMpJ7kJaB8LxVqdsxydGi-Ct7QKQGNnewHP73xR29WFuRFpwO2piJs7pOI-XueAtT6zHJjqYmr8GEpvY-Km7jzzy_NWXV6mHwB_-r_WuwJFse6gFk5E60hOkpeIsaA5-E5rI8dEB9MHb5FOOhEWdERhkJYTw2raD9fwCDF3ZHnAw.jpg","type":"url"}],"category":"news"},\
                {'post_id':"2313123","group_id":"3129837123987",\
              "items":[{"item":"Some another cool text","type":"text"},\
                       {"item":"https://cdn4.telegram-cdn.org/file/K5ZzQIE3z5f7sa09Dapztm7Npe1bcIV4mkyRo4WT1XzC5XOILcTVUcJxKlqRdGID4VOy4eFFDKDgxK5GZGleL_FrHsiK4CrbnfTSzdEz6GajFkeU3VNxjhOi63SPsRGjXEZFZeqo_elP3E7uA7XHksQbG2nLssKQoZN_jbvBDFcMpJ7kJaB8LxVqdsxydGi-Ct7QKQGNnewHP73xR29WFuRFpwO2piJs7pOI-XueAtT6zHJjqYmr8GEpvY-Km7jzzy_NWXV6mHwB_-r_WuwJFse6gFk5E60hOkpeIsaA5-E5rI8dEB9MHb5FOOhEWdERhkJYTw2raD9fwCDF3ZHnAw.jpg","type":"url"}],\
                        "category":"news"}]
    return jsonify(posts)

@app.route('/user/<int:user_id>/groups')
def getGroups(user_id):
    groups = [{'group_id':"8217389712839","group_name":"Офигенная группа из ТГ","picture":"https://cdn4.telegram-cdn.org/file/mmbNmK_bHW_sqT4aFR5pC_BUftg4IdrVrAR9AvyGmulpkHyVtkeeYuuI4Mj7wKUl27sqmxE2-wORX6RLq_zP3UhTMk3AVjlw1cV5k3EOwPD9jLwgV0FKFrXUU-J19CGpumi-p38VD442ZKouJzxfRViNRze7um61fvs6fmFBlJfv9t0kajuBdMobPokAo1cLPGwqStAj8T33QVzfevVSZlNacO4r14Rxvg8maGC0CeJsaYlAgPFcr7ogK5PgD7YguHbIAYsIDDUkx9k9-2ONNiEx87TTUwj0b8tF6933JI1tnVtbvlx0zJ0WJuh3LeYVaQYhVk-Hp3CboK4ZlISbuA.jpg","source":"Telegram"},\
              {'group_id':"3129837123987","group_name":"Невероятная группа из VK","picture":"https://yandex.ru/images/search?text=обезьяна+улыбается+картинка&img_url=https%3A%2F%2Fkrot.info%2Fuploads%2Fposts%2F2022-03%2F1646146774_35-krot-info-p-smeshnaya-ulibka-smeshnie-foto-38.jpg&pos=1&rpt=simage&stype=image&lr=121642&parent-reqid=1689281633196850-3016692286679148818-balancer-l7leveler-kubr-yp-vla-89-BAL-357&source=serp","source":"VK"}]
    return jsonify(groups)

@app.route('/user/<int:user_id>/VK')
def vkAuth(user_id):
    # request.args.get('username')
    # https://oauth.vk.com/authorize?client_id=51676262&display=mobile&redirect_uri=https://oauth.vk.com/blank.html&scope=335872&response_type=token&v=5.131&userid=1323123
    
    return f"Авторизация прошла успешно для пользователя {user_id}"

if __name__ == '__main__':
    app.run(debug=True)