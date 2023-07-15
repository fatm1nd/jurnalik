import grpc
import selector_pb2 as your_proto
import selector_pb2_grpc as your_proto_grpc
import os
import time
import threading



def pingSelector(selector_id,user_id):
    # server_host = os.getenv('GRPC_SERVER_HOST', 'localhost')
    # server_port = os.getenv('GRPC_SERVER_PORT', '50051')
    t = threading.Thread(target=pingThread, args=(selector_id,user_id))
    t.start()

    # empty_request = your_proto.Empty()
    # response = stub.PingAll(empty_request)
    # print("SelectAll response:", response)

    # empty_request = your_proto.Empty()
    # response = stub.PingAll(empty_request)
    # print("SelectAll response:", response)

def pingThread(selector_id,user_id):
    host = ''
    if (selector_id == 0):
        host = 'telegram_selector'
    else:
        host = 'vk_selector'
    
    
    channel = grpc.insecure_channel(f'{host}:{"50051"}')
    stub = your_proto_grpc.SelectorStub(channel)
    selector_ping = your_proto.User()
    selector_ping.user = str(user_id)
    response = stub.SelectOne(selector_ping)




