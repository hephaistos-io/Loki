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

package ch.hephaistos.utilities.loki.util.annotations;

import java.lang.annotation.*;

/**
 * This annotation is supposed to be used in conjunction with ReflectorGrid.
 * It has different variables which are used to set different options
 * during the creation of the grid. 
 * 
 * the enum <b>Fieldtype</b> is used to define if the Field is constructed
 * using a <b>TextField</b> or <b>TextArea</b>.
 * 
 * <b>editable</b> is used to define if a variable is read only or can 
 * also be written to.
 * 
 * <b>options</b> is used, once filled, to create a comboBox for the variable.
 * This is, in terms of hierarchy, above the <b>fieldtype</b> enum. As soon as
 * you fill something into options, the field for the corresponding variable
 * turns into a ComboBox.
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
     * true == calls function
     * false == does nothing
     * @return if it is enabled or not
     */
    public boolean enabled() default true;

    /**
     * This gives the Label of the variable a tooltip, which the user can show when hovering
     * over it with the mouse. This function is usable no mater what fields you are using.
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
