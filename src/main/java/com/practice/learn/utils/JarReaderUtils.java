/**
 * 
 */
package com.practice.learn.utils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mayur-raj
 *
 */
public class JarReaderUtils {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		final String jarPath = "jars/basics-0.0.1-SNAPSHOT.jar";
		final String packagePrefix = "com.practice";
		JarReaderUtils readJars = new JarReaderUtils();
		Map<Class<?>, List<Method>> filteredClassMap = readJars.filterRestControllers(jarPath, packagePrefix);
		readJars.printOutput(filteredClassMap);

	}

	private void printOutput(Map<Class<?>, List<Method>> filteredClassMap) {
		filteredClassMap.keySet().forEach(cls -> {
			System.out.println("Class Name: " + cls.getName());
			filteredClassMap.get(cls).forEach(method -> {
				System.out.println("Method Name: " + method.getName());
				Parameter[] params = method.getParameters();
				Annotation[][] paramAnnotations = method.getParameterAnnotations();
				for (Annotation[] anArry : paramAnnotations) {
					for (Annotation an : anArry) {
						System.out.println("Param Annotations: " + an.toString());
					}
				}
				for (Parameter param : params) {
					System.out.println("Params: " + param.getName());
				}
				
				System.out.println("#############");
			});

			System.out.println("*********************************************");
		});

	}

	public Map<Class<?>, List<Method>> filterRestControllers(String jarPath, String packagePrefix) throws IOException {
		File jarFile = this.loadJar(jarPath);
		Set<String> classNames = this.getClassNamesFromJarFile(jarFile);
		Map<Class<?>, List<Method>> classMap = new HashMap<Class<?>, List<Method>>();

		try (URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { new URL("jar:file:" + jarFile + "!/"),
				new URL("jar:file:" + jarFile + "!/BOOT-INF/classes/") })) {
			classNames.stream().filter(clz -> clz.contains(packagePrefix)).forEach(clz -> {
				clz = clz.substring(clz.indexOf(packagePrefix));
				try {
					Class<?> cls = classLoader.loadClass(clz);
					if (cls.isAnnotationPresent(RestController.class)) {
						Method[] methods = cls.getMethods();
						for (Method method : methods) {
							if (method.isAnnotationPresent(RequestMapping.class)) {
								RequestMapping requestMappingAnnotation = method.getAnnotation(RequestMapping.class);
								List<RequestMethod> requestMethods = Arrays.asList(requestMappingAnnotation.method());
								if (requestMethods.contains(RequestMethod.POST)) {
									addToMap(classMap, cls, method);
								}
							} else if (method.isAnnotationPresent(PostMapping.class)) {
								addToMap(classMap, cls, method);
							}
						}
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}

		return classMap;
	}

	private void addToMap(Map<Class<?>, List<Method>> classMap, Class<?> cls, Method method) {
		List<Method> methodList = new ArrayList<Method>();
		if (classMap.containsKey(cls)) {
			methodList = classMap.get(cls);
		}
		methodList.add(method);
		classMap.put(cls, methodList);

	}

	private Set<String> getClassNamesFromJarFile(File givenFile) throws IOException {
		Set<String> classNames = new HashSet<>();
		try (JarFile jarFile = new JarFile(givenFile)) {
			Enumeration<JarEntry> e = jarFile.entries();
			while (e.hasMoreElements()) {
				JarEntry jarEntry = e.nextElement();
				if (jarEntry.getName().endsWith(".class")) {
					String className = jarEntry.getName().replace("/", ".").replace(".class", "");
					classNames.add(className);
				}
			}
			return classNames;
		}
	}

	private File loadJar(String JAR_PATH) {
		File jarFile = null;
		try {
			jarFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(JAR_PATH)).toURI());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jarFile;
	}

}
