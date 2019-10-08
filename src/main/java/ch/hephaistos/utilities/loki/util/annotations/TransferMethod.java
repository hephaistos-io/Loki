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

package ch.hephaistos.utilities.loki.util.annotations;

import java.lang.annotation.*;

/**
 * This annotation is supposed to be used in conjunction with ReflectorGrid.
 * It has different variables which are used to set different options
 * during the creation of the grid. 
 *
 * <b>enabled()</b> allows you to show a disabled button on the GUI.
 *
 * <b>tooltip()</b> show the user a tooltip when hovering over the button
 *
 * <b>name()</b> sets the name to be displayed in the button.
 *
 * <b>Example</b>
 * {@code
 * @TransferGrid(tooltip = "Defines the runtime of this application")
 * private ExampleEnum option = ExampleEnum.FULL;
 *
 * @TransferGrid
 * private ExampleDataObject data;
 *
 * @TransferGrid(editable = false)
 * private String uneditableForYou = "blocked";
 * }
 *
 * This generates a Grid with a button named "Press Me!". It will have no tooltip,
 * and upon pressing it, the function will be executed.
 * 
 * @author Ricardo Daniel Monteiro Simoes
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TransferMethod {


    /**
     * This defines if the button is enabled or not.
     * default is set to true.
     * true => calls function
     * false => does nothing
     * @return
     */
    public boolean enabled() default true;

    /**
     * This gives the Label of the variable a tooltip, which the user can show when hovering
     * over it with the mouse.
     *
     * @return Returns an empty String if there is no tooltip defined.
     */
    public String tooltip() default "";

    /**
     * This is used to put some text inside the button.
     *
     * @return Returns an empty String if there is no name defined.
     */
    public String name() default "";
}
