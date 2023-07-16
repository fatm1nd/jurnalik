import grpc
import selector_pb2_grpc as your_proto_grpc
import selector_pb2 as your_proto
import telegram_module

import ml_pb2_grpc as ml_grpc
import ml_pb2 as ml_proto

from concurrent import futures
import threading
import time
import psycopg2
from dotenv import dotenv_values
import telegram_module as tm

# python3 -m grpc_tools.protoc -I ../schema/ --python_out=. --grpc_python_out=. ../schema/ml.proto

config = dotenv_values(".env")


HOST = config["POSTGRES_HOST"]
PORT = config["POSTGRES_PORT"]
USER = config["POSTGRES_USER"]
PASSWORD = config["POSTGRES_PASSWORD"]
DATABASE = config["POSTGRES_DATABASE"]
# print(config, flush=True)

print(
    f"Telegram Selector Module is ready. DB is on {config['POSTGRES_HOST']}", flush=True)


def ping_ML_module_all():
    channel = grpc.insecure_channel(f'{"mlservice"}:{"50051"}')
    stub = ml_grpc.MLStub(channel)
    selector_ping = your_proto.Empty()
    stub.PingAll(selector_ping)


def ping_ML_module_one(user_id):
    channel = grpc.insecure_channel(f'{"mlservice"}:{"50051"}')
    stub = ml_grpc.MLStub(channel)
    selector_ping = ml_proto.SelectorPing()
    selector_ping.user = str(user_id)
    selector_ping.source = ml_proto.SelectorPing.Source.Value("TG")
    stub.PingOne(selector_ping)


class SelectorServicer(your_proto_grpc.SelectorServicer):
    def SelectOne(self, request, context):
        # print(f"Start selecting for user {request.user}",flush=True)
        # tm.handle_one(request.user)
        # print(f"Stop selecting for user {request.user}",flush=True)
        thread = threading.Thread(
            target=self.process_ping_one, args=(request,))
        thread.start()
        return your_proto.Empty()

    def process_ping_one(self, ping):
        # print(type(user_id), flush=True)
        user_id = str(ping.user)
        # # STARING SELECTING FOR USER
        # print(f"Start selecting for user {user_id}",flush=True)
        # tm.handle_one(user_id)
        # print(f"Stop selecting for user {user_id}",flush=True)
        # # END SELECTING
        print("Start downloading for one", flush=True)
        telegram_module.handle_one(user_id)
        print("Stop downloading for one", flush=True)
        print("Alerting ML about done work", flush=True)
        ping_ML_module_one(user_id)
        print("Alerted ML about donw work", flush=True)

    def SelectAll(self, request, context):
        thread = threading.Thread(target=self.process_ping_all)
        thread.start()
        return your_proto.Empty()

    def process_ping_all(self):
        print(f"Start selecting for all", flush=True)
        tm.handle_all()
        ping_ML_module_all()
        print(f"Stop selecting for all", flush=True)


server = grpc.server(futures.ThreadPoolExecutor())
your_proto_grpc.add_SelectorServicer_to_server(
    SelectorServicer(), server)
server.add_insecure_port('[::]:50051')
server.start()
print("gRPC server is starting", flush=True)
server.wait_for_termination()
