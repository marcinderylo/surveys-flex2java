package org.adaptiveplatform.communication {
	import mx.resources.ResourceManager;

	public interface ResultHandler {
		function onSuccess(success:Function):ResultHandler;
		function onFault(fault:Function):ResultHandler;
		function onError(error:String, fault:Function):ResultHandler;
	}
}