package com.oc.ocframework.util.component.json.gson.config;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class ForeignKeyExclusionStrategy implements ExclusionStrategy {

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		boolean isManyToMany = false;
		boolean isManyToOne = false;
		boolean isOneToMany = false;
		if(f.getAnnotation(ManyToMany.class) != null) {
			isManyToMany = true;
		}
		if(f.getAnnotation(ManyToOne.class) != null) {
			isManyToOne = true;
		}
		if(f.getAnnotation(OneToMany.class) != null) {
			isOneToMany = true;
		}
		return isManyToMany || isManyToOne || isOneToMany;
	}
	
	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}
}
