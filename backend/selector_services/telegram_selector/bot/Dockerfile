FROM python:latest

COPY requirements.txt .
RUN pip3 install --user -r requirements.txt


ADD bot.py /
ADD db.py /
ADD config.ini /
ADD selector_pb2_grpc.py /
ADD selector_pb2.py /
ADD .env /

WORKDIR /

# CMD [ "python3" , "bot.py &"]