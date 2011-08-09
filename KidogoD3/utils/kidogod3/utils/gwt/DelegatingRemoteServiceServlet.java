package kidogod3.utils.gwt;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public abstract class DelegatingRemoteServiceServlet extends RemoteServiceServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6870359514641073788L;

	protected abstract Object getDelegate(Class<?> serviceClass);
	
	@Override
	public String processCall(String payload) throws SerializationException {
	    checkPermutationStrongName();

	    try {
	      RPCRequest rpcRequest = RPC.decodeRequest(payload, null, this);
	      
	      Object delegate = getDelegate(rpcRequest.getMethod().getDeclaringClass());

	      onAfterRequestDeserialized(rpcRequest);
	      
	      return RPC.invokeAndEncodeResponse(delegate, rpcRequest.getMethod(),
	          rpcRequest.getParameters(), rpcRequest.getSerializationPolicy(),
	          rpcRequest.getFlags());
	    } catch (IncompatibleRemoteServiceException ex) {
	      log(
	          "An IncompatibleRemoteServiceException was thrown while processing this call.",
	          ex);
	      return RPC.encodeResponseForFailure(null, ex);
	    }
	}

}
