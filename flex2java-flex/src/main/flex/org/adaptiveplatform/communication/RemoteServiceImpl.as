package org.adaptiveplatform.communication {
import mx.messaging.Channel;
import mx.messaging.ChannelSet;
import mx.messaging.channels.AMFChannel;
import mx.rpc.AbstractOperation;
import mx.rpc.remoting.RemoteObject;

public class RemoteServiceImpl implements RemoteService {

    public var channelName:String;
    public var channelUrl:String;

    public var channelSet:ChannelSet;

    public function RemoteServiceImpl() {
    }

    public function initialize():void {
		channelSet=new ChannelSet();
		var channel:Channel=new AMFChannel(channelName, channelUrl);
		channelSet.addChannel(channel);
    }

    public function call(service:String, method:String, ... parameters:Array):ResultHandler {
        var remoteObject:RemoteObject=createRemoteObject();


        remoteObject.destination=service;
        var operation:AbstractOperation=remoteObject.getOperation(method);
        operation.arguments=parameters;
        operation.send();

        var resultHandler:RemoteMethodResultHandler=new RemoteMethodResultHandler(remoteObject)
        return resultHandler;
    }

    private function createRemoteObject():RemoteObject {
        if (channelSet == null) {
            initialize();
        }
        var remoteObject:RemoteObject=new RemoteObject();
        remoteObject.channelSet=channelSet;
        return remoteObject;
    }
}
}