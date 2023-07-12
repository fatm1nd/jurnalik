FROM python:latest

COPY requirements.txt .
RUN pip3 install --user -r requirements.txt

ADD main.py /
ADD jurnalik_ml.py /
ADD model.pkl /
ADD .env /
ADD ./ml_pb2_grpc.py /
ADD ./ml_pb2.py /

WORKDIR /

# CMD [ "python3" , "main.py"]