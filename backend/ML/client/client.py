import grpc
import ml_pb2 as your_proto
import ml_pb2_grpc as your_proto_grpc
import os
import time

def run_grpc_client():
    # server_host = os.getenv('GRPC_SERVER_HOST', 'localhost')
    # server_port = os.getenv('GRPC_SERVER_PORT', '50051')
    channel = grpc.insecure_channel(f'{"grpc-server"}:{"50051"}')
    stub = your_proto_grpc.MLStub(channel)

    # Call the SelectOne RPC
    # Create an instance of SelectorPing
    selector_ping = your_proto.SelectorPing()

    # Set the user field
    selector_ping.user = "your_user_name"

    # Set the source field using the enum values
    selector_ping.source = your_proto.SelectorPing.Source.Value("TG")  # Or your_proto.SelectorPing.Source.TG



    response = stub.PingOne(selector_ping)
    print("SelectOne response:", response)

    # Call the SelectAll RPC
    empty_request = your_proto.Empty()
    response = stub.PingAll(empty_request)
    print("SelectAll response:", response)

    empty_request = your_proto.Empty()
    response = stub.PingAll(empty_request)
    print("SelectAll response:", response)

if __name__ == '__main__':
    print("Waiting 3s", flush=True)
    time.sleep(3)
    run_grpc_client()
