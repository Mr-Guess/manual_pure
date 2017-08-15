package com.ay.framework.filter.tomcat;

import java.util.List;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

/** 
 * @Description 
 * @date 2012-10-30
 * @author WangXin
 */
public class JMXGetEncoding {
	/**
	 * 返回Tomcat设置在server.xml的Connector上的URIEncoding属性值，如果无法获取或没有设置返回null
	 * 
	 * @param port
	 *            HTTP监听端口，用于对应Connector
	 * @return 
	 */
	public static String getConnectorURIEncoding(int port) {
		String result = null;
		// 获取当前环境下的JMX MBeanServer，Tomcat启动的时候会创建一个(通常环境下也只有一个MBeanServer)
		List<MBeanServer> serverList = MBeanServerFactory.findMBeanServer(null);
		// 遍历MBeanServer找到要的MBean及对应的属性
		for (MBeanServer server : serverList) {
			// 获取MBean
			try {
				result = (String) server.getAttribute(ObjectName.getInstance("Catalina:type=Connector,port=" + port), "URIEncoding");
			} catch (AttributeNotFoundException e) { // 指定的属性在 MBean中是不可访问的
				// Do Nothing
			} catch (InstanceNotFoundException e) { // 指定的 MBean未注册
				// Do Nothing
			} catch (MBeanException e) {
				System.out.println("MBeanException: " + e.getMessage());
			} catch (ReflectionException e) {
				System.out.println("ReflectionException: " + e.getMessage());
			} catch (MalformedObjectNameException e) {
				System.out.println("MalformedObjectNameException: " + e.getMessage());
			}
			if (result != null) { // 拿到值就跳出
				break;
			}
		}

		return result;
	}
}

