package com.oc.ocframework.data.hibernate.config;

import org.hibernate.boot.model.naming.EntityNaming;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitBasicColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitEntityNameSource;
import org.hibernate.boot.model.naming.ImplicitJoinColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitJoinTableNameSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.springframework.stereotype.Component;

import com.oc.ocframework.util.component.string.OcFrameworkStringUtil;

@Component
public class OcFrameworkImplicitNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {

	private static final long serialVersionUID = 4319541169521101685L;
	
	public static final String TABLE_NAME_PREFIX = "t";
	public static final char NAME_SEPARATOR = '_';

	@Override
	public Identifier determinePrimaryTableName(ImplicitEntityNameSource source) {
		Identifier identifier = super.determinePrimaryTableName(source);
		String primaryTableName = OcFrameworkStringUtil.camelCaseToSeparator(identifier.getText(), NAME_SEPARATOR, TABLE_NAME_PREFIX); 
		return this.toIdentifier(primaryTableName, source.getBuildingContext());
	}
	
	@Override
	public Identifier determineBasicColumnName(ImplicitBasicColumnNameSource source) {
		Identifier identifier = super.determineBasicColumnName(source);
		String basicColumnName = OcFrameworkStringUtil.camelCaseToSeparator(identifier.getText(), NAME_SEPARATOR);
		return this.toIdentifier(basicColumnName, source.getBuildingContext());
	}

	@Override
	public Identifier determineJoinTableName(ImplicitJoinTableNameSource source) {
		EntityNaming owningEntityNaming = source.getOwningEntityNaming();
		EntityNaming nonOwningEntityNaming = source.getNonOwningEntityNaming();
		String joinTableEntityName = this.transformEntityName(owningEntityNaming) + this.transformEntityName(nonOwningEntityNaming);
		String joinTableName = OcFrameworkStringUtil.camelCaseToSeparator(joinTableEntityName, NAME_SEPARATOR, TABLE_NAME_PREFIX);
		return this.toIdentifier(joinTableName, source.getBuildingContext());
	}

	@Override
	public Identifier determineJoinColumnName(ImplicitJoinColumnNameSource source) {
		String referencedTableName = source.getReferencedTableName().getText();
		String referencedColumnName = source.getReferencedColumnName().getText();
		String referencedTableNameWithoutPrefix = OcFrameworkStringUtil.separatorToRemovePrefix(referencedTableName, TABLE_NAME_PREFIX);
		String joinColumnName = referencedTableNameWithoutPrefix + NAME_SEPARATOR + referencedColumnName;
		return this.toIdentifier(joinColumnName, source.getBuildingContext());
	}
}
 