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

package ch.hephaistos.utilities.loki.util.interfaces;

import ch.hephaistos.utilities.loki.ReflectorGrid;

import java.lang.reflect.Field;

@FunctionalInterface
public interface ChangeListener {

    /**
     * This interface is intended to be used in combination with TransferGrid.
     * You can implement this interface and add it trough
     * {@link ReflectorGrid}. It will be called every time there is an update,
     * and you will receive the object that was updated as well as the field that was updated.
     *
     * This is made specifically this way so that you can focus on only updating the necessary parts of your
     * Application.
     *
     * @param field The field of the object that was updated
     * @param object The object that was updated
     */
    void onObjectValueChanged(Field field, Object oldValue, Object newValue, Object object);

}
