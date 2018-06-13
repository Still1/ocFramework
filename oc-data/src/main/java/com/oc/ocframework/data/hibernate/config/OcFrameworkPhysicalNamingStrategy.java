package com.oc.ocframework.data.hibernate.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.stereotype.Component;

import com.oc.ocframework.util.component.string.OcFrameworkStringUtil;

@Component
public class OcFrameworkPhysicalNamingStrategy implements PhysicalNamingStrategy {

	public static final String PHYSICAL_TABLE_NAME_PREFIX = "t";
	public static final String PHYSICAL_TABLE_NAME_SEPARATOR = "_";
	
	@Override
	public Identifier toPhysicalCatalogName(Identifier arg0, JdbcEnvironment arg1) {
		return arg0;
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier arg0, JdbcEnvironment arg1) {
		String name = arg0.getText();
		String physicalColumnName = OcFrameworkStringUtil.camelCaseToSeparator(name, PHYSICAL_TABLE_NAME_SEPARATOR); 
		return arg1.getIdentifierHelper().toIdentifier(physicalColumnName);
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
		String name = arg0.getText();
		String physicalTableName = OcFrameworkStringUtil.camelCaseToSeparator(name, PHYSICAL_TABLE_NAME_SEPARATOR, PHYSICAL_TABLE_NAME_PREFIX); 
		return arg1.getIdentifierHelper().toIdentifier(physicalTableName);
	}

}
