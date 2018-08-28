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

import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import ch.hephaistos.utilities.loki.util.interfaces.ObjectChangeListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A small helper for Reflection.
 *
 * @author I-Al-Istannen, : https://github.com/I-Al-Istannen
 */
public class ReflectionHelper {

    private static List<ChangeListener> interfacesToInvoke = new LinkedList<ChangeListener>();


    public static void addInterfaceToUpdate(ChangeListener interfaceToAdd){
        interfacesToInvoke.add(interfaceToAdd);
    }

    /**
     * Returns all fields from the class and all superclasses.
     *
     * @param start  The start {@link Class}
     * @param filter A filter for the fields, in order to only return some
     *               results.
     * @return All fields matching the {@link Predicate} from the whole class
     * hierarchy.
     */
    public static List<Field> getAllFieldsInClassHierachy(Class<?> start, Predicate<Field> filter) {
        List<Field> fields = new ArrayList<>();
        Class<?> currentClass = start;

        while (currentClass != null) {
            Collections.addAll(fields, currentClass.getDeclaredFields());
            currentClass = currentClass.getSuperclass();
        }

        return fields.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    /**
     * Returns the value of a field.
     *
     * @param field  The {@link Field} to get the value from.
     * @param handle The handle object to use (according to
     *               {@link Field#get(Object)})
     * @param <T>    The type of the return value you expect
     * @return The value of the field.
     * @throws ClassCastException        if the type is not what you stored it as
     * @throws ReflectionHelperException if any
     *                                   {@link ReflectiveOperationException} occurs.
     */
    public static <T> T getFieldValue(Field field, Object handle) {
        try {
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            T t = (T) field.get(handle);
            return t;
        } catch (ReflectiveOperationException e) {
            throw new ReflectionHelperException(e);
        }
    }

    /**
     * Sets the value of a field.
     * This function calls the objects that implemented the corresponding listeners.
     * The Listeners get called the following way:
     *
     * 1. ObjectChangeListener
     * 2. ChangeListener
     *
     * This means, that after a value has been changed using reflection, the update function of the object gets
     * called first.
     *
     * This is so that one can make sure the object runs its necessary functions before the GUI starts
     * updating itself.
     *
     * @param field  The {@link Field} to set the value for.
     * @param object The object object to use (according to
     * {@link Field#set(Object, Object)})
     * @param value  The value to set it to
     * @throws ReflectionHelperException if any
     * {@link ReflectiveOperationException} occurs.
     */
    public static void setFieldValue(Field field, Object object, Object value) {
        try {
            field.setAccessible(true);
            field.set(object, value);

            if(object instanceof ObjectChangeListener){
                ((ObjectChangeListener) object).onFieldValueChanged(field);
            }

            notifyListeners(field, object);

        } catch (ReflectiveOperationException e) {
            throw new ReflectionHelperException(e);
        }
    }


    /**
     * This function is called when the value of a variable changes. Use this to launch
     * updates or changes in other parts of the application.
     *
     * First thing it does is notify all the interfaces.
     * Due to there being a risk of an unhandled exception with the Reflective invokation of the update method,
     * they are done last so that at least the interfaces can receive an update.
     *
     * @param object the object that had its value changed
     * @param field is the field that was changed
     */
    public static void notifyListeners(Field field, Object object){
        for(ChangeListener i : interfacesToInvoke){i.onObjectValueChanged(field, object);}
    }

    public static class ReflectionHelperException extends RuntimeException {
        ReflectionHelperException(Throwable cause) {
            super(cause);
        }
    }
}
