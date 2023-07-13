import jurnalik_ml
import grpc
import ml_pb2_grpc as your_proto_grpc
import ml_pb2 as your_proto
from concurrent import futures
import threading 
import time
import psycopg2
from dotenv import dotenv_values

# python3 -m grpc_tools.protoc -I ../schema/ --python_out=. --grpc_python_out=. ../schema/ml.proto

GLOBAL_COUNTER = 0
AMOUNT_OF_SOURCES = 2

USERS_COUNTERS = {}

config = dotenv_values(".env")

print(f"ML Module is ready. DB is on {config['HOST']}",flush=True)

HOST = config["HOST"]
PORT = config["PORT"]
USER = config["USER"]
PASSWORD = config["PASSWORD"]
DATABASE = config["DATABASE"]

class MLServicer(your_proto_grpc.MLServicer):
    def PingOne(self,request, context):
        thread = threading.Thread(target=self.process_ping_one, args=(request,))
        thread.start()
        return your_proto.Empty()

    def getAuthesOfUser(self,user_id):
        con = psycopg2.connect(database=DATABASE, user=USER, password=PASSWORD, host=HOST, port=PORT)
        cur = con.cursor()
        print(f"Getting count for {user_id} and {type(user_id)}", flush=True)
        cur.execute("SELECT * FROM full_users_ids WHERE user_id=%s",(str(user_id),))
        sources = cur.fetchone()
        con.close()

        counterL = [1 if sources[i] != None else 0 for i in range(1,len(sources))]
        return sum(counterL)

    def process_ping_one(self, ping):
        # print(type(user_id), flush=True)
        user_id = str(ping.user)
        source = ping.source
        print(f"{user_id}: user_id -> ",ping.user, flush=True)
        print(f"{user_id}: source -> ",ping.source, flush=True)
        
        counter = self.getAuthesOfUser(user_id)

        if not user_id in USERS_COUNTERS:
            USERS_COUNTERS[user_id] = 0

        
        if counter == USERS_COUNTERS[user_id] + 1:
            print(f"{user_id}:Start copywriting for {user_id}", flush=True)
            # RUNNING ML MODULE
            jurnalik_ml.run_one((user_id,))
            # END OF RUNNING
            print(f"{user_id}:End copywriting for {user_id}")
            USERS_COUNTERS[user_id] = 0
        else:
            USERS_COUNTERS[user_id] += 1
            print(f"{user_id}:Take a ping from {user_id} it's {USERS_COUNTERS[user_id]} ping. User has {counter} auths",flush=True)
        # else:
        #     USERS_COUNTERS[user_id] = 1
        #     print(f"{user_id}:Take a ping from {user_id} it's {USERS_COUNTERS[user_id]} ping. User has {counter} auths",flush=True)

        
    

        

    def PingAll(self, request, context):
        thread = threading.Thread(target=self.process_ping_all)
        thread.start()
        return your_proto.Empty()
    
    def process_ping_all(self):
        # jurnalik_ml.run_all()
        global GLOBAL_COUNTER
        GLOBAL_COUNTER += 1
        if GLOBAL_COUNTER == AMOUNT_OF_SOURCES:
            # jurnalik_ml.run_all()
            GLOBAL_COUNTER = 0
        
def serve():
    server = grpc.server(futures.ThreadPoolExecutor())
    your_proto_grpc.add_MLServicer_to_server(
        MLServicer(), server)
    server.add_insecure_port('[::]:50051')  
    server.start()
    print("Server is starting")
    server.wait_for_termination()

if __name__ == '__main__':
    serve()


