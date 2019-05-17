/**
 * WSCustWechatAppFacadeSrvProxyServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package WSCustWechatAppFacade;

public class WSCustWechatAppFacadeSrvProxyServiceLocator extends org.apache.axis.client.Service implements WSCustWechatAppFacadeSrvProxyService {

    public WSCustWechatAppFacadeSrvProxyServiceLocator() {
    }


    public WSCustWechatAppFacadeSrvProxyServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSCustWechatAppFacadeSrvProxyServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSCustWechatAppFacade
    private java.lang.String WSCustWechatAppFacade_address = "http://192.168.91.35:56898/ormrpc/services/WSCustWechatAppFacade";

    public java.lang.String getWSCustWechatAppFacadeAddress() {
        return WSCustWechatAppFacade_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSCustWechatAppFacadeWSDDServiceName = "WSCustWechatAppFacade";

    public java.lang.String getWSCustWechatAppFacadeWSDDServiceName() {
        return WSCustWechatAppFacadeWSDDServiceName;
    }

    public void setWSCustWechatAppFacadeWSDDServiceName(java.lang.String name) {
        WSCustWechatAppFacadeWSDDServiceName = name;
    }

    public WSCustWechatAppFacadeSrvProxy getWSCustWechatAppFacade() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSCustWechatAppFacade_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSCustWechatAppFacade(endpoint);
    }

    public WSCustWechatAppFacadeSrvProxy getWSCustWechatAppFacade(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            WSCustWechatAppFacadeSoapBindingStub _stub = new WSCustWechatAppFacadeSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSCustWechatAppFacadeWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSCustWechatAppFacadeEndpointAddress(java.lang.String address) {
        WSCustWechatAppFacade_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (WSCustWechatAppFacadeSrvProxy.class.isAssignableFrom(serviceEndpointInterface)) {
                WSCustWechatAppFacadeSoapBindingStub _stub = new WSCustWechatAppFacadeSoapBindingStub(new java.net.URL(WSCustWechatAppFacade_address), this);
                _stub.setPortName(getWSCustWechatAppFacadeWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("WSCustWechatAppFacade".equals(inputPortName)) {
            return getWSCustWechatAppFacade();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://127.0.0.1:56898/ormrpc/services/WSCustWechatAppFacade", "WSCustWechatAppFacadeSrvProxyService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://127.0.0.1:56898/ormrpc/services/WSCustWechatAppFacade", "WSCustWechatAppFacade"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSCustWechatAppFacade".equals(portName)) {
            setWSCustWechatAppFacadeEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
