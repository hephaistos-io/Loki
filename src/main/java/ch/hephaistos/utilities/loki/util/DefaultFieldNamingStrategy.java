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
import java.util.function.Function;

/**
 * Contains some basic {@link FieldNamingStrategy}s.
 * 
 * @author I-Al-Istannen, : https://github.com/I-Al-Istannen
 */
public enum DefaultFieldNamingStrategy implements FieldNamingStrategy {
  /**
   * {@link DefaultFieldNamingStrategy}.VERBATIM returns the name the Variable has
   */
  VERBATIM(Field::getName),

  /**
   * {@link DefaultFieldNamingStrategy}.SPLIT_TO_CAPITALIZED_WORDS returns the name split up and in CamelCase.
   * Example:
   * int receiverPort turns into Receiver Port
   */
  SPLIT_TO_CAPITALIZED_WORDS(field -> {
    StringBuilder output = new StringBuilder();

    boolean wordBoundary = true;

    for (char c : field.getName().toCharArray()) {
      if (Character.isUpperCase(c) || c == '_') {
        wordBoundary = true;
      }

      if (wordBoundary) {
        if (output.length() > 0) {
          output.append(" ");
        }
        output.append(Character.toUpperCase(c));
        wordBoundary = false;
      } else {
        output.append(Character.toLowerCase(c));
      }
    }

    return output.toString();
  });

  private Function<Field, String> transformationFunction;

  DefaultFieldNamingStrategy(Function<Field, String> transformationFunction) {
    this.transformationFunction = transformationFunction;
  }

  @Override
  public String toString(Field field) {
    return transformationFunction.apply(field);
  }

}
