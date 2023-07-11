import jurnalik_ml
import grpc
import ml_pb2_grpc as your_proto_grpc
import ml_pb2 as your_proto
from concurrent import futures

# python3 -m grpc_tools.protoc -I ../schema/ --python_out=. --grpc_python_out=. ../schema/ml.proto



class SelectorServicer(your_proto_grpc.SelectorServicer):
    def SelectOne(self, request, context):
        # Implement your logic here
        # Process the request and return a response
        return your_proto.Empty()

    def SelectAll(self, request, context):
        # Implement your logic here
        # Process the request and return a response
        return your_proto.Empty()

def serve():
    server = grpc.server(futures.ThreadPoolExecutor())
    your_proto_grpc.add_SelectorServicer_to_server(
        SelectorServicer(), server)
    server.add_insecure_port('[::]:50051')  
    server.start()
    server.wait_for_termination()

if __name__ == '__main__':
    serve()


