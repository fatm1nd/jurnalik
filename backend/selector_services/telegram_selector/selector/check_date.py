import re
from datetime import datetime


def extract_datetime_info(html_string):
    pattern = r'datetime="(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2})'
    match = re.search(pattern, html_string)

    if match:
        year, month, day, hour, minute, second = map(int, match.groups())
        return datetime(year, month, day, hour, minute)
    else:
        raise ValueError("Datetime not found in the provided string.")