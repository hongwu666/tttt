package test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class Mode<T, V> {
	private Class entityClass;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getClassName() {
		Type type = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) type).getActualTypeArguments();
		entityClass = (Class) params[0];

		Class<T> value = (Class) params[1];
		System.out.println(entityClass.getSimpleName());
		System.out.println(value.getSimpleName());
		return entityClass.getSimpleName();
	}

	public Class getEntityClass() {
		return entityClass;
	}


	public void setEntityClass(Class entityClass) {
		this.entityClass = entityClass;
	}

	

}
