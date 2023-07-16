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
    print("Scheduler Service is running. Start initial 'SelectAll' ping.", flush=True)
    thread = threading.Thread(target=pingSelectors)
    thread.start()
    print('Initial ping is sent.', flush=True)
    print('Schedule the ping')
    schedule.every().day.at("00:00").do(pingSelectors)
    print("Waiting...", flush=True)
    while True:
        schedule.run_pending()
        time.sleep(1)






def pingSelectors():
    print("Initiale 'SelectAll' pings", flush=True)
    print(" - Initiale ping for Telegram", flush=True)
    t = threading.Thread(target=pingThread, args=(0,))
    t.start()
    print(" - Initiale ping for VK", flush=True)
    t2 = threading.Thread(target=pingThread, args=(1,))
    t2.start()

def pingThread(selector_id):
    host = ''
    if (selector_id == 0):
        host = 'telegram_selector'
    else:
        host = 'vk_selector'
    
    

    current_time = datetime.now()
    formatted_time = current_time.strftime("%Y-%m-%d %H:%M:%S")

    print("Current time:", formatted_time)
    
    channel = grpc.insecure_channel(f'{host}:{"50051"}')
    stub = example_pb2_grpc.SelectorStub(channel)
    selector_ping = example_pb2.Empty()
    response = stub.SelectAll(selector_ping)







if __name__ == '__main__':
    main()
