import grpc
# import ml_pb2 as your_proto
# import ml_pb2_grpc as your_proto_grpc
import selector_pb2 as your_protos
import selector_pb2_grpc as your_proto_grpcs
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
    selector_ping.user = "207"

    # Set the source field using the enum values
    selector_ping.source = your_proto.SelectorPing.Source.Value("TG")  # Or your_proto.SelectorPing.Source.TG


    time.sleep(7)

    # Call the SelectAll RPC
    empty_request = your_proto.Empty()
    response = stub.PingAll(empty_request)
    print("SelectAll response:", response)

    # empty_request = your_proto.Empty()
    # response = stub.PingAll(empty_request)
    # print("SelectAll response:", response)

    # empty_request = your_proto.Empty()
    # response = stub.PingAll(empty_request)
    # print("SelectAll response:", response)

def run_grpc_client_selector():
    # server_host = os.getenv('GRPC_SERVER_HOST', 'localhost')
    # server_port = os.getenv('GRPC_SERVER_PORT', '50051')
    channel = grpc.insecure_channel(f'{"telegram_selector"}:{"50051"}')
    stub = your_proto_grpcs.SelectorStub(channel)

    # Call the SelectOne RPC
    # Create an instance of SelectorPing
    # selector_ping = your_proto.User()

    # # Set the user field

    # # Set the source field using the enum values
    # selector_ping.source = your_proto.SelectorPing.Source.Value("TG")  # Or your_proto.SelectorPing.Source.TG


    time.sleep(7)

    # Call the SelectAll RPC
    empty_request = your_protos.Empty()
    response = stub.SelectAll(empty_request)
    print("SelectAll response:", response)


if __name__ == '__main__':
    print("Waiting 3s", flush=True)
    time.sleep(3)
    # run_grpc_client()
    run_grpc_client_selector()



