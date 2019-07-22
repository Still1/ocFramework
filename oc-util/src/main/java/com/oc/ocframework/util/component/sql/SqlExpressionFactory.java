package com.oc.ocframework.util.component.sql;

import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;

public class SqlExpressionFactory {
    public static BinaryExpression getBinaryExpression(String operation) {
        BinaryExpression binaryExpression = null;
        switch(operation) {
            case "eq" :
                binaryExpression = new EqualsTo();
                break;
            case "like" :
                binaryExpression = new LikeExpression();
                break;
        }
        return binaryExpression;
    }
}
