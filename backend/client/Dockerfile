FROM python:latest

COPY requirements.txt .
RUN pip3 install --user -r requirements.txt

ADD main.py /
ADD client_db.py /
ADD .env /
ADD selector_pb2_grpc.py /
ADD selector_pb2.py /
ADD ml_pb2_grpc.py /
ADD ml_pb2.py /
ADD grpc_client.py /

# RUN python3 main.py