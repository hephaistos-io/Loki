/**

 Loki is an easy to use library that transforms simple annotations into a powerful GUI
 Copyright (C) 2018 Ricardo Simoes

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License as published
 by the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.


 */
package ch.hephaistos.utilities.loki.util.statics;

import ch.hephaistos.utilities.loki.util.typeconversion.EnumStringConverter;
import ch.hephaistos.utilities.loki.util.typeconversion.TypeConverterCollection;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * This class has a Collection of different Listeners. These can be used in a GUI to save the user Input into the
 * correct Field.
 * Created by Ricardo on 15.11.2017.
 * @author Ricardo Daniel Monteiro Simoes
 */
public class ListenerCollection {


    /**
     * This collection contains all needed TypeConverters.
     * In the case of Enums, it adds every new Enum-Type to the converterCollection.
     */
    private static TypeConverterCollection typeConverterCollection = new TypeConverterCollection();

    public static Consumer<String> getStringListener(Field field, Object handle, Object fieldObject) {
        Consumer<String> changeListener = string -> {
            Object value = typeConverterCollection.fromString(field.getType(), string);
            ReflectionHelper.setFieldValue(field, handle, value);
        };
        return changeListener;
    }


    public static Consumer<Enum> getEnumListener(Field field, Object handle, Object fieldObject) {
        Class<? extends Enum> clazz = (Class<? extends Enum>) field.getType();
        typeConverterCollection.addConverter(clazz, new EnumStringConverter(clazz));
        Consumer<Enum> changeListener = enumType -> {
            Object value = typeConverterCollection.fromObject(field.getType(), enumType);
            ReflectionHelper.setFieldValue(field, handle, value);
        };
        return changeListener;
    }


    public static Consumer<BigDecimal> getBigDecimalListener(Field field, Object handle, Object fieldObject) {
        Consumer<BigDecimal> changeListener = BigDecimal -> {
            Object value = typeConverterCollection.fromObject(field.getType(), BigDecimal);
            ReflectionHelper.setFieldValue(field, handle, value);
        };
        return changeListener;
    }

    public static Consumer<Object> getObjectListener(Field field, Object handle, Object fieldObject) {
        Consumer<Object> changeListener = object -> {
            Object value = typeConverterCollection.fromObject(field.getType(), object);
            ReflectionHelper.setFieldValue(field, handle, value);
        };

        return changeListener;
    }
}


