package com.funcas.pboot.common.util;

import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * XML处理
 * Author: funcas
 * Date: 14/8/25
 * Time: 上午9:59
 */
public final class XmlStream {
	private final static String ROOT_ELEMENT_XML = "xml";
	private final static String XML_VERSION = "1.0";
	private final static Map<Class<?>, Unmarshaller> messageUnmarshaller;
	private final static Map<Class<?>, Marshaller> messageMarshaller;
	static {
		messageUnmarshaller = new ConcurrentHashMap<Class<?>, Unmarshaller>();
		messageMarshaller = new ConcurrentHashMap<Class<?>, Marshaller>();
	}

	public static final Charset UTF_8 = Charset.forName("UTF-8");
	public static final Charset ASCII = Charset.forName("US-ASCII");
	public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
	/**
	 * Xml2Bean
	 * 
	 * @param content
	 *            xml内容
	 * @param clazz
	 *            bean类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromXML(InputStream content, Class<T> clazz) {
		Unmarshaller unmarshaller = messageUnmarshaller.get(clazz);
		if (unmarshaller == null) {
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
				unmarshaller = jaxbContext.createUnmarshaller();
				messageUnmarshaller.put(clazz, unmarshaller);
			} catch (JAXBException e) {
				throw new IllegalArgumentException(e);
			}
		}
		try {
			Source source = new StreamSource(content);
			XmlRootElement rootElement = clazz.getAnnotation(XmlRootElement.class);
			if (rootElement == null
					|| rootElement.name().equals(XmlRootElement.class.getMethod("name").getDefaultValue().toString())) {
				JAXBElement<T> jaxbElement = unmarshaller.unmarshal(source, clazz);
				return jaxbElement.getValue();
			} else {
				return (T) unmarshaller.unmarshal(source);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		} finally {
			if (content != null) {
				try {
					content.close();
				} catch (IOException e) {
					;
				}
			}
		}
	}

	/**
	 * Xml2Bean
	 * 
	 * @param content
	 *            xml内容
	 * @param clazz
	 *            bean类型
	 * @return
	 */
	public static <T> T fromXML(String content, Class<T> clazz) {
		return fromXML(new ByteArrayInputStream(content.getBytes(UTF_8)), clazz);
	}

	/**
	 * map2xml
	 * 
	 * @param map
	 *            value无嵌套的map
	 * @return xml内容
	 */
	public static String map2xml(Map<String, String> map) {
		StringWriter sw = new StringWriter();
		try {
			XMLStreamWriter xw = XMLOutputFactory.newInstance().createXMLStreamWriter(sw);
			xw.writeStartDocument(UTF_8.name(), XML_VERSION);
			xw.writeStartElement(ROOT_ELEMENT_XML);
			for (Entry<String, String> entry : map.entrySet()) {
				if (StringUtils.isBlank(entry.getValue())) {
					continue;
				}
				xw.writeStartElement(entry.getKey());
				xw.writeCData(entry.getValue());
				xw.writeEndElement();
			}
			xw.writeEndDocument();
			xw.flush();
			xw.close();
		} catch (XMLStreamException e) {
			throw new IllegalArgumentException(e);
		} finally {
			try {
				sw.close();
			} catch (IOException e) {
				;
			}
		}
		return sw.getBuffer().toString();
	}

	/**
	 * map2xml
	 * 
	 * @param json
	 *            value无嵌套的json
	 * @return xml内容
	 */
	public static String map2xml(JSONObject json) {
		StringWriter sw = new StringWriter();
		try {
			XMLStreamWriter xw = XMLOutputFactory.newInstance().createXMLStreamWriter(sw);
			xw.writeStartDocument(UTF_8.name(), XML_VERSION);
			xw.writeStartElement(ROOT_ELEMENT_XML);
			for (Entry<String, Object> entry : json.entrySet()) {
				if (StringUtils.isBlank(json.getString(entry.getKey()))) {
					continue;
				}
				xw.writeStartElement(entry.getKey());
				xw.writeCData(json.getString(entry.getKey()));
				xw.writeEndElement();
			}
			xw.writeEndDocument();
			xw.flush();
			xw.close();
		} catch (XMLStreamException e) {
			throw new IllegalArgumentException(e);
		} finally {
			try {
				sw.close();
			} catch (IOException e) {
				;
			}
		}
		return sw.getBuffer().toString();
	}

	/**
	 * xml2map
	 * 
	 * @param content
	 *            无嵌套节点的xml内容
	 * @return map对象
	 */
	public static Map<String, String> xml2map(String content) {
		Map<String, String> map = new HashMap<String, String>();
		StringReader sr = new StringReader(content);
		try {
			XMLStreamReader xr = XMLInputFactory.newInstance().createXMLStreamReader(sr);
			while (true) {
				int event = xr.next();
				if (event == XMLStreamConstants.END_DOCUMENT) {
					xr.close();
					break;
				} else if (event == XMLStreamConstants.START_ELEMENT) {
					String name = xr.getLocalName();
					while (true) {
						event = xr.next();
						if (event == XMLStreamConstants.START_ELEMENT) {
							name = xr.getLocalName();
						} else if (event == XMLStreamConstants.END_ELEMENT) {
							break;
						} else if (event == XMLStreamConstants.CHARACTERS) {
							String value = xr.getText();
							map.put(name, value);
						}
					}
				}
			}
		} catch (XMLStreamException e) {
			throw new IllegalArgumentException(e);
		} finally {
			sr.close();
		}
		return map;
	}

	/**
	 * Bean2Xml
	 * 
	 * @param object
	 *            bean对象
	 * @return xml内容
	 */
	public static String toXML(Object object) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		toXML(object, os);
		return StringUtils.toEncodedString(os.toByteArray(), UTF_8);
	}

	/**
	 * Bean2Xml
	 * 
	 * @param t
	 *            bean对象
	 * @param os
	 *            输出流
	 */
	@SuppressWarnings("unchecked")
	public static <T> void toXML(T t, OutputStream os) {
		Class<T> clazz = (Class<T>) t.getClass();
		Marshaller marshaller = messageMarshaller.get(clazz);
		if (marshaller == null) {
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
				marshaller = jaxbContext.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_ENCODING, UTF_8.name());
				marshaller.setProperty(CharacterEscapeHandler.class.getName(), (CharacterEscapeHandler) (char[] chars, int i, int i1, boolean bln, Writer writer) -> {
					writer.write(chars, i, i1);
				});
				messageMarshaller.put(clazz, marshaller);
			} catch (JAXBException e) {
				throw new IllegalArgumentException(e);
			}
		}
		try {
			XmlRootElement rootElement = clazz.getAnnotation(XmlRootElement.class);
			if (rootElement == null
					|| rootElement.name().equals(XmlRootElement.class.getMethod("name").getDefaultValue().toString())) {
				marshaller.marshal(new JAXBElement<T>(new QName(ROOT_ELEMENT_XML), clazz, t), os);
			} else {
				marshaller.marshal(t, os);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					;
				}
			}
		}
	}
}
