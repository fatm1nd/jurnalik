import grpc
import selector_pb2 as example_pb2
import selector_pb2_grpc as example_pb2_grpc
import schedule
import threading
import time
import os
import threading
from datetime import datetime


time.sleep(7)
    


def main():
    # Create a thread to send requests periodically
    print("Scheduler Service is running.", flush=True)
    
    print('Schedule the ping')
    schedule.every().day.at("21:00").do(pingSelectors)
    print("Waiting...", flush=True)
    while True:
        schedule.run_pending()
        time.sleep(1)






def pingSelectors():
    print("Initiale 'SelectAll' pings", flush=True)
    print(" - Initiale ping for Telegram", flush=True)
    
    channel = grpc.insecure_channel(f'{"telegram_selector"}:{"50051"}')
    stub = example_pb2_grpc.SelectorStub(channel)
    selector_ping = example_pb2.Empty()
    response = stub.SelectAll(selector_ping)


    print(" - Initiale ping for VK", flush=True)
    current_time = datetime.now()
    formatted_time = current_time.strftime("%Y-%m-%d %H:%M:%S")

    print("Current time:", formatted_time, flush=True)
    
    channel = grpc.insecure_channel(f'{"vk_selector"}:{"50051"}')
    stub = example_pb2_grpc.SelectorStub(channel)
    selector_ping = example_pb2.Empty()
    response = stub.SelectAll(selector_ping)

    print("All services get whole info. ML Module should start working", flush=True)


def pingThread(selector_id):
    host = ''
    if (selector_id == 0):
        host = ''
    else:
        host = 'vk_selector'
    
    

    current_time = datetime.now()
    formatted_time = current_time.strftime("%Y-%m-%d %H:%M:%S")

    print("Current time:", formatted_time)
    
    channel = grpc.insecure_channel(f'{host}:{"50051"}')
    stub = example_pb2_grpc.SelectorStub(channel)
    selector_ping = example_pb2.Empty()
    response = stub.SelectAll(selector_ping)



def pingSelectorsForUser(user_id,sources):
    t = threading.Thread(target=pingSelectorsForUserThread, args=(user_id, sources))


def pingSelectorsForUserThread(user_id,sources):
    for source in sources:
        if (source == 'tg'):
            host = 'telegram_selector'
        elif (source == 'vk'):
            host = 'vk_selector'
        else:
            print("There is no social media to select. Check smth", flush=True) 
            continue
        
        
        channel = grpc.insecure_channel(f'{host}:{"50051"}')
        stub = example_pb2_grpc.SelectorStub(channel)
        selector_ping = example_pb2.User()
        selector_ping.user = str(user_id)
        response = stub.SelectOne(selector_ping)



if __name__ == '__main__':
    main()
