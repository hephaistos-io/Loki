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

package ch.hephaistos.utilities.loki.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Defines how a {@link Field} name is mapped to a label in the GUI.
 * 
 * @author I-Al-Istannen, : https://github.com/I-Al-Istannen
 */
public interface FieldNamingStrategy {

  /**
   * @param field The {@link Field} to transform
   * @return The name of the field, according to this strategy
   */
  String toString(Field field);

}
