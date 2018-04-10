/**
 * Project Name:ris-variable
 * File Name:ClassScaner.java
 * Package Name:com.ris.variable
 * Date:2018年4月9日上午10:12:35
 * Copyright (c) 2018,  百信普惠金融 All Rights Reserved.
 *
*/

package com.luotuo.tool.common.utils.getclass;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;


/**
 * ClassName:ClassScaner <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年4月9日 上午10:12:35 <br/>
 * 
 * @author 鲁济良
 * @version 1.0
 * @since JDK 1.8
 * @see
 */
public class ClassScaner implements ResourceLoaderAware {

	private final List<TypeFilter> includeFilters = new LinkedList<TypeFilter>();
	private final List<TypeFilter> excludeFilters = new LinkedList<TypeFilter>();

	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	private MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(
			this.resourcePatternResolver);

	@SuppressWarnings({ "rawtypes", "unchecked" })
    public static Set<Class> scan(String[] basePackages, Class<? extends Annotation>... annotations) {
		ClassScaner cs = new ClassScaner();

		if (ArrayUtils.isNotEmpty(annotations)) {
			for (Class anno : annotations) {
				cs.addIncludeFilter(new AnnotationTypeFilter(anno));
			}
		}

		Set<Class> classes = new HashSet<Class>();
		for (String s : basePackages)
			classes.addAll(cs.doScan(s));
		return classes;
	}

	@SafeVarargs
    @SuppressWarnings("rawtypes")
    public static Set<Class> scan(String basePackages, Class<? extends Annotation>... annotations) {
		return ClassScaner.scan(StringUtils.tokenizeToStringArray(basePackages, ",; \t\n"), annotations);
	}

	public final ResourceLoader getResourceLoader() {
		return this.resourcePatternResolver;
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
		this.metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
	}

	public void addIncludeFilter(TypeFilter includeFilter) {
		this.includeFilters.add(includeFilter);
	}

	public void addExcludeFilter(TypeFilter excludeFilter) {
		this.excludeFilters.add(0, excludeFilter);
	}

	public void resetFilters(boolean useDefaultFilters) {
		this.includeFilters.clear();
		this.excludeFilters.clear();
	}

	@SuppressWarnings("rawtypes")
    public Set<Class> doScan(String basePackage) {
		Set<Class> classes = new HashSet<Class>();
		try {
			String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
					+ org.springframework.util.ClassUtils.convertClassNameToResourcePath(
							SystemPropertyUtils.resolvePlaceholders(basePackage))
					+ "/**/*.class";
			Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);

			for (int i = 0; i < resources.length; i++) {
				Resource resource = resources[i];
				if (resource.isReadable()) {
					MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
					if ((includeFilters.size() == 0 && excludeFilters.size() == 0) || matches(metadataReader)) {
						try {
							classes.add(Class.forName(metadataReader.getClassMetadata().getClassName()));
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (IOException ex) {
			throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
		}
		return classes;
	}

	protected boolean matches(MetadataReader metadataReader) throws IOException {
		for (TypeFilter tf : this.excludeFilters) {
			if (tf.match(metadataReader, this.metadataReaderFactory)) {
				return false;
			}
		}
		for (TypeFilter tf : this.includeFilters) {
			if (tf.match(metadataReader, this.metadataReaderFactory)) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		
		ClassScaner.scan("com.luotuo.tool.common.utils", null)
				.forEach(clazz -> System.out.println(clazz));
		
	}

}
