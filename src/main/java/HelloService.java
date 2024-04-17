import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import io.quarkus.example.Greeter;
import io.quarkus.example.GreeterGrpc;
import io.quarkus.example.HelloReply;
import io.quarkus.example.HelloRequest;
import io.quarkus.grpc.GrpcService;
import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Uni;

@GrpcService
public class HelloService implements Greeter {
    @Override
    @RunOnVirtualThread
    public Uni<HelloReply> sayHello(HelloRequest request) {
        String message = "Hello, " + request.getName();

        if (request.getName().isBlank()) {
            throw Status.INVALID_ARGUMENT.withDescription("Must provide a name").asRuntimeException();
        } else if (request.getName().equals("cat")) {
            message = "Meow!";
        } else if (request.getName().equals("dog")) {
            message = "Bark!";
        }

        var result = HelloReply.newBuilder().setMessage(message).build();
        return Uni.createFrom().item(result);
    }
}
