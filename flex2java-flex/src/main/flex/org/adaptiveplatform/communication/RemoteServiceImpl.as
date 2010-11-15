package org.adaptiveplatform.communication {
	import mx.messaging.Channel;
	import mx.messaging.ChannelSet;
	import mx.messaging.channels.AMFChannel;
	import mx.rpc.AbstractOperation;
	import mx.rpc.remoting.RemoteObject;
	
public class RemoteServiceImpl implements RemoteService {

    public var channelSet:ChannelSet;

    public function RemoteServiceImpl() {
    }

	public function call(service:String, method:String, ... parameters:Array):ResultHandler {
        var remoteObject:RemoteObject=new RemoteObject();
        remoteObject.channelSet=channelSet;
        remoteObject.destination=service;
        var operation:AbstractOperation=remoteObject.getOperation(method);
        operation.arguments=parameters;
        operation.send();

        var resultHandler:RemoteMethodResultHandler=new RemoteMethodResultHandler(remoteObject)
        return resultHandler;
    }
}
}