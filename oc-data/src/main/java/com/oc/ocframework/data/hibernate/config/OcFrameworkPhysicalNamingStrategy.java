package com.oc.ocframework.data.hibernate.config;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;


public class OcFrameworkPhysicalNamingStrategy implements PhysicalNamingStrategy {

	@Override
	public Identifier toPhysicalCatalogName(Identifier arg0, JdbcEnvironment arg1) {
		return arg0;
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier arg0, JdbcEnvironment arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Identifier toPhysicalSchemaName(Identifier arg0, JdbcEnvironment arg1) {
		return arg0;
	}

	@Override
	public Identifier toPhysicalSequenceName(Identifier arg0, JdbcEnvironment arg1) {
		return arg0;
	}

	@Override
	public Identifier toPhysicalTableName(Identifier arg0, JdbcEnvironment arg1) {
		// TODO Auto-generated method stub
		String name = arg0.getText();
		StringUtils.splitByCharacterTypeCamelCase(name);
		return null;
	}

}
