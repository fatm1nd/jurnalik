from html.parser import HTMLParser
from lxml import objectify
import requests
import xml.etree.ElementTree as ET
import re
from bs4 import BeautifulSoup
import re
import check_date


def parse_media(url, desired_date):
    response = requests.get(url, headers={"Accept": 'application/json'})
    main = BeautifulSoup(response.content.decode('utf-8'), features='lxml')
    postsRaw = (main.find_all('div', attrs={"class": "tgme_widget_message"}))
    post_media = []
    for post in postsRaw[::-1]:
        date_element = post.find('time', attrs={'class': 'time'})
        post_date = check_date.extract_datetime_info(str(date_element))
        if desired_date == post_date:
            reg = "(?:\(['\"]?)(.*?)(?:['\"]?\))"
            pictures = post.find_all('a', attrs={'class': 'tgme_widget_message_photo_wrap'})
            # print(*pictures, sep="\n")
            for picture in pictures:
                post_media.append(re.search(reg, picture['style']).group(1))
            for video in post.find_all('video'):
                post_media.append(video['src'])
        elif post_date < desired_date:
            break
    return post_media
