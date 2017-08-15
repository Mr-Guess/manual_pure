package com.ay.framework.filter.tomcat;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
/** 
 * @Description 
 * @date 2012-10-30
 * @author WangXin
 */
public class TomcatServer {
public final static String SERVER_FACTORY_CLASSNAME = "org.apache.catalina.ServerFactory";
    
    public static Map<String, String> getConnectorUriEncoding() {
        Map<String, String> map = new HashMap<String, String>();
        // 获得 Tomcat server.xml 解析后对象的工厂
         Class serverFactoryClass = Util.getClass(SERVER_FACTORY_CLASSNAME);
        if(serverFactoryClass == null) {
            return map;
        }
        try {
            // 通过 ServerFactory 的 getServer() 静态方法获得 server.xml 中的 Server
            // 一个 Tomcat 只有一个 Server 对象
             Object server = Util.invokeStaticMethod(serverFactoryClass, "getServer");            
            // 通过 Server 对象的 findServices 获得所有的 Service
            Object servicesArray = Util.invokeMethod(server, "findServices");
            for(int i = 0, k = Array.getLength(servicesArray); i < k; i++) {
                Object service = Array.get(servicesArray, i);
                // 获得 Service 的 name
                String serviceName = (String)Util.invokeMethod(service, "getName");
                // 通过 Service 对象的 findConnectors 获得所有的 Connector
                Object connectorsArray = Util.invokeMethod(service, "findConnectors");
                for(int j = 0, m = Array.getLength(connectorsArray); j < m; j++) {
                    Object connector = Array.get(connectorsArray, j);
                    // 通过 Connector 对象的 getURIEncoding 方法获取 URI 传输编码
                       String uriEncoding = (String)Util.invokeMethod(connector, "getURIEncoding");
                       String protocol = (String)Util.invokeMethod(connector, "getProtocol");
                    // 通过 getPort 方法获得当前 Connector 的端口号
                       Integer port = (Integer)Util.invokeMethod(connector, "getPort");
                    
                    // 由于 Tomcat 可以配置多个 Service 元素，因此采用
                       // Service Name + Connector Port 作为 key
                    if(protocol.toUpperCase().contains("HTTP"))
                    	map.put(serviceName + "_" + port, uriEncoding);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}

