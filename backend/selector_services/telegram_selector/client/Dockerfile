FROM python:latest

COPY requirements.txt .
RUN pip3 install --user -r requirements.txt

ADD ./client.py /
ADD ./ml_pb2_grpc.py /
ADD ./ml_pb2.py /
ADD ./selector_pb2_grpc.py /
ADD ./selector_pb2.py /

WORKDIR /

# CMD [ "python3" , "client.py"]