/*
 * GRAKN.AI - THE KNOWLEDGE GRAPH
 * Copyright (C) 2018 Grakn Labs Ltd
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package graql.lang.statement.builder;

import graql.lang.Graql;
import graql.lang.pattern.Pattern;
import graql.lang.property.AbstractProperty;
import graql.lang.property.DataTypeProperty;
import graql.lang.property.HasAttributeTypeProperty;
import graql.lang.property.PlaysProperty;
import graql.lang.property.RegexProperty;
import graql.lang.property.RelatesProperty;
import graql.lang.property.SubProperty;
import graql.lang.property.ThenProperty;
import graql.lang.property.TypeProperty;
import graql.lang.property.VarProperty;
import graql.lang.property.WhenProperty;
import graql.lang.statement.Statement;
import graql.lang.statement.StatementType;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

/**
 * Type Statement Properties
 */
public interface StatementTypeBuilder {

    @CheckReturnValue
    default StatementType type(Graql.Token.Type type) {
        return type(type.toString());
    }
    
    /**
     * @param name a string that this variable's label must match
     * @return this
     */
    @CheckReturnValue
    default StatementType type(String name) {
        return type(new TypeProperty(name));
    }

    /**
     * set this concept type variable as abstract, meaning it cannot have direct instances
     *
     * @return this
     */
    @CheckReturnValue
    default StatementType isAbstract() {
        return type(AbstractProperty.get());
    }

    default StatementType sub(Graql.Token.Type type) {
        return sub(type.toString());
    }

    /**
     * @param type a concept type id that this variable must be a kind of
     * @return this
     */
    @CheckReturnValue
    default StatementType sub(String type) {
        return sub(Graql.type(type));
    }

    /**
     * @param type a concept type that this variable must be a kind of
     * @return this
     */
    @CheckReturnValue
    default StatementType sub(Statement type) {
        return sub(new SubProperty(type));
    }

    default StatementType subX(Graql.Token.Type type) {
        return subX(type.toString());
    }

    /**
     * @param type a concept type id that this variable must be a kind of, without looking at parent types
     * @return this
     */
    @CheckReturnValue
    default StatementType subX(String type) {
        return subX(Graql.type(type));
    }

    /**
     * @param type a concept type that this variable must be a kind of, without looking at parent type
     * @return this
     */
    @CheckReturnValue
    default StatementType subX(Statement type) {
        return sub(new SubProperty(type, true));
    }

    @CheckReturnValue
    default StatementType sub(SubProperty property) {
        return type(property);
    }

    /**
     * @param type a resource type that this type variable can be one-to-one related to
     * @return this
     */
    @CheckReturnValue
    default StatementType key(String type) {
        return key(Graql.var().type(type));
    }

    /**
     * @param type a resource type that this type variable can be one-to-one related to
     * @return this
     */
    @CheckReturnValue
    default StatementType key(Statement type) {
        return type(new HasAttributeTypeProperty(type, true));
    }

    /**
     * @param type a resource type that this type variable can be related to
     * @return this
     */
    @CheckReturnValue
    default StatementType has(String type, Graql.Argument<?>... args) {
        return has(Graql.type(type));
    }

    /**
     * @param type a resource type that this type variable can be related to
     * @return this
     */
    @CheckReturnValue
    default StatementType has(Statement type) {
        return type(new HasAttributeTypeProperty(type));
    }

    /**
     * @param type a Role id that this concept type variable must play
     * @return this
     */
    @CheckReturnValue
    default StatementType plays(String type) {
        return plays(Graql.type(type));
    }

    /**
     * @param type a Role that this concept type variable must play
     * @return this
     */
    @CheckReturnValue
    default StatementType plays(Statement type) {
        return type(new PlaysProperty(type, false));
    }

    /**
     * @param type a Role id that this relation type variable must have
     * @return this
     */
    @CheckReturnValue
    default StatementType relates(String type) {
        return relates(type, null);
    }

    /**
     * @param type a Role that this relation type variable must have
     * @return this
     */
    @CheckReturnValue
    default StatementType relates(Statement type) {
        return relates(type, null);
    }

    /**
     * @param roleType a Role id that this relation type variable must have
     * @return this
     */
    @CheckReturnValue
    default StatementType relates(String roleType, @Nullable String superRoleType) {
        return relates(Graql.type(roleType), superRoleType == null ? null : Graql.type(superRoleType));
    }

    /**
     * @param roleType a Role that this relation type variable must have
     * @return this
     */
    @CheckReturnValue
    default StatementType relates(Statement roleType, @Nullable Statement superRoleType) {
        return type(new RelatesProperty(roleType, superRoleType));
    }

    /**
     * @param datatype the datatype to set for this resource type variable
     * @return this
     */
    @CheckReturnValue
    default StatementType datatype(String datatype) {
        return datatype(Graql.Token.DataType.of(datatype));
    }

    /**
     * @param datatype the datatype to set for this resource type variable
     * @return this
     */
    @CheckReturnValue
    default StatementType datatype(Graql.Token.DataType datatype) {
        return type(new DataTypeProperty(datatype));
    }

    /**
     * Specify the regular expression instances of this resource type must match
     *
     * @param regex the regex to set for this resource type variable
     * @return this
     */
    @CheckReturnValue
    default StatementType regex(String regex) {
        return type(new RegexProperty(regex));
    }

    /**
     * @param when the left-hand side of this rule
     * @return this
     */
    @CheckReturnValue // TODO: make when() method take a more strict sub type of pattern
    default StatementType when(Pattern when) {
        return type(new WhenProperty(when));
    }

    /**
     * @param then the right-hand side of this rule
     * @return this
     */
    @CheckReturnValue // TODO: make then() method take a more strict sub type of pattern
    default StatementType then(Pattern then) {
        return type(new ThenProperty(then));
    }

    @CheckReturnValue   // TODO: will be made "private" once we upgrade to Java 9 or higher
    StatementType type(VarProperty property);
}
