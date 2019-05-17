/**
 * WSCustWechatAppFacadeSrvProxy.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package WSCustWechatAppFacade;

public interface WSCustWechatAppFacadeSrvProxy extends java.rmi.Remote {
    public java.lang.String getData(java.lang.String bizType, java.lang.String jsonType) throws java.rmi.RemoteException, custwechatappfacade.client.WSInvokeException;
    public java.lang.String bybHandler(java.lang.String methodName, java.lang.String jsonData) throws java.rmi.RemoteException, custwechatappfacade.client.WSInvokeException;
    public java.lang.String importData(java.lang.String bizType, java.lang.String jsonTpye) throws java.rmi.RemoteException, custwechatappfacade.client.WSInvokeException;
    public void updateToken() throws java.rmi.RemoteException, custwechatappfacade.client.WSInvokeException;
}
