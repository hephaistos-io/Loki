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

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Ricardo Daniel Monteiro Simoes
 */
public class TypeHelper {

    private static final Set<Class<?>> primitiveNumbers = Stream
            .of(int.class, long.class, float.class,
                    double.class, byte.class, short.class)
            .collect(Collectors.toSet());

    
    /**
     * @author Pshemo @ StackOverflow
     * https://stackoverflow.com/a/37656409/5471598
     * @param cls the class to check
     * @return true if the given class is of numeric nature
     */
    public static boolean isNumericType(Class<?> cls) {
        if (cls.isPrimitive()) {
            return primitiveNumbers.contains(cls);
        } else {
            return Number.class.isAssignableFrom(cls);
        }
    }

    /**
     * Checks to see if the given Class is of type Enum
     * @param type the class you want to check
     * @return true if the Class is OR extends Enum.class
     */
    public static boolean isEnum(Class<?> type) {
        return Enum.class.isAssignableFrom(type);
    }

    /**
     * Checks to see if the given Class is part of java.lang
     * @param type the class you want to check
     * @return true if the class is part of java.lang
     */
    public static boolean isJavaLang(Class<?> type) {
        return type.getName().startsWith("java.lang");
    }

}
