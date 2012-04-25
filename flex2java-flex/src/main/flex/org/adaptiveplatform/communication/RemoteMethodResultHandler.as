package org.adaptiveplatform.communication {
	import flash.events.Event;

	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;

	public class RemoteMethodResultHandler implements ResultHandler {

		private var remoteMethod:RemoteObject;

		public function RemoteMethodResultHandler(remoteMethod:RemoteObject) {
			this.remoteMethod=remoteMethod;
		}

		public function onSuccess(success:Function):ResultHandler {
                remoteMethod.addEventListener(ResultEvent.RESULT, function(event:ResultEvent):void {
                    if (success.length == 0) {
                        success();
                    } else {
                        success(event.result);
                    }
				});
			return this;
		}

		public function onFault(fault:Function):ResultHandler {
			remoteMethod.addEventListener(FaultEvent.FAULT, function(event:FaultEvent):void {
					fault(event.fault);
				});
			return this;
		}

		public function onError(error:String, fault:Function):ResultHandler {
			remoteMethod.addEventListener(FaultEvent.FAULT, function(event:FaultEvent):void {
					if (event.fault.faultCode == error) {
						fault(event.fault);
					}
				});
			return this;
		}
		
		public function addListener(onSuccess:Function, onFailure:Function):ResultHandler {
		    remoteMethod.addEventListener(ResultEvent.RESULT, onSuccess);
            remoteMethod.addEventListener(FaultEvent.FAULT, onFailure);
			return this;
		}
	}
}
