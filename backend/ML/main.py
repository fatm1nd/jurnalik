import jurnalik_ml
import grpc
import ml_pb2_grpc as your_proto_grpc
import ml_pb2 as your_proto
from concurrent import futures
import threading 
import time

# python3 -m grpc_tools.protoc -I ../schema/ --python_out=. --grpc_python_out=. ../schema/ml.proto



class MLServicer(your_proto_grpc.MLServicer):
    def PingOne(self,request, context):
        thread = threading.Thread(target=self.process_ping_one, args=(request,))
        thread.start()
        return your_proto.Empty()

    def process_ping_one(self, user_id):
        print(user_id, flush=True)
        # jurnalik_ml.run_one(user_id)
        

    def PingAll(self, request, context):
        thread = threading.Thread(target=self.process_ping_all)
        thread.start()
        return your_proto.Empty()
    
    def process_ping_all(self):
        # jurnalik_ml.run_all()
        print("One", flush=True)
        time.sleep(3)
        print("Two", flush=True)
        time.sleep(3)
        print("Three", flush=True)

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


