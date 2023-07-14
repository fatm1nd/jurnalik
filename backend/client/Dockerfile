FROM python:latest

COPY requirements.txt .
RUN pip3 install --user -r requirements.txt

ADD main.py /
ADD post.py /
ADD client_db.py /
ADD .env /

# RUN python3 main.py