FROM python:latest

COPY requirements.txt .
RUN pip3 install --user -r requirements.txt

ADD bot.py /
ADD core.py /
ADD check_date.py /
ADD get_posts.py /
ADD MediaParser.py /
ADD selector_pb2_grpc.py /
ADD selector_pb2.py /
ADD ml_pb2_grpc.py /
ADD ml_pb2.py /
ADD telegram_module.py /
ADD db.py /
ADD config.ini /
ADD +79858805091.session /

ADD .env /

WORKDIR /

COPY run.sh run.sh
# CMD chmod +x run.sh
CMD bash ./run.sh

# CMD [ "python3" , "bot.py &"]