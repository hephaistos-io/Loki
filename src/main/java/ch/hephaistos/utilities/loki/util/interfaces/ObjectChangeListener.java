/**
 * The MIT License
 *
 * Copyright (c) 2019 Ricardo Daniel Monteiro Simoes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package ch.hephaistos.utilities.loki.util.interfaces;

import java.lang.reflect.Field;

/**
 * This interface is intended to be used in objects that will get utilized in combination with TransferGrid.
 * You do NOT have to implement this interface.
 * This Interface allows the user the see, which variable was updated. Therefore, an optimized way of
 * calling functions can be achieved.
 *
 * This exists so your function can do certain things after a variable was updated. Lets say,
 * you want to do some specific calculations after a value changes, you can use this interface for that.
 *
 * <b>Example:</b>
 *
 * {@code
 * public class ExampleObject implements ObjectChangeListener {
 *     @Override
 *     public void onFieldValueChanged(Field field) {
 *         System.out.println(field.getName() + " was updated!");
 *     }
 * }
 *
 * }
 *
 * @author Ricardo Daniel Monteiro Simoes
 */
@FunctionalInterface
public interface ObjectChangeListener {

    /**
     * Notifies you of the field that had its value changed.
     *
     * @param field The field of the object that was updated
     */
    void onFieldValueChanged(Field field);

}
