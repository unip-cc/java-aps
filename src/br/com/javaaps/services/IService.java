package br.com.javaaps.services;

import java.util.Collection;

public interface IService<T> {

	Collection<T> load();
	void save(T obj);
	void edit(String objectId, T obj);
	void delete(String objectId);
}
