package br.com.yuri.resources

import br.com.yuri.PublishertestReply
import br.com.yuri.PublishertestRequest
import br.com.yuri.PublishertestServiceGrpc
import io.grpc.stub.StreamObserver
import io.micronaut.grpc.annotation.GrpcService

@GrpcService

class ProductResources : PublishertestServiceGrpc.PublishertestServiceImplBase() {
    override fun send(request: PublishertestRequest?, responseObserver: StreamObserver<PublishertestReply>?) {
        val toSend = "Hello, ${request?.name}"

        val reply = PublishertestReply.newBuilder()
                .setMessage(toSend)
                .build()

        responseObserver?.onNext(reply)
        responseObserver?.onCompleted()

    }
}