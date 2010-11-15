package org.adaptiveplatform.communication {

public interface RemoteService {
    function call(service:String, method:String, ... parameters):ResultHandler;
}
}
