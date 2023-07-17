import grpc
import selector_pb2 as your_proto
import selector_pb2_grpc as your_proto_grpc
import os
import time
import threading



def pingSelector(selector_id,user_id):
    # t = threading.Thread(target=pingThread, args=(selector_id,user_id))
    # t.start()
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




