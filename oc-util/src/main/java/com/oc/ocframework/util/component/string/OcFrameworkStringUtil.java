package com.oc.ocframework.util.component.string;

import org.apache.commons.lang3.StringUtils;

public class OcFrameworkStringUtil {
	
	public static String camelCaseToSeparator(String str, char separator) {
		String[] stringSplitArray = StringUtils.splitByCharacterTypeCamelCase(str);
		StringBuilder stringBuilder = new StringBuilder();
		for(String stringSplitFragment : stringSplitArray) {
			stringBuilder.append(StringUtils.lowerCase(stringSplitFragment));
			stringBuilder.append(separator);
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		return stringBuilder.toString();
	}
	
	public static String camelCaseToSeparator(String str, char separator, String prefix) {
		return prefix + separator + camelCaseToSeparator(str, separator);
	}
	
	public static String separatorToRemovePrefix(String str, String prefix) {
		int prefixLength = prefix.length();
		return str.substring(prefixLength + 1);
	}
}
