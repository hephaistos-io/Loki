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

import ch.hephaistos.utilities.loki.ReflectorGrid;

import java.lang.reflect.Field;

/**
 * This interface is intended to be used in combination with TransferGrid.
 * You can implement this interface and add it trough
 * {@link ReflectorGrid}. It will be called every time there is an update,
 * and you will receive the object that was updated as well as the field that was updated.
 *
 * This is made specifically this way so that you can focus on only updating the necessary parts of your
 * Application
 *
 * <b>Example:</b>
 *
 * {@code
 * public class ExampleMain extends Application implements ChangeListener {
 *      ReflectorGrid reflectorGrid = new ReflectorGrid();
 *      ...
 *      reflectorGrid.addChangeListener(this);
 *
 *      @Override
 *      public void onObjectValueChanged(Field field, Object oldValue, Object newValue, Object object) {
 *          System.out.println("Field: " + field.getName() + " changed its value from " +
 *          oldValue.toString() + " to " + newValue.toString() + " in object " + object.toString());
 *      }
 * }
 *
 * }
 *
 * @author Ricardo Daniel Monteiro Simoes
 */
@FunctionalInterface
public interface ChangeListener {

    /**
     * This function gets called every time the ReflectorGrid sets a new value to a variable.
     *
     * @param field The field of the object that was updated
     * @param oldValue The previous value the field was set to
     * @param newValue The newly set value
     * @param object The object that was updated
     */
    void onObjectValueChanged(Field field, Object oldValue, Object newValue, Object object);

}
