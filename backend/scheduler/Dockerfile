FROM python:latest

COPY requirements.txt .
RUN pip3 install --user -r requirements.txt

ADD scheduler.py /
ADD selector_pb2_grpc.py /
ADD selector_pb2.py /

# RUN python3 main.py