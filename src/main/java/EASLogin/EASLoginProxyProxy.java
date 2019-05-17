package EASLogin;

public class EASLoginProxyProxy implements EASLoginProxy {
  private String _endpoint = null;
  private EASLoginProxy eASLoginProxy = null;
  
  public EASLoginProxyProxy() {
    _initEASLoginProxyProxy();
  }
  
  public EASLoginProxyProxy(String endpoint) {
    _endpoint = endpoint;
    _initEASLoginProxyProxy();
  }
  
  private void _initEASLoginProxyProxy() {
    try {
      eASLoginProxy = (new EASLoginProxyServiceLocator()).getEASLogin();
      if (eASLoginProxy != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)eASLoginProxy)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)eASLoginProxy)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (eASLoginProxy != null)
      ((javax.xml.rpc.Stub)eASLoginProxy)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public EASLoginProxy getEASLoginProxy() {
    if (eASLoginProxy == null)
      _initEASLoginProxyProxy();
    return eASLoginProxy;
  }
  
  public client.WSContext login(java.lang.String userName, java.lang.String password, java.lang.String slnName, java.lang.String dcName, java.lang.String language, int dbType, java.lang.String authPattern) throws java.rmi.RemoteException{
    if (eASLoginProxy == null)
      _initEASLoginProxyProxy();
    return eASLoginProxy.login(userName, password, slnName, dcName, language, dbType, authPattern);
  }
  
  public client.WSContext login(java.lang.String userName, java.lang.String password, java.lang.String slnName, java.lang.String dcName, java.lang.String language, int dbType, java.lang.String authPattern, int isEncodePwd) throws java.rmi.RemoteException{
    if (eASLoginProxy == null)
      _initEASLoginProxyProxy();
    return eASLoginProxy.login(userName, password, slnName, dcName, language, dbType, authPattern, isEncodePwd);
  }
  
  public client.WSContext login(java.lang.String userName, java.lang.String password, java.lang.String slnName, java.lang.String dcName, java.lang.String language, int dbType) throws java.rmi.RemoteException{
    if (eASLoginProxy == null)
      _initEASLoginProxyProxy();
    return eASLoginProxy.login(userName, password, slnName, dcName, language, dbType);
  }
  
  public boolean logout(java.lang.String userName, java.lang.String slnName, java.lang.String dcName, java.lang.String language) throws java.rmi.RemoteException{
    if (eASLoginProxy == null)
      _initEASLoginProxyProxy();
    return eASLoginProxy.logout(userName, slnName, dcName, language);
  }
  
  public client.WSContext loginSale(java.lang.String userName, java.lang.String password, java.lang.String slnName, java.lang.String dcName, java.lang.String language, int dbType) throws java.rmi.RemoteException{
    if (eASLoginProxy == null)
      _initEASLoginProxyProxy();
    return eASLoginProxy.loginSale(userName, password, slnName, dcName, language, dbType);
  }
  
  public java.lang.String loginMaterialQR(java.lang.String jsonStr) throws java.rmi.RemoteException{
    if (eASLoginProxy == null)
      _initEASLoginProxyProxy();
    return eASLoginProxy.loginMaterialQR(jsonStr);
  }
  
  public java.lang.String loginBfb(java.lang.String jsonStr) throws java.rmi.RemoteException{
    if (eASLoginProxy == null)
      _initEASLoginProxyProxy();
    return eASLoginProxy.loginBfb(jsonStr);
  }
  
  public java.lang.String loginZBAPP(java.lang.String jsonStr) throws java.rmi.RemoteException, com.kingdee.bos.webservice.WSInvokeException{
    if (eASLoginProxy == null)
      _initEASLoginProxyProxy();
    return eASLoginProxy.loginZBAPP(jsonStr);
  }
  
  
}